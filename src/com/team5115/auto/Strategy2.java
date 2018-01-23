package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;
import com.team5115.statemachines.StateMachineBase;

//if we're in front of our side of the switch, drop a cube, otherwise, cross the auto line
public class Strategy2 extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1;
	public static final int PLACE = 2;
	public static final int FINISHED = 3;
	//ONSIDE means robot amd switch are on the same side
	//OfFIDE means robt and switch are on different sides

	AutoDrive drive;
	int position;
	int switchPosition;
	
	public Strategy2(int p, int sp) {
		drive = new AutoDrive();
		
		position = p;
		switchPosition = sp;
	}
	public void update() {
		switch(state){
		case INIT:
			drive.startLine(11.6, 0.25);
			setState(DRIVING);
			break;
		case DRIVING:
			if(drive.state == AutoDrive.FINISHED){
				if(position == switchPosition) {
					setState(PLACE);
				}
				else {
					setState(FINISHED);
				}
			}
			drive.update();
			break;
		case PLACE:
			Robot.CMM.setState(Robot.CMM.EJECT);
			break;
		case FINISHED:
			break;
		}
	}
}
