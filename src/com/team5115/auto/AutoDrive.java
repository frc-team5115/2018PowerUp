package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Konstanten;
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
		targetAngle = Robot.drivetrain.getYaw();
		//System.out.println("Target " + targetAngle);

		
		//Change back to our constants, this one doesn't work
		forwardController = new PID(Konstanten.AUTO_FORWARD_KP, Konstanten.AUTO_FORWARD_KI, Konstanten.AUTO_FORWARD_KD ,maxSpeed);
		turnController = new PID(Konstanten.AUTO_TURN_KP, Konstanten.AUTO_TURN_KI ,Konstanten.AUTO_TURN_KD);
		setState(DRIVING);
	}

	public void startTurn(double angle, double maxSpeed) {
		targetDist = Robot.drivetrain.distanceTraveled();
		targetAngle = Robot.drivetrain.getYaw() + (angle);

		forwardController = new PID(Konstanten.AUTO_FORWARD_KP, Konstanten.AUTO_FORWARD_KI, Konstanten.AUTO_FORWARD_KD);
		turnController = new PID(Konstanten.TURN_KP, Konstanten.TURN_KI ,Konstanten.AUTO_TURN_KD, maxSpeed);
		
		setState(DRIVING);
	}
	
	public void startArc(double radius, double angle, double maxSpeed) {
		double distance = (angle * Math.PI / 180) * radius;
		//double time = angle / maxSpeed;
		double maxForwardSpeed = Konstanten.ARC_RATIO * maxSpeed;
		targetDist = Robot.drivetrain.distanceTraveled() + distance;
		targetAngle = Robot.drivetrain.getYaw() + (angle);

		forwardController = new PID(Konstanten.AUTO_FORWARD_KP, Konstanten.AUTO_FORWARD_KI, Konstanten.AUTO_FORWARD_KD, maxForwardSpeed);
		turnController = new PID(Konstanten.AUTO_TURN_KP, Konstanten.AUTO_TURN_KI ,Konstanten.AUTO_TURN_KD, maxSpeed);
		
		setState(DRIVING);
	}

	public void update() {
		//System.out.println("autodrive state: " + state);
		switch (state) {
			case DRIVING:
				Robot.drivetrain.inuse = true;

				// run every Constants.getAsDouble()DELAY seconds while driving
				double vForward = forwardController.getPID(targetDist, Robot.drivetrain.distanceTraveled(), Robot.drivetrain.averageSpeed());

				double clearYaw = clearSteer(Robot.drivetrain.getYaw(), targetAngle);
				double vTurn = turnController.getPID(targetAngle, clearYaw, Robot.drivetrain.getTurnVelocity());

				//Log.add("yawp", turnController.getError() * Constantos.AUTO_TURN_KP);
				//Log.add("yawi", turnController.getError() * Constantos.AUTO_TURN_KI);
				//Log.add("yawcorrection", vTurn);
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

				SmartDashboard.putNumber("vTurn", vTurn);
				SmartDashboard.putNumber("vForward", vForward);
				
				if (forwardController.isFinished(Konstanten.FORWARD_TOLERANCE, Konstanten.FORWARD_DTOLERANCE) && turnController.isFinished(Konstanten.TURN_TOLERANCE, Konstanten.TURN_DTOLERANCE)) {
					Robot.drivetrain.drive(0, 0);
					setState(FINISHED);
				}

				break;
			case FINISHED:
				Robot.drivetrain.inuse = false;
				Robot.drivetrain.drive(0, 0);
				
				forwardController = null;
				turnController = null;
				break;
		}
	}

	private double clearSteer(double yaw, double target) {
		if (Math.abs(target - yaw) > 180) {
			if (target < 180) {	// yaw must be too high for target
				yaw -= 180 * 2;
			} else {	// yaw must be too low for target
				yaw += 180 * 2;
			}
		}

		return yaw;
	}

}
