package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;
import com.team5115.statemachines.CubeManipulatorManager;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;

//drop a cube in the correct side of the switch
public class SwitchAuto extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1;		//2 ft
	public static final int TURNING = 2;	//45 degrees
	public static final int DRIVING2 = 3;		//6.4 ft
	public static final int TURNING2 = 4;	//-45 degrees
	public static final int DRIVING3 = 5;		//4.8 ft
	public static final int PLACE = 6;
	public static final int FINISHED = 7;

	AutoDrive drive;

	
	AutoDrive turn; 
	double time;
	
	int switchPosition;
	
	int left = 1;
	int right = 2;
	
	public SwitchAuto(int sp) {
		drive = new AutoDrive();

		switchPosition = sp;
	}
	
	public void setState(int s) {
    	switch (state) {
    	case PLACE:
    		time = Timer.getFPGATimestamp();
    		break;
    	}
    	state = s;
    }
	
	public void update() {
		switch(state){
		case INIT:
			drive.startLine(2, 0.5);
			Robot.CMM.setState(CubeManipulatorManager.STOP);
			setState(DRIVING);
			break;
		case DRIVING:
			drive.update();
			Robot.CMM.update();
			if(drive.state == AutoDrive.FINISHED){
				if(switchPosition == left) {
					drive.startTurn(45, 0.25);
				}
				else {
					drive.startTurn(-45, 0.25);
				}
				Robot.CMM.setState(CubeManipulatorManager.TRANSIT);
				setState(TURNING);
			}
			break;
			
		case TURNING:
			drive.update();
			Robot.CMM.update();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startLine(6.4, 0.5);
				}
				else {
					drive.startLine(7.75, 0.5);
				}
				Robot.EM.startMovement(Konstanten.SWITCH_HEIGHT);
				Robot.CMM.setState(CubeManipulatorManager.SWITCH);
				setState(DRIVING2);
			}
			break;
			
		case DRIVING2:
			drive.update();
			Robot.CMM.update();
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
			Robot.CMM.update();
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
			Robot.CMM.update();
			if (drive.state == AutoDrive.FINISHED) { 
				Robot.CMM.setState(CubeManipulatorManager.DUMP);
				setState(PLACE);
			}
			break;
		case PLACE:
			Robot.CMM.update();
			if(Timer.getFPGATimestamp() >= time + Konstanten.DUMPING_DELAY)
				setState(FINISHED);
			break;
			
		case FINISHED:
			break;
		}
	}
}
