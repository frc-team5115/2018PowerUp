package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

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
        		Robot.IM.setState(Robot.IM.STOP);
        		Robot.CM.setState(Robot.CM.STOP);
        		Robot.EM.setState(Robot.EM.STOP);
        		if (InputManager.intake())
        			setState(INTAKE);
        		break;
        	case INIT:
        		break;
        	case INTAKE:
        		//ELEVATOR DOWN, INTAKEACTIVE,CARRIAGE ARMED
        		// IF SENSOR IMPUT IS RECOGNIZED, GO TO TRANSIT
        		Robot.IM.setState(Robot.IM.INTAKE);
        		Robot.CM.setState(Robot.CM.DUMP);
        		Robot.EM.setState(Robot.EM.RETURN);
        		Robot.IM.update();
        		Robot.CM.update();
        		Robot.EM.update();
        		if (Robot.intake.isCube())
        			setState(SWALLOW);
        		break;
        	case SPIT:
        		Robot.IM.setState(Robot.IM.SPIT);
        		break;
        	case SWALLOW:
        		//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE ACTIVE
        		//IF USER INPUT IS RECOGNIZED, GO TO DUMP
        		Robot.IM.setState(Robot.IM.GRIP);
        		Robot.EM.update();
        		Robot.IM.update();
        		Robot.CM.update();
        	
        		
        		setState(SWITCH);
        		break;
        	case TRANSIT:
        		Robot.CM.setState(Robot.CM.GRAB);
        		Robot.IM.setState(Robot.IM.RELEASE);
        		Robot.EM.update();
        		Robot.IM.update();
        		Robot.CM.update();
        		
        		if (InputManager.switchHeight())
        			setState(SWITCH);
        		else if (InputManager.scaleHeight())
        			setState(SCALE);
        	case SWITCH:
        		Robot.EM.setState(SWITCH);
        		Robot.IM.update();
        		Robot.CM.update();
        		Robot.EM.update();
        		
        		if (InputManager.eject())
        			setState(DUMP);
        		break;
        	case SCALE:
        		Robot.EM.setState(SCALE);
        		Robot.IM.update();
        		Robot.CM.update();
        		Robot.EM.update();
        		if (InputManager.eject())
        			setState(DUMP);
        		break;
        	case DUMP:
            	//ELEVATOR STILL, INTAKE INACTIVE, CARRIAGE DUMPING
        		//IF USER INPUTIS DETECTED,GO TO RETURING
        		Robot.CM.setState(Robot.CM.DUMP);
        		Robot.IM.setState(Robot.IM.STOP);
        		Robot.EM.update();
        		Robot.IM.update();
        		Robot.CM.update();
        		
        		if (InputManager.returnHeight())
        			setState(RETURNING);
            	break;
        	case RETURNING:
        		Robot.IM.setState(Robot.IM.STOP);
        		Robot.CM.setState(Robot.CM.STOP);
        		Robot.EM.setState(Robot.EM.RETURN);
        		Robot.IM.update();
        		Robot.CM.update();
        		Robot.EM.update();
        		//ELEVATOR RETURNING, INTAKE INACTIVE,CARRIAGE INACTIVE
        		//GO TO DRIVING
        		break;
        }
    }
}