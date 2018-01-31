package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Konstanten;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class CubeManipulatorManager extends StateMachineBase {

	public static final int INIT = 1;
	public static final int INTAKE = 2;
	public static final int SPIT = 3;
	public static final int SWALLOW = 4;
	public static final int TRANSIT = 5;
	public static final int SWITCH = 6;
	public static final int SCALE = 7;
	public static final int DUMP = 8;
	public static final int RETURNING = 9;
	public static final int RESET = 10;

	

	PID turnController;
	public void update() {
		switch (state) {
			case STOP:
				//EVERYTHING INACTIVE
				Robot.IM.setState(IntakeManager.STOP);
				Robot.CM.setState(CarriageManager.STOP);
				Robot.EM.setState(ElevatorManager.STOP);
				if (InputManager.intake()) {
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
				break;
			case INIT:
				break;
			case INTAKE:
				//ELEVATOR DOWN, INTAKE ACTIVE,CARRIAGE ARMED
				// IF SENSOR IMPUT IS RECOGNIZED, GO TO TRANSIT
				Robot.IM.setState(IntakeManager.INTAKE);
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				if (Robot.intake.isCube())
					setState(SWALLOW);
				break;
			case SPIT:
				Robot.IM.setState(IntakeManager.SPIT);
				break;
			case SWALLOW:
				//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE ACTIVE
				//IF USER INPUT IS RECOGNIZED, GO TO DUMP
				Robot.IM.setState(IntakeManager.GRIP);
				Robot.EM.update();
				Robot.IM.update();
				Robot.CM.update();
			
				
				setState(TRANSIT);
				break;
			case TRANSIT:
				Robot.CM.setState(CarriageManager.GRAB);
				Robot.IM.setState(IntakeManager.RELEASE);
				Robot.EM.update();
				Robot.IM.update();
				Robot.CM.update();
				
				if (InputManager.switchHeight()) {
					Robot.EM.startMovement(Konstanten.SWITCH_HEIGHT);
					setState(SWITCH);
				}
				if (InputManager.scaleHeight()) {
					Robot.EM.startMovement(Konstanten.SCALE_HEIGHT);
					setState(SCALE);
				}
				if(InputManager.moveUp() && !Robot.elevator.maxHeight()){
					Robot.EM.setState(ElevatorManager.MOVING_UP);
				}
				if(InputManager.moveDown() && !Robot.elevator.minHeight()){
					Robot.EM.setState(ElevatorManager.MOVING_DOWN);
				}
				if(InputManager.moveDown() == InputManager.moveUp()){
					Robot.EM.setState(ElevatorManager.STOP);
				}
				if (InputManager.eject()) {
					setState(DUMP);
				}
				break;
				
			case SWITCH:
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				
				if(InputManager.moveUp() || InputManager.moveDown()){
					Robot.EM.cancelMovement();
					state = TRANSIT;
				}
				
				if (InputManager.eject()) {
					setState(DUMP);
				}
				
				break;
			case SCALE:
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				
				if(InputManager.moveUp() || InputManager.moveDown()){
					Robot.EM.cancelMovement();
					state = TRANSIT;
				}
				
				if (InputManager.eject()) {
					setState(DUMP);
				}
				break;
			case DUMP:
				//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE DUMPING
				//IF USER INPUTIS DETECTED,GO TO RETURING
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.IM.setState(StateMachineBase.STOP);
				Robot.EM.update();
				Robot.IM.update();
				Robot.CM.update();
				
				if (InputManager.returnHeight()){
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(RETURNING);
				}
				break;
			case RETURNING:
				Robot.IM.setState(StateMachineBase.STOP);
				Robot.CM.setState(StateMachineBase.STOP);
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				//ELEVATOR RETURNING, INTAKE INACTIVE,CARRIAGE INACTIVE
				//GO TO DRIVING
				break;
		}
	}
}