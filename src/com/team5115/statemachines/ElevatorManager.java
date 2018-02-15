package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Konstanten;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class ElevatorManager extends StateMachineBase {

	public static final int STOP = 0;
	public static final int MOVING_UP = 1;
	public static final int MOVING_DOWN = 2;
	public static final int MOVING_TO = 3;
	
	PID movement;
	double targetAngle;
	double dAngle;
	double angle;
	double output;
	
	public void setState(int s) {
		super.setState(s);
		System.out.println("Switched to " + s);
	}
	
	public void startMovement(double angle){
		targetAngle = angle;
		movement = new PID(Konstanten.ARM_KP, Konstanten.ARM_KI, Konstanten.ARM_KD, Konstanten.ELEVATOR_SPEED);
		setState(MOVING_TO);
	}
	
	public void cancelMovement(){
		if (movement != null){
			movement = null;
			state = STOP;
		}
	}
	
	public void update() {
		angle = Robot.elevator.getAngle();
		dAngle = Robot.elevator.getAngleSpeed();
		switch (state) {
			case STOP:
				//Stops the elevator
				Robot.elevator.move(0);
				break;
			case MOVING_UP:
				//Elevator moves up to max height
				if(Robot.elevator.maxHeight()){
					Robot.elevator.move(0);
				} else {
					Robot.elevator.move(0.25);	
				}
				break;
			case MOVING_DOWN:
				//Elevator goes down to lowest point
				if(Robot.elevator.minHeight()){
					Robot.elevator.move(0);
				} else{
					Robot.elevator.move(-0.25);
				}
				break;
			case MOVING_TO:
				//Elevator moves to either switch or scale height 
				output = movement.getPID(targetAngle, angle, dAngle);
				Robot.elevator.move(output);
				//System.out.println("out " + output);
				
				if(movement.isFinished(Konstanten.ARM_TOLERANCE, Konstanten.ARM_DTOLERANCE)){
					movement = null;
					setState(STOP);
				}
				break;
		}
	}
}