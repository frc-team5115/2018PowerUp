package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class CubeManipulatorManager extends StateMachineBase {

	public static final int STOP = 0;
	public static final int INIT = 1;
    public static final int INTAKE = 2;
    public static final int TRANSIT = 3;
    public static final int LIFTING= 4; 
    public static final int HOLD= 5;
    public static final int EJECT = 6;
    public static final int  RETURNING= 7;

    

    PID turnController;
    public void setState(int s) {
        switch (s) {
        	case STOP:
        		//EVERYTHING INACTIVE
        		break;
        	case INIT:
        		
        		break;
        	case INTAKE:
        		//ELEVATOR DOWN, INTAKEACTIVE,CARRIAGE ARMED
        		// IF SENSOR IMPUT IS RECOGNIZED, GO TO TRANSIT
        		break;
        	case TRANSIT :
        		//ELEVATOR DOWN, INTAKE INACTIVE, CARRIAGE ACTIVE
        		//IF A USER INPUT IS RECOGNIZED, GO TO LIFTING
        		
        		break;
        	case LIFTING:
        		//ELEVATOR MOVING, INTAKE INACTIVE, CARRIAGE ACTIVE
        		//IF SENSOR INPUT IS RECOGNZED,GO TO HOLD
        		break;
        	case HOLD:
        		//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE ACTIVE
        		//IF USER INPUT IS RECOGNIZED, GO TO EJECT
        		break;
        	case  EJECT:
            	//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE EJECTING
        		//IF USER INPUTIS DETECTED,GO TO RETURING
            	break;
        	case RETURNING:
        		//ELEVATOR RETURNING, INTAKE INACTIVE,CARRIAGE INACTIVE
        		//GO TO DRIVING
        		break;
        }
    }
}