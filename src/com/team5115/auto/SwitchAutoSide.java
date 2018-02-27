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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//starts on a side and goes to whichever side of the switch is ours
public class SwitchAutoSide extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1; //11.6 ft
	public static final int TURNING = 2;
	public static final int DRIVING2 = 3;
	public static final int TURNING2 = 4;
	public static final int DRIVING3 = 5;
	public static final int PLACE = 6;
	public static final int FINISHED = 7;
	
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
	
	AutoDrive drive;
	double time;
	int position;
	int switchPosition;
	
	public SwitchAutoSide(int p, int sp) {
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
	
	protected void updateChildren() {
		drive.update();
		Robot.EM.update();
		Robot.IM.update();
		Robot.CM.update();
	}
	
	public void update() {
		SmartDashboard.putNumber("stateNumber ", state);
		switch(state){
		case INIT:
			Robot.EM.setTarget(Konstanten.SWITCH_HEIGHT);
			Robot.EM.setState(ElevatorManager.MOVING_TO);
			Robot.IM.setState(IntakeManager.STOW_CLOSED);
			Robot.CM.setState(CarriageManager.GRAB);
			if(position == switchPosition){
				drive.startLine(14, 0.5);
			} else {
				drive.startLine(19, 0.5);
			}
			setState(DRIVING);
			break;
			
		case DRIVING:
			updateChildren();
			Robot.CMM.collisionAvoidance();
			if(drive.state == AutoDrive.FINISHED){
				if(position == RIGHT){
					drive.startTurn(-90, 0.25);
				} else{
					//Position is left
					drive.startTurn(90, 0.25);
				}
				Robot.EM.setState(STOP);
				setState(TURNING);
			}
			break;
			
		case TURNING:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED){
				if(position == switchPosition){
					drive.startLine(3.2, 0.25);
				}else{
					//Position != swith position
					drive.startLine(14.12, 0.5);
				}
				setState(DRIVING2);
			}
			break;
			
		case DRIVING2:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED){
				if(position == switchPosition){
					time = Timer.getFPGATimestamp();
					setState(PLACE);
				} else {
					if(position == RIGHT){
						drive.startTurn(-90, 0.25);
					} else {
						//Position = left
						drive.startTurn(90, 0.25);
					} 
					setState(TURNING2);
				}
			}
			break;
			
		case TURNING2:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED){
				drive.startLine(2.8, 0.25);
				setState(DRIVING3);
			}
			break;
			
		case DRIVING3:
			updateChildren();
			System.out.println("DRIVING3");
			System.out.println(drive.state);
			if(drive.state == AutoDrive.FINISHED){
				Robot.drivetrain.drive(0, 0);
				time = Timer.getFPGATimestamp();
				setState(PLACE);
			}
			break;
			
		case PLACE:
			Robot.drivetrain.drive(0, 0);
			Robot.CM.setState(CarriageManager.DUMP);
			updateChildren();
			if(Timer.getFPGATimestamp() > time + Konstanten.SPIT_DELAY){
				setState(FINISHED);
			}
			break;
			
		case FINISHED:
			Robot.drivetrain.drive(0, 0);
			break;
		}
	}
}
