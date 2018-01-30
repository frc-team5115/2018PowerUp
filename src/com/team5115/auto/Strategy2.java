package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Constantos;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;
import com.team5115.statemachines.CubeManipulatorManager;
import com.team5115.statemachines.StateMachineBase;

//if we're in front of our side of the switch, drop a cube, otherwise, cross the auto line
public class Strategy2 extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1; //11.6 ft
	public static final int TURNING = 2;
	public static final int DRIVING2 = 3;
	public static final int PLACE = 4;
	public static final int FINISHED = 5;
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
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
			if (position == RIGHT) {
				drive.startLine(10, 0.5);
			}
			else {
				drive.startLine(2, .5);

				
			}
			setState(DRIVING);
			break;
		case DRIVING:
			drive.update();
			if(drive.state == AutoDrive.FINISHED){
				if(position == RIGHT) {
					if(position == switchPosition) {
						setState(PLACE);
					}
					else {
						setState(FINISHED);
					}
				}
				else {	//position == LEFT
					drive.startTurn(11, .25);
					setState(TURNING);
				}
			}
			break;
		case TURNING:
			drive.update();
			if (drive.state == AutoDrive.FINISHED) {
				drive.startLine(9.08, .25);
				setState(DRIVING2);
			}
			break;
		case DRIVING2:
			drive.update();
			if (drive.state == AutoDrive.FINISHED) {
				if(position == switchPosition) {
					setState(PLACE);
				}
				else {
					setState(FINISHED);
				}
			}
			break;
		case PLACE:
			Robot.CMM.setState(CubeManipulatorManager.DUMP);
			break;
		case FINISHED:
			break;
		}
	}
}
