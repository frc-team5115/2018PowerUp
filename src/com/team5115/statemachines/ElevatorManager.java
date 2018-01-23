package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class ElevatorManager extends StateMachineBase {

	public static final int STOP = 0;
	public static final int RETURN = 1;
    public static final int SWITCH = 2;
    public static final int SCALE = 3;
    

    PID turnController;
    public void update() {
        switch (state) {
        	case STOP:
        		Robot.elevator.move(0);
        		break;
        	case RETURN:
        		Robot.elevator.moveTo(Constants.RETURN_HEIGHT);
        		break;
        	case SWITCH:
        		Robot.elevator.moveTo(Constants.SWITCH_HEIGHT);
        		break;
        	case SCALE:
        		Robot.elevator.moveTo(Constants.SCALE_HEIGHT);
        		break;
        }
    }
}