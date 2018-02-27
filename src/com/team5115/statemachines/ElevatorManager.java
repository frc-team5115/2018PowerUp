package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Konstanten;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class ElevatorManager extends StateMachineBase {

	public static final int STOP = 0;
	public static final int MOVING_TO = 1;
	
	PID movement;
	double targetAngle;
	double dAngle;
	double angle;
	double output;
	
	public ElevatorManager(){
		movement = new PID( Konstanten.ARM_KP,	//readability!
							Konstanten.ARM_KI,	//because this line was too thicc
							Konstanten.ARM_KD,
							Konstanten.ELEVATOR_SPEED_SCALE);
	}

	public void setTarget(double target) {
		targetAngle = target;
	}
	
	public void update() {
		angle = Robot.elevator.getAngle();
		dAngle = Robot.elevator.getAngleSpeed();
		switch (state) {
			case STOP:
				//Stops the elevator
				Robot.elevator.move(0);
				break;

			case MOVING_TO:
				//Elevator moves to either switch or scale height 
				output = movement.getPID(targetAngle, angle, dAngle);
				Robot.elevator.move(output);
				break;
		}
	}
	

}