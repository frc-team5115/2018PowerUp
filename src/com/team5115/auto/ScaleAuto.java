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

//drop a cube in the correct side of the scale
public class ScaleAuto extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1;
	public static final int TURNING = 2;
	public static final int DRIVING2 = 3;
	public static final int TURNING2 = 4;
	public static final int DRIVING3 = 5;
	public static final int PLACE = 6;
	public static final int TURNING3 = 7;
	public static final int DRIVING4 = 8;
	public static final int TURNING4 = 9;
	public static final int PICKUP = 10;
	public static final int FINISHED = 11;
	// Distances and Angles are not accurate, need to be changed later

	AutoDrive drive;
	
	double time;
	
	int scalePosition;
	
	int left = 1;
	int right = 2;
	
	
	public ScaleAuto(int sp) {
		drive = new AutoDrive();

		scalePosition = sp;
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
				if(scalePosition == left) {
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
				if(scalePosition == left) {
					drive.startLine(6.4, 0.5);
				}
				else {
					drive.startLine(7.75, 0.5);
				}
				Robot.CMM.setState(CubeManipulatorManager.SWITCH);
				setState(DRIVING2);
			}
			break;
			
		case DRIVING2:
			drive.update();
			Robot.CMM.update();
			if (drive.state == AutoDrive.FINISHED) { 
				if(scalePosition == left) {
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
				if(scalePosition == left) {
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
			if (Timer.getFPGATimestamp() >= time + Konstanten.DUMPING_DELAY)
				drive.startTurn(180, 0.25);
				Robot.CMM.setState(CubeManipulatorManager.RETURNING);
				setState(TURNING3);
			break;
		case TURNING3:
			drive.update();
			Robot.CMM.update();
			if (drive.state == AutoDrive.FINISHED) {
				drive.startLine(5, 0.25);	//placeholder distance
				setState(DRIVING4);
			}
			break;
		case DRIVING4:
			drive.update();
			Robot.CMM.update();
			if (drive.state == AutoDrive.FINISHED) {
				drive.startTurn(45, 0.25);	//might be wrong direction
				Robot.CMM.setState(CubeManipulatorManager.INTAKE);
				setState(TURNING4);
			}
			break;
		case TURNING4:
			drive.update();
			Robot.CMM.update();
			if (drive.state == AutoDrive.FINISHED) {
				setState(PICKUP);
			}
			break;
		// Might have to add more driving and turning in order to line up with cubes past switch, currently tries to intake from the side
		// I don't know if that will work, so this might get even more complicated...
		case PICKUP:
			Robot.CMM.update();
			if (Robot.intake.isCube()) {
				setState(FINISHED);
			}
		case FINISHED:
			break;
		}
	}
}
