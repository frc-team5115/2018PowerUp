package com.team5115.auto;

import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;
import com.team5115.statemachines.CarriageManager;
import com.team5115.statemachines.CubeManipulatorManager;
import com.team5115.statemachines.ElevatorManager;
import com.team5115.statemachines.IntakeManager;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;
//Deprecated. Use SwitchAutoSide instead.
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
				drive.startLine(9, 0.125);
				Robot.EM.setTarget(Konstanten.SWITCH_HEIGHT);
				Robot.EM.setState(ElevatorManager.MOVING_TO);
			}
			else {
				drive.startLine(2, .25);
				Robot.EM.setState(ElevatorManager.STOP);
			}
			Robot.IM.setState(IntakeManager.STOW_CLOSED);
			Robot.CM.setState(CarriageManager.GRAB);
			setState(DRIVING);
			break;
		case DRIVING:
			drive.update();
			Robot.CM.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CMM.collisionAvoidance();
			if(drive.state == AutoDrive.FINISHED){
				if(position == RIGHT) {
					if(position == switchPosition) {
						Robot.CM.setState(CarriageManager.DUMP);
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
			Robot.CM.update();
			Robot.EM.update();
			Robot.IM.update();
			drive.update();
			if (drive.state == AutoDrive.FINISHED) {
				drive.startLine(5.08, .25);
				Robot.EM.setTarget(Konstanten.SWITCH_HEIGHT);
				Robot.EM.setState(ElevatorManager.MOVING_TO);
				setState(DRIVING2);
			}
			break;
		case DRIVING2:
			Robot.CM.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CMM.collisionAvoidance();
			drive.update();
			if (drive.state == AutoDrive.FINISHED) {
				if(position == switchPosition) {
					Robot.CM.setState(CarriageManager.DUMP);
					setState(PLACE);
				}
				else {
					setState(FINISHED);
				}
			}
			break;
		case PLACE:
			Robot.CM.update();
			Robot.EM.update();
			if(Timer.getFPGATimestamp() > time + Konstanten.SPIT_DELAY)
				setState(FINISHED);
			break;
		case FINISHED:
			break;
		}
	}
}
