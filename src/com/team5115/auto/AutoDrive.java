package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.cruzsbrian.robolog.Constants;
//import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDrive extends StateMachineBase {

    public static final int DRIVING = 1;
    public static final int FINISHED = 2;

    double targetDist;
    double targetAngle;

    PID forwardController;
    PID turnController;

    public void startLine(double dist, double maxSpeed) {
        //Takes some time to reset gyros + encoders
    	Timer.delay(0.1);
        targetDist = Robot.drivetrain.distanceTraveled() + dist;
        Log.log("target", Robot.drivetrain.getYaw());
        targetAngle = Robot.drivetrain.getYaw();
        //System.out.println("Target " + targetAngle);

        
        //Change back to our constants, this one doesn't work
        //forwardController = new PID(Constants.AUTO_FORWARD_KP, Constants.AUTO_FORWARD_KI, Constants.AUTO_FORWARD_KD, maxSpeed);
        //turnController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD, maxSpeed);
        forwardController = new PID(Constants.getAsDouble("auto_forward_kp"), Constants.getAsDouble("auto_forward_ki"), Constants.getAsDouble("auto_forward_kd"), maxSpeed);
        turnController = new PID(Constants.getAsDouble("auto_turn_kp"), Constants.getAsDouble("auto_turn_ki"), Constants.getAsDouble("auto_turn_kd"), maxSpeed);
        setState(DRIVING);
    }

    public void startTurn(double angle, double maxSpeed) {
        targetDist = Robot.drivetrain.distanceTraveled();
        targetAngle = Robot.drivetrain.getYaw() + (angle * (Math.PI / 180));

        //forwardController = new PID(Constants.AUTO_FORWARD_KP, Constants.AUTO_FORWARD_KI, Constants.AUTO_FORWARD_KD, maxSpeed);
        //turnController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD, maxSpeed);
        forwardController = new PID(Constants.getAsDouble("auto_forward_kp"), Constants.getAsDouble("auto_forward_ki"), Constants.getAsDouble("auto_forward_kd"), maxSpeed);
        turnController = new PID(Constants.getAsDouble("auto_turn_kp"), Constants.getAsDouble("auto_turn_ki"), Constants.getAsDouble("auto_turn_kd"), maxSpeed);


        setState(DRIVING);
    }

    public void update() {
        switch (state) {
            case DRIVING:
                Robot.drivetrain.inuse = true;

                // run every Constants.getAsDouble()DELAY seconds while driving
                double vForward = forwardController.getPID(targetDist, Robot.drivetrain.distanceTraveled(), Robot.drivetrain.averageSpeed());

                double clearYaw = clearSteer(Robot.drivetrain.getYaw(), targetAngle);
                double vTurn = turnController.getPID(targetAngle, clearYaw, Robot.drivetrain.getTurnVelocity());

                Log.add("yawp", turnController.getError() * Constants.getAsDouble("auto_turn_kp"));
                Log.add("yawi", turnController.getError() * Constants.getAsDouble("auto_turn_ki"));
                Log.add("yawcorrection", vTurn);
                //Log.log("vForward", vForward);
                //Log.log("average speed", Robot.drivetrain.averageSpeed());
                Log.log("leftSpeed", Robot.drivetrain.leftSpeed());
                Log.log("rightSpeed", Robot.drivetrain.rightSpeed());
                Log.log("error", targetDist - Robot.drivetrain.distanceTraveled());
                Log.log("turn error", targetAngle - Robot.drivetrain.getYaw());
                Robot.drivetrain.drive(vForward, vTurn);
                //System.out.println("distance travelled:  " + Robot.drivetrain.distanceTraveled());
                //System.out.println("Yaw " + Robot.drivetrain.getYaw());
                
                SmartDashboard.putNumber("distance traveled", Robot.drivetrain.distanceTraveled());
                SmartDashboard.putNumber("velocity", Robot.drivetrain.averageSpeed());
                
                
                
                if (forwardController.isFinished(Constants.getAsDouble("forward_tolerance"), Constants.getAsDouble("forward_dtolerance")) && turnController.isFinished(Constants.getAsDouble("turn_tolerance"), Constants.getAsDouble("turn_dtolerance"))) {
                    Robot.drivetrain.drive(0, 0);
                    setState(FINISHED);
                }

                break;
            case FINISHED:
                Robot.drivetrain.inuse = false;
            	Robot.drivetrain.drive(0, 0);
            	break;
        }
    }

    private double clearSteer(double yaw, double target) {
        if (Math.abs(target - yaw) > Math.PI) {
            if (target < Math.PI) {	// yaw must be too high for target
                yaw -= Math.PI * 2;
            } else {	// yaw must be too low for target
                yaw += Math.PI * 2;
            }
        }

        return yaw;
    }

}
