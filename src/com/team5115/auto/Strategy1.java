package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;
import com.team5115.statemachines.StateMachineBase;

//drop a cube in the correct side of the switch
public class Strategy1 extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVINGFORWARD = 1;
	public static final int TURNINGRIGHT = 2;
	public static final int TURNINGLEFT = 3;
	public static final int DRIVINGFORWARDAGAIN = 4;
	public static final int TURNINGLEFTAGAIN = 5;
	public static final int TURNINGRIGHTAGAIN = 6;
	public static final int DRIVINGFORWARDAGAINAGAIN = 7;
	public static final int PLACE = 8;
	public static final int FINISHED = 9;

	AutoDrive drive;
	AutoDrive turn;
	int position;
	int switchPosition;
	
	int left = 1;
	int right = 2;
	
	public Strategy1(int p, int sp) {
		drive = new AutoDrive();
		turn = new AutoDrive();
		
		position = p;
		switchPosition = sp;
	}
	public void update() {
		switch(state){
		case INIT:
			drive.startLine(11.6, 0.25);
			setState(DRIVINGFORWARD);
			break;
		case DRIVINGFORWARD:
			if(drive.state == AutoDrive.FINISHED){
				if(switchPosition == left) {
					setState(TURNINGLEFT);
				}
				else {
					setState(TURNINGRIGHT);
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
