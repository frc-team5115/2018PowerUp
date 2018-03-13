package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
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

//drop a cube in the correct side of the switch
public class SwitchAutoCenter extends StateMachineBase {
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
	
	public SwitchAutoCenter(int sp) {
		drive = new AutoDrive();
		switchPosition = sp;
	}
	
	public void setState(int s) {
    	switch (s) {
    	case DRIVING3:
    		time = Timer.getFPGATimestamp();
    		break;
    	case PLACE:
    		time = Timer.getFPGATimestamp();
    		break;
    	}
    	state = s;
    }
	
	protected void updateChildren() {
		drive.update();
		Robot.EM.update();
		Robot.IM.update();
		Robot.CM.update();
	}
	
	public void update() {
		switch(state){
		case INIT:
			drive.startLine(2, 0.5);
			Robot.EM.setState(ElevatorManager.STOP);
			Robot.IM.setState(IntakeManager.STOW_CLOSED);
			Robot.CM.setState(CarriageManager.GRAB);
			setState(DRIVING);
			break;
		case DRIVING:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED){
				if(switchPosition == left) {
					drive.startTurn(-45, 0.4);
				}
				else {
					drive.startTurn(45, 0.4);
				}
				Robot.CMM.setState(CubeManipulatorManager.TRANSIT);
				setState(TURNING);
			}
			break;
			
		case TURNING:
			updateChildren();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startLine(6.4, 0.5);
				}
				else {
					drive.startLine(6.4, 0.5);
				}
				Robot.EM.setTarget(Konstanten.SWITCH_HEIGHT);
				Robot.EM.setState(ElevatorManager.MOVING_TO);
				setState(DRIVING2);
			}
			break;
			
		case DRIVING2:
			updateChildren();
			Robot.CMM.collisionAvoidance();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startTurn(45, 0.4);
				}
				else {
					drive.startTurn(-45, 0.4);
				}
				setState(TURNING2);
			}
			break;
					
		case TURNING2:
			updateChildren();
			if (drive.state == AutoDrive.FINISHED) { 
				if(switchPosition == left) {
					drive.startLine(2.37, 0.5);
				}
				else {
					drive.startLine(2.04, 0.5);
				}
			
				setState(DRIVING3);
			}
			break;
		case DRIVING3:
			updateChildren();
			if (drive.state == AutoDrive.FINISHED || Timer.getFPGATimestamp() - time > 2) { 
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.drivetrain.drive(0, 0);
				setState(PLACE);
			}
			break;
		case PLACE:
			updateChildren();
			if(Timer.getFPGATimestamp() >= time + Konstanten.SPIT_DELAY)
				setState(FINISHED);
			break;
			
		case FINISHED:
			break;
		}
	}
}
