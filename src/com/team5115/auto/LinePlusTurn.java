package com.team5115.auto;

import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LinePlusTurn extends StateMachineBase {

	public static final int DRIVING = 1;
	public static final int FINISHED = 2;
	
	boolean line;

	double targetDist;
	double targetAngle;
	
	double startDist;
	double startAngle;
	double distToTurn;
	double turnAngle;

	PID forwardController;
	PID turnController;

	public void start(double dist, double angle, double distToTurn, double maxForwardSpeed, double maxTurnSpeed) {
		line = true;
		
		targetDist = Robot.drivetrain.distanceTraveled() + dist;
		targetAngle = Robot.drivetrain.getYaw();

		turnAngle = angle;
		this.distToTurn = distToTurn;
		startDist = Robot.drivetrain.distanceTraveled();
		startAngle = Robot.drivetrain.getYaw();
		
		//Change back to our constants, this one doesn't work
		forwardController = new PID(Konstanten.AUTO_FORWARD_KP, Konstanten.AUTO_FORWARD_KI, Konstanten.AUTO_FORWARD_KD ,maxForwardSpeed);
		turnController = new PID(Konstanten.TURN_KP, Konstanten.TURN_KI ,Konstanten.AUTO_TURN_KD, maxTurnSpeed);
		setState(DRIVING);
	}

	public void update() {
		SmartDashboard.putNumber("autodrive state: ", state);
		System.out.println("autodrive target: " + targetDist);
		switch (state) {
			case DRIVING:
				Robot.drivetrain.inuse = true;

				// run every Constants.getAsDouble()DELAY seconds while driving
				double vForward = forwardController.getPID(targetDist, Robot.drivetrain.distanceTraveled(), Robot.drivetrain.averageSpeed());

				double clearYaw = clearSteer(Robot.drivetrain.getYaw(), targetAngle);
				double vTurn = turnController.getPID(targetAngle, clearYaw, Robot.drivetrain.getTurnVelocity());
				
				if (!line && Math.abs(turnController.getError()) > 4 * Konstanten.TURN_TOLERANCE) {
					vTurn += 0.15 * Math.signum(vTurn);
				}
				
				if (Robot.drivetrain.distanceTraveled() - startDist >= distToTurn) {
					targetAngle = startAngle + turnAngle;
				}

				Robot.drivetrain.drive(vForward, vTurn);
				
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
			if (target < 180) {
				yaw -= 360;
			} else {
				yaw += 360;
			}
		}

		return yaw;
	}

}
