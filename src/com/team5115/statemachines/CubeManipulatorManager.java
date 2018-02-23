package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class CubeManipulatorManager extends StateMachineBase {
	
	public static final int INTAKE = 1;
	public static final int LOWER_INTAKE = 2;
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
		//System.out.println(state);
		switch (state) {
			case STOP:
				// EVERYTHING INACTIVE
				Robot.IM.setState(IntakeManager.STOP);
				Robot.CM.setState(CarriageManager.STOP);
				Robot.EM.setState(ElevatorManager.STOP);
				if (InputManager.intake()) {
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
				break;
			case INTAKE:
				// ELEVATOR DOWN, INTAKE ACTIVE,CARRIAGE ARMED
				// IF SENSOR INPUT IS RECOGNIZED, GO TO TRANSIT
				
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				if (InputManager.bump()){
					Robot.IM.setState(IntakeManager.CORRECT);
				}
				else{
					Robot.IM.setState(IntakeManager.INTAKE);
				}
				if ((InputManager.grabIntake() || Robot.intake.isCube()) && Robot.elevator.minHeight())
					setState(SWALLOW);
				break;
				
				
			case LOWER_INTAKE:
				Robot.IM.setState(IntakeManager.LOWER_INTAKE);
				Robot.IM.update();
				Robot.CM.update();
				
				if(InputManager.spit()) {
					setState(SPIT);
				}
			case SPIT:
				Robot.IM.setState(IntakeManager.SPIT);
				Robot.IM.update();
				Robot.CM.update();
				
				if(!Robot.intake.isCube()) {
					setState(INTAKE);
				}
				break;
			case SWALLOW:
				// ELEVATOR STILL, INTAKE UP, CARRIAGE ACTIVE
				// IF USER INPUT IS RECOGNIZED, GO TO DUMP
				Robot.IM.setState(IntakeManager.GRIP);
				Robot.EM.update();
				Robot.IM.update();
				Robot.CM.update();
			
				Timer.delay(1);
				Robot.CM.setState(CarriageManager.GRAB);
				setState(TRANSIT);
				break;
			case TRANSIT:
				// need to implement ability to lower intake for exchange 
				//Robot.IM.setState(IntakeManager.STOP);
				Robot.EM.update();
				Robot.IM.update();
				Robot.CM.update();
				if ((Robot.elevator.getAngle() <  Konstanten.INTAKE_HEIGHT) && Robot.elevator.movingArm){
					Robot.IM.setState(IntakeManager.RELEASE);
				}
				else{
					Robot.IM.setState(IntakeManager.GRIP);
				}
				if (InputManager.intake()) {
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
//				if(Robot.elevator.getAngle() <  Konstanten.INTAKE_HEIGHT){
//					Robot.IM.setState(IntakeManager.RELEASE);
//				}
//				else{
//					Robot.IM.setState(IntakeManager.GRIP);
//					System.out.println("GRIPPPPPP");
//				}
				
				if((InputManager.moveUp()) && !Robot.elevator.maxHeight()){
					Robot.EM.setState(ElevatorManager.MOVING_UP);
				}
				
				if(InputManager.moveDown() && !Robot.elevator.minHeight()){
					Robot.EM.setState(ElevatorManager.MOVING_DOWN);
				}
				
				if (InputManager.moveDown() == InputManager.moveUp() && Robot.EM.state != ElevatorManager.MOVING_TO) {
					Robot.IM.setState(IntakeManager.GRIP);
					Robot.EM.setState(ElevatorManager.STOP);
				}
		
				if (InputManager.eject()) {
					setState(DUMP);
				}
				if (InputManager.switchHeight()) {
					if(Robot.elevator.getAngle() <  Konstanten.INTAKE_HEIGHT){
						Robot.IM.setState(IntakeManager.RELEASE);
					}
					else {
						Robot.IM.setState(IntakeManager.GRIP);
						System.out.println("GRIPPPPPP");
					}
					Robot.EM.startMovement(Konstanten.SWITCH_HEIGHT);
					setState(SWITCH);
				}
				if (InputManager.scaleHeight()) {
					Robot.EM.startMovement(Konstanten.SCALE_HEIGHT);
					setState(SCALE);
				}
				if (InputManager.spit() && Robot.elevator.minHeight()) {
					Robot.CM.setState(CarriageManager.DUMP);
					Robot.IM.setState(IntakeManager.GRIP);
					Robot.IM.setState(IntakeManager.LOWER_INTAKE);
					//delay
					setState(LOWER_INTAKE);
				}
				break;
				
			case SWITCH:
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				
				if(InputManager.moveUp() || InputManager.moveDown()){
					Robot.EM.cancelMovement();
					setState(TRANSIT);
				}
				
				if(InputManager.scaleHeight()) {
					Robot.EM.cancelMovement();
					Robot.EM.startMovement(Konstanten.SCALE_HEIGHT);
					setState(SCALE);
				}
				
				if(InputManager.returnHeight()) {
					Robot.EM.cancelMovement();
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(TRANSIT);
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
					setState(TRANSIT);
				}
				
				if (InputManager.eject()) {
					setState(DUMP);
				}
				if(InputManager.switchHeight()) {
					Robot.EM.cancelMovement();
					Robot.EM.startMovement(Konstanten.SWITCH_HEIGHT);
					setState(SWITCH);
				}
				
				if(InputManager.returnHeight()) {
					Robot.EM.cancelMovement();
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(TRANSIT);
				}
				break;
			case DUMP:
				//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE DUMPING
				//IF USER INPUT IS DETECTED, GO TO RETURING
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.IM.setState(IntakeManager.STOP);
				Robot.EM.update();
				Robot.IM.update();
				Robot.CM.update();
				
				if (InputManager.returnHeight()){
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(RETURNING);
				}
				if (InputManager.intake()){
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
				break;
			case RETURNING:
				Robot.IM.setState(IntakeManager.STOP);
				Robot.IM.setState(IntakeManager.RAISE_INTAKE);
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				//ELEVATOR RETURNING, INTAKE INACTIVE,CARRIAGE INACTIVE
				if (InputManager.intake() || InputManager.spit()) {
					Robot.EM.startMovement(Konstanten.RETURN_HEIGHT);
					setState(INTAKE);
				}
				//GO TO DRIVING
				break;
			case RESET:
				Robot.IM.setState(IntakeManager.RAISE_INTAKE);
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.EM.setState(ElevatorManager.STOP);
				Robot.IM.update();
				Robot.CM.update();
				Robot.EM.update();
				break;
		}
	}
}