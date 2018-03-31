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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//starts on a side and goes to the scale if the scale is ours, or the switch if the switch is ours, or cross if neither are ours. prefer switch if both are ours
public class FastSideAuto_Switch extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1; //11.6 ft
	public static final int TURNING = 2;
	public static final int DRIVING2 = 3;
	public static final int TURNING2 = 4;
	public static final int DRIVING3 = 5;
	public static final int PLACE = 6;
	public static final int FINISHED = 7;
	public static final int SCALE_DRIVING = 8;
	
	public static final int LEFT  = 1;
	public static final int RIGHT = 2;
	
	AutoDrive drive;
	LinePlusTurn lpt;
	double time;
	int position;
	int switchPosition;
	int scalePosition;
	
	public FastSideAuto_Switch(int p, int sp, int scp) {
		drive = new AutoDrive();
		lpt = new LinePlusTurn();
		
		position = p;
		switchPosition = sp;
		scalePosition = scp;
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
		Robot.CMM.collisionAvoidance();
	}
	
	public void update() {
		SmartDashboard.putNumber("stateNumber ", state);
		switch(state){
		case INIT:
			Robot.EM.setState(ElevatorManager.MOVING_TO);
			Robot.IM.setState(IntakeManager.STOW_CLOSED);
			Robot.CM.setState(CarriageManager.GRAB);
			if(position == switchPosition){
				Robot.EM.setTarget(Konstanten.SWITCH_HEIGHT);
				drive.startLine(12, 0.75);
				setState(DRIVING);
			} else if (position == scalePosition) {
				Robot.EM.setTarget(Konstanten.SCALE_HEIGHT);
				if (position == LEFT) {
					// total dist, angle, dist to turn, forward speed, turning speed
					lpt.start(23, 30, 19, 0.75, 0.5);
				} else {
					lpt.start(23, -30, 19, 0.75, 0.5);
				}
				setState(SCALE_DRIVING);
			} else { //neither are ours, go for auto line
				Robot.EM.setTarget(Konstanten.RETURN_HEIGHT);
				drive.startLine(11.6, 0.75);
				setState(DRIVING);
			}
			break;
			
		case DRIVING:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED){
				if (position == switchPosition) {
					if (position == LEFT){
						drive.startTurn(90, .5);
					}
					else { //position == right
						drive.startTurn(-90, .5);
					}
					setState(TURNING);
					
				} else { //neither are ours, go for auto line
					setState(FINISHED);
				}
			}
			break;
			
		case TURNING:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED){
				if (position == switchPosition) {
					drive.startLine(2.5, 0.25);
				}
				setState(DRIVING2);
				time = Timer.getFPGATimestamp();
			}
			break;
			
		case DRIVING2:
			updateChildren();
			if(drive.state == AutoDrive.FINISHED || Timer.getFPGATimestamp() >= time + 4){
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.drivetrain.drive(0, 0);
				setState(PLACE);
			}
			break;
				
		case PLACE:
				updateChildren();
				setState(FINISHED);
				break;
				
		case FINISHED:
			updateChildren();
			Robot.drivetrain.drive(0, 0);
			break;
			
		case SCALE_DRIVING:
			updateChildren();
			if (lpt.state == LinePlusTurn.FINISHED) {
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.drivetrain.drive(0, 0);
				setState(PLACE);
			}
			break;
			
		}
	}
}