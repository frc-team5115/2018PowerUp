package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class CarriageManager extends StateMachineBase {

	public static final int GRAB = 0;
    public static final int HOLD = 1;
    public static final int EJECT = 2; 
    

    PID turnController;
    public void setState(int s) {
        switch (s) {
        	case GRAB:
        		
        		break;
        	case HOLD:
        		
        		break;
        		
        	case EJECT:
        		
        		break;
        }
    }
}