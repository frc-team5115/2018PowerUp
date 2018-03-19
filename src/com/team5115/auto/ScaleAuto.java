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

//drop a cube in the correct side of the scale
public class ScaleAuto extends StateMachineBase {
	public static final int INIT = 0;
	public static final int DRIVING = 1;
	public static final int TURNING = 2;
	public static final int DRIVING2 = 3;
	public static final int TURNING2 = 4;
	public static final int DRIVING3 = 5;
	public static final int TURNING3 = 6;
	public static final int DRIVING4 = 7;
	public static final int FINISHED = 8;
	// Distances and Angles are not accurate, need to be changed later

	AutoDrive drive;
	
	double time;
	
	int scalePosition;
	int position;
	int left = 1;
	int right = 2;
	
	
	public ScaleAuto(int sp, int p) {
		drive = new AutoDrive();
		position = p;
		scalePosition = sp;
		//check 
	}
	
	public void update() {
		SmartDashboard.putNumber("state", state);
		switch(state){
		case INIT:
			Robot.CMM.setState(CubeManipulatorManager.STOP);
			drive.startLine(12, 0.5); //distance that is going to be required every time
			Robot.CM.setState(CarriageManager.GRAB);
			Robot.IM.setState(IntakeManager.STOW_OPEN);
			Robot.EM.setTarget(Konstanten.SCALE_HEIGHT);
			Robot.EM.setState(ElevatorManager.MOVING_TO);
			setState(DRIVING);
			break;
			
		case DRIVING:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			Robot.CMM.collisionAvoidance();
			if(drive.state == AutoDrive.FINISHED){
				Robot.EM.setState(ElevatorManager.STOP);
				if (position == scalePosition){
					if (position == left){
						drive.startTurn(27, .5);
					}
					else {
						drive.startTurn(-27, .5);
					}
					setState(TURNING2);
				}
				else if (position == left){
					//drive.startTurn(90, .5);
					//setState(TURNING);
					setState(FINISHED);
				}
				else {//position = right
					//drive.startTurn(-90, .5);
					//setState(TURNING);
					setState(FINISHED);
				}
			}
			break;
			
		case TURNING:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
				drive.startLine(19.4,  0.5);
				setState(DRIVING2);
			}
			break;
			
		case DRIVING2:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
				if (position == left){
					drive.startTurn(-117, .5);
				}
				else {
					drive.startTurn(117, .5);
				}
				setState(TURNING2);
			}
			break;
			
		case TURNING2:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
				drive.startLine(2, 0.25);
				setState(DRIVING4);
			}
			break;

			
		case DRIVING4:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
				System.out.println("last leg finished");
				Robot.drivetrain.drive(0, 0);
				Robot.CM.setState(CarriageManager.DUMP);
				Robot.IM.setState(IntakeManager.STOP);
				Robot.EM.setState(ElevatorManager.STOP);
			}
			break;
			
		case FINISHED:
			Robot.drivetrain.drive(0, 0);
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			break;
		}
	
	}
}
