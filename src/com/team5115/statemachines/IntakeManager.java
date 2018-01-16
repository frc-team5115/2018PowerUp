package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class IntakeManager extends StateMachineBase {

	public static final int STOP = 0;
    public static final int INTAKE = 1;

    

    PID turnController;
    public void setState(int s) {
        switch (s) {
        	case STOP:
        		
        		break;
        	case INTAKE:
        		
        		break;
        }
    }
}