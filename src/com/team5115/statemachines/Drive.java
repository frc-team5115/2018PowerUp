package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Konstanten;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class Drive extends StateMachineBase {

	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int TIPPING = 3;

	PID turnController;
	public Drive() {
		turnController = new PID(1, Konstanten.TURN_KI, 0);
	}

	public void update() {
		switch (state) {
			case STOP:
				Robot.drivetrain.drive(0, 0);
				break;

			case DRIVING:
		   
				if (!Robot.drivetrain.inuse) {
					// find desired forward and turning speeds in ft/s
					double forwardSpeed = InputManager.getForward() * InputManager.getThrottle() * Konstanten.TOP_SPEED;
					double turnSpeed = InputManager.getTurn() * InputManager.getThrottle() * Konstanten.TOP_TURN_SPEED;
					
//					if (forwardSpeed < 0) {
//						turnSpeed = -turnSpeed;
//					}
					//System.out.println("forward " + InputManager.getForward());
					//System.out.println("turn " + turnSpeed);

					// open loop control for forward
					// vForward is negative because y on the joystick is reversed
					//double vForward = forwardSpeed * Constants.FORWARD_KF;
					
					Robot.drivetrain.drive(forwardSpeed, turnSpeed);
					if ((Robot.drivetrain.getPitch() < Konstanten.TIP_THRESHOLD) && (Robot.drivetrain.forwarAccel() > Konstanten.ACCEL_THRESHOLD)){
						Robot.drivetrain.inuse = true;
						setState(TIPPING);
					}
				}
				break;
			case TIPPING:
					double power = -(Robot.drivetrain.getPitch() / Konstanten.TIP_THRESHOLD);
					Robot.drivetrain.drive(power, 0);
					if (Robot.drivetrain.getPitch() > Konstanten.SAFE_ANGLE){
						Robot.drivetrain.inuse = false;
						setState(DRIVING);
					}
					break;
		}
	}

}
