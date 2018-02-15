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

//if we're in front of our side of the switch, drop a cube, otherwise, cross the auto line
public class DropCubeAuto extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1; //11.6 ft
	public static final int TURNING = 2;
	public static final int DRIVING2 = 3;
	public static final int PLACE = 4;
	public static final int FINISHED = 5;
	
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
	
	AutoDrive drive;
	double time;
	int position;
	int switchPosition;
	
	public DropCubeAuto(int p, int sp) {
		drive = new AutoDrive();
		
		position = p;
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
			if (position == RIGHT) {
				drive.startLine(10, 0.5);
			}
			else {
				drive.startLine(2, .5);
			}
			Robot.EM.startMovement(Konstanten.SWITCH_HEIGHT);
			Robot.CMM.setState(CubeManipulatorManager.SWITCH);
			setState(DRIVING);
			break;
		case DRIVING:
			drive.update();
			Robot.CMM.update();
			if(drive.state == AutoDrive.FINISHED){
				if(position == RIGHT) {
					if(position == switchPosition) {
						Robot.CMM.setState(CubeManipulatorManager.DUMP);
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
			Robot.CMM.update();
			drive.update();
			if (drive.state == AutoDrive.FINISHED) {
				drive.startLine(9.08, .25);
				setState(DRIVING2);
			}
			break;
		case DRIVING2:
			Robot.CMM.update();
			drive.update();
			if (drive.state == AutoDrive.FINISHED) {
				if(position == switchPosition) {
					Robot.CMM.setState(CubeManipulatorManager.DUMP);
					setState(PLACE);
				}
				else {
					setState(FINISHED);
				}
			}
			break;
		case PLACE:
			Robot.CMM.update();
			if(Timer.getFPGATimestamp() > time + Konstanten.DUMPING_DELAY)
				setState(FINISHED);
			break;
		case FINISHED:
			break;
		}
	}
}
