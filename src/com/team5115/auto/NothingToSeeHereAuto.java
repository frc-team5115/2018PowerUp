package com.team5115.auto;

import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

//do nothing
public class Strategy4 extends StateMachineBase {
	public static final int INIT = 0;
	public static final int FINISHED = 2;
	public void update() {
		switch(state){
		case INIT:
			setState(FINISHED);
			break;
		case FINISHED:
			Robot.drivetrain.drive(0, 0);
			break;
		}
	}
}