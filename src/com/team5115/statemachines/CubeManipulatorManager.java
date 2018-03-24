package com.team5115.statemachines;

import com.team5115.Konstanten;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class CubeManipulatorManager extends StateMachineBase {
	
	public static final int INTAKE = 1;
	public static final int LOWER_INTAKE = 2;
	public static final int PASS_TO_INTAKE = 3;
	public static final int PASS_TO_ARM = 4;
	public static final int TRANSIT = 5;
	public static final int DRIVIN_AROUND_WIT_DA_INTAKE_DOWN = 6;
	public static final int EMPTY = 7;
	
	public double armGoal = Robot.elevator.getAngle();
	private double time;
	
	protected void updateChildren() {
		Robot.IM.update();
		Robot.EM.update();
		Robot.CM.update();
	}
	
	public void collisionAvoidance() {
		if (Robot.elevator.minHeight() && !Robot.elevator.movingArm) {
			Robot.IM.setState(IntakeManager.GRIP_UP);
		} else if (Robot.elevator.getAngle() <= Konstanten.INTAKE_HEIGHT) {
			Robot.IM.setState(IntakeManager.STOW_OPEN);
		} else {
			Robot.IM.setState(IntakeManager.STOW_CLOSED);
		}
	}
	
	public void update() {
		switch (state) {
			case STOP:
				// EVERYTHING INACTIVE
				updateChildren();
				Robot.IM.setState(IntakeManager.STOP);
				Robot.CM.setState(CarriageManager.STOP);
				Robot.EM.setState(ElevatorManager.STOP);
				if (InputManager.intake()) {
					setState(INTAKE);
				}
				break;
			case INTAKE:
				// ELEVATOR DOWN, INTAKE ACTIVE,CARRIAGE ARMED
				// IF SENSOR INPUT IS RECOGNIZED, GO TO TRANSIT
				
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
				armGoal = Konstanten.RETURN_HEIGHT;
				updateChildren();
				
				if (InputManager.bump()) {
					Robot.IM.setState(IntakeManager.CORRECT);
				} else if (Robot.intake.isCube()){
					Robot.IM.setState(IntakeManager.GRIP_DOWN);
				} else if (InputManager.eject()) {
					Robot.IM.setState(IntakeManager.SPIT);
				} else {
					Robot.IM.setState(IntakeManager.INTAKE);
				}

				if (!InputManager.intake()) {
					setState(PASS_TO_ARM);
				}
				
				break;

			case PASS_TO_INTAKE:
				Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
				armGoal = Konstanten.RETURN_HEIGHT;
				updateChildren();
				
				if(Robot.elevator.minHeight()) {
					Robot.IM.setState(IntakeManager.GRIP_UP);
					Robot.CM.setState(CarriageManager.DUMP);
					time = Timer.getFPGATimestamp();
					setState(DRIVIN_AROUND_WIT_DA_INTAKE_DOWN);
				}
				else {
					collisionAvoidance();
				}
				break;
				
			case DRIVIN_AROUND_WIT_DA_INTAKE_DOWN:
				updateChildren();

				Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
				
				if(Timer.getFPGATimestamp() >= time + Konstanten.PASSBACK_TIME) {
					Robot.IM.setState(IntakeManager.GRIP_DOWN);
				}
				else {
					Robot.IM.setState(IntakeManager.GRIP_UP);
				}
				
				//user inputs
				if (InputManager.eject()) {
					Robot.IM.setState(IntakeManager.SPIT);
					time = Timer.getFPGATimestamp();
					setState(EMPTY);
				}
				if (InputManager.moveUp()) {
					armGoal = Robot.elevator.getAngle() + Konstanten.ELEVATOR_STEP;
					setState(PASS_TO_ARM);
				}
				if (InputManager.moveDown()) {
					setState(PASS_TO_ARM);
				}
				if (InputManager.scaleHeight()) {
					armGoal = Konstanten.SCALE_HEIGHT;
					setState(PASS_TO_ARM);
				}
				if (InputManager.switchHeight()) {
					armGoal = Konstanten.SWITCH_HEIGHT;
					setState(PASS_TO_ARM);
				}
				if (InputManager.returnHeight()) {
					armGoal = Konstanten.RETURN_HEIGHT;
					setState(PASS_TO_ARM);
				}
				if (InputManager.intake()) {
					setState(INTAKE);
				}
				
				break;
			case PASS_TO_ARM:
				// ELEVATOR STILL, INTAKE UP, CARRIAGE ACTIVE
				// IF USER INPUT IS RECOGNIZED, GO TO EMPTY
				updateChildren();
				
				Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
				Robot.CM.setState(CarriageManager.DUMP);
				

				//Robot.IM.setState(IntakeManager.GRIP_UP);
				time = Timer.getFPGATimestamp();
				setState(TRANSIT);
				
				if (Robot.elevator.minHeight() && !InputManager.grabIntake()) {
					Robot.IM.setState(IntakeManager.GRIP_UP);
					time = Timer.getFPGATimestamp();
					setState(TRANSIT);
				} else {
					Robot.IM.setState(IntakeManager.GRIP_DOWN);
				}
				
				break;
			
			case TRANSIT:
				// need to implement ability to lower intake for exchange 
				//Robot.IM.setState(IntakeManager.STOP)
				updateChildren();
				
				if (Robot.elevator.minHeight()) {
					Robot.IM.setState(IntakeManager.GRIP_UP);
				}
				else {
					collisionAvoidance();
				}
				
				if (Timer.getFPGATimestamp() >= time + Konstanten.PASSOFF_TIME) {
					Robot.CM.setState(CarriageManager.GRAB);
					Robot.EM.setTarget(armGoal);
				}
				else {
					Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
					Robot.CM.setState(CarriageManager.DUMP);
				}
				
				if (InputManager.intake()) {
					Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
				
				if((InputManager.moveUp()) && !Robot.elevator.maxHeight()){
					armGoal = Robot.elevator.getAngle() + Konstanten.ELEVATOR_STEP;
				}
				
				if(InputManager.moveDown() && !Robot.elevator.minHeight()){
					armGoal = Robot.elevator.getAngle() - Konstanten.ELEVATOR_STEP;
				}
		
				if (InputManager.eject()) {
					setState(EMPTY);
				}
				
				if (InputManager.switchHeight()) {
					armGoal = Konstanten.SWITCH_HEIGHT;
				}
				
				if (InputManager.scaleHeight()) {
					armGoal = Konstanten.SCALE_HEIGHT;
				}
				
				if (InputManager.returnHeight()) {
					armGoal = Konstanten.RETURN_HEIGHT;
				}
				
				if (InputManager.spit()) {
					setState(PASS_TO_INTAKE);
				}
				break;
				
			case EMPTY:
				//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE DUMPING
				//IF USER INPUT IS DETECTED, GO TO RETURING
				Robot.CM.setState(CarriageManager.DUMP);
				collisionAvoidance();
				updateChildren();
				
				// if we're coming from DRIVIN_AROUND_WIT_DA_INTAKE_DOWN, wait for the ejection to finish
				// otherwise, time should already be something from much longer ago
				if (Timer.getFPGATimestamp() >= time + Konstanten.SPIT_DELAY) {
					//Robot.IM.setState(IntakeManager.STOW_CLOSED);
				}
				
				Robot.EM.setTarget(armGoal);

				if((InputManager.moveUp()) && !Robot.elevator.maxHeight()){
					armGoal = Robot.elevator.getAngle() + Konstanten.ELEVATOR_STEP;
				}
				
				if(InputManager.moveDown() && !Robot.elevator.minHeight()){
					armGoal = Robot.elevator.getAngle() - Konstanten.ELEVATOR_STEP;
				}

				if (InputManager.switchHeight()) {
					armGoal = Konstanten.SWITCH_HEIGHT;
				}
				
				if (InputManager.scaleHeight()) {
					armGoal = Konstanten.SCALE_HEIGHT;
				}
				
				if (InputManager.returnHeight()) {
					armGoal = Konstanten.RETURN_HEIGHT;
				}
				
				if (InputManager.intake()){
					Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
				
				if (InputManager.grabIntake()) {
					setState(TRANSIT);
				}
				
				break;
		}
	}
}