package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Constantos;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;
import com.team5115.statemachines.CubeManipulatorManager;
import com.team5115.statemachines.StateMachineBase;

//drop a cube in the correct side of the switch
public class Strategy1 extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1;		//2 ft
	public static final int TURNING = 2;	//45 degrees
	public static final int DRIVING2 = 3;		//6.4 ft
	public static final int TURNING2 = 4;	//-45 degrees
	public static final int DRIVING3 = 5;		//4.8 ft
	public static final int PLACE = 6;
	public static final int FINISHED = 7;

	AutoDrive drive;

	
	
	int switchPosition;
	
	int left = 1;
	int right = 2;
	
	public Strategy1(int sp) {
		drive = new AutoDrive();

		switchPosition = sp;
	}
	public void update() {
		System.out.println("state: " + state);
		switch(state){
		case INIT:
			drive.startLine(2, 0.5);
			setState(DRIVING);
			break;
		case DRIVING:
			drive.update();
			if(drive.state == AutoDrive.FINISHED){
				if(switchPosition == left) {
					drive.startTurn(45, 0.25);
				}
				else {
					drive.startTurn(-45, 0.25);
				}
				setState(TURNING);
			}
			break;
			
		case TURNING:
			drive.update();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startLine(6.4, 0.5);
				}
				else {
					drive.startLine(7.75, 0.5);
				}
			
				setState(DRIVING2);
			}
			break;
			
		case DRIVING2:
			drive.update();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startTurn(-45, 0.25);
				}
				else {
					drive.startTurn(45, 0.25);
				}
				setState(TURNING2);
			}
			break;
					
		case TURNING2:
			drive.update();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startLine(3.37, 0.25);
				}
				else {
					drive.startLine(4.04, 0.25);
				}
			
				setState(DRIVING3);
			}
			break;
		case DRIVING3:
			drive.update();
			if (drive.state == AutoDrive.FINISHED) { 
				Robot.CMM.setState(CubeManipulatorManager.DUMP);
				setState(PLACE);
			}
			break;
		case PLACE:
			Robot.CMM.update();
			setState(FINISHED);
			break;
			
		case FINISHED:
			break;
		}
	}
}
