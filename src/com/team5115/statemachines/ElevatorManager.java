package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class ElevatorManager extends StateMachineBase {

	public static final int STOP = 0;
    public static final int UPPING = 1;
    public static final int DOWNING = 2;
    public static final int RETURN = 3;
    public static final int SWITCH = 4;
    public static final int SCALE = 5;
    

    PID turnController;
    public void setState(int s) {
        switch (s) {
        	case STOP:
        		//Robot.elevator.move(0);
        		if (InputManager.up()) {
        			state = UPPING;
        		}
        		if (InputManager.down()) {
        			state = DOWNING;
        		}
        		if (InputManager.returnHeight()) {
        			state = RETURN;
        		}
        		if (InputManager.switchHeight()) {
        			state = SWITCH;
        		}
        		if (InputManager.scaleHeight()) {
        			state = SCALE;
        		}
        		
        		break;
        	case UPPING:
        		
        		//	Robot.elevator.move(1);
        		if (!InputManager.up()) {
        			state = STOP;
        		}
        		break;
        	case DOWNING:
        		if (!InputManager.down()) {
        			state = STOP;
        		}
        		break;
        	case RETURN:
        		if (!InputManager.returnHeight()) {
        			state = STOP;
        		}
        		break;
        	case SWITCH:
        		if (!InputManager.switchHeight()) {
        			state = STOP;
        		}
        		break;
        	case SCALE:
        		if (!InputManager.scaleHeight()) {
        			state = STOP;
        		}
        		break;
        }
    }
}