package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class CarriageManager extends StateMachineBase {

	public static final int GRAB = 1;
    public static final int DUMP = 2; 
    
    

    PID turnController;
    public void update() {
        switch (state) {
        	case STOP:
        		Robot.carriage.stop();
        		break;
        	case GRAB:
        		Robot.carriage.grab();
        		break;
        	case DUMP:
        		Robot.carriage.eject();
        		break;
        }
    }
}