package com.team5115;

import com.cruzsbrian.robolog.Log;

public class PID {

    /**
     * What is PID?
     * PID stands for Proportional-Integral-Derivative
     * It is a type of feedback loop that takes in an error value and tries to compensate for the error
     * Proportional control changes the motor input as a function of some fraction of the error in speed
     * Integral control changes the motor input as a function of some fraction of the error in distance travelled (or distance left to travel)
     * Derivative control changes the rate of change of the motor input as a function of some fraction of the error in the motor's acceleration
     */

    double kp;
    double ki;
    double kd;
    double maxOutput;

    double error;
    double errorAccum = 0;
    double dError;
    double output;

    /**
     * Create a PID loop with a certain maximum output
     * @param p
     * @param i
     * @param d
     * @param max
     */
    public PID(double p, double i, double d, double max) {
        kp = p;
        ki = i;
        kd = d;
        maxOutput = max;
    }

    /**
     * Create a PID loop. Maximum output defaults to +/- 1, with the assumption that this will be used as a % of vBus for a motor
     * @param p
     * @param i
     * @param d
     */
    public PID(double p, double i, double d) {
        this(p, i, d, 1);
    }

    /**
     * Runs the PID loop. Must be run every Constants.DELAY seconds. dReading should be in reading units per second
     * @param setpoint
     * @param reading
     * @param dReading
     * @return output of PID controller
     */
    public double getPID(double setpoint, double reading, double dReading) {
        error = setpoint - reading;
        dError = -dReading;		// result of taking the derivative of the equation above with respect to time

        output = kp * error + ki * errorAccum + kd * dError;
        Log.log("error", error);
        // Do not integrate if the output exceeds max to avoid intergral windup. See youtu.be/fusr9eTceEo
        if (Math.abs(output) <= maxOutput) {
            errorAccum += error * Constants.DELAY;
        }

        // Do not return a value greater than the maximum, but make sure it's the right sign
        if (Math.abs(output) > maxOutput) {
            output = maxOutput * Math.signum(output);
        }
        Log.log("pid output", output);
        return output;
    }

    /**
     * Returns true if PID loop has settled. Takes tolerance in reading units and dErrorTolerance in reading units per second
     * @param tolerance
     * @param dErrorTolerance
     * @return true if PID loop has settled to within tolerances, false otherwise
     */
    public boolean isFinished(double tolerance, double dErrorTolerance) {
        return (Math.abs(error) < tolerance && Math.abs(dError) < dErrorTolerance);
    }

	public double getError() {
		return error;
	}
}
