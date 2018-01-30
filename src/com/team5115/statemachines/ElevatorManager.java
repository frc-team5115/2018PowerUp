package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constantos;

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
    
    public void startMovement(double angle){
    	targetAngle = angle;
    	movement = new PID(Constantos.ARM_KD, Constantos.ARM_KI, Constantos.ARM_KD);
    	state = MOVING_TO;
    }
    
    public void cancelMovement(){
    	if (movement != null){
    		movement = null;
    		state = STOP;
    	}
    }
    
    public void update() {
    	angle = Robot.elevator.getAngle();
    	dAngle = Robot.elevator.getDAngle();
        switch (state) {
        	case STOP:
        		Robot.elevator.move(0);
//        		if(InputManager.moveUp() && !Robot.elevator.maxHeight()){
//        			state = MOVING_UP;
//        		}
//        		if(InputManager.moveDown() && !Robot.elevator.minHeight()){
//        			state = MOVING_DOWN;
//        		}
//        		if(InputManager.returnHeight()){
//        			startMovement(Constantos.RETURN_HEIGHT);
//        			state = MOVING_TO;
//        		}
//        		
        		
        		break;
        	case MOVING_UP:
        		Robot.elevator.move(1);
        		if(Robot.elevator.maxHeight()){
            		Robot.elevator.move(0);
        		}
        		break;
        	case MOVING_DOWN:
        		Robot.elevator.move(-1);
        		if(Robot.elevator.minHeight()){
            		Robot.elevator.move(0);
        		}
        		break;
        	case MOVING_TO:
        		output = movement.getPID(targetAngle, angle, dAngle);
        		Robot.elevator.move(output);
        		
        		if(movement.isFinished(Constantos.ARM_TOLERANCE, Constantos.ARM_DTOLERANCE)){
        			movement = null;
        			state = STOP;
        		}
//        		if(InputManager.moveUp() && !Robot.elevator.maxHeight()){
//        			movement = null;
//        			state = MOVING_UP;
//        		}
//        		if(InputManager.moveDown() && !Robot.elevator.minHeight()){
//        			movement = null;
//        			state = MOVING_DOWN;
//        		}
        		
        		break;
        }
    }
}