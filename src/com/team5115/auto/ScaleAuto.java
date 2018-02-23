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
		switch(state){
		case INIT:
			Robot.CMM.setState(CubeManipulatorManager.STOP);
			drive.startLine(17.5, 0.5); //distance that is going to be required every time
			Robot.CM.setState(CarriageManager.GRAB);
			Robot.IM.setState(IntakeManager.RELEASE);
			Robot.EM.startMovement(Konstanten.SCALE_HEIGHT);
			setState(DRIVING);
			break;
			
		case DRIVING:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if ((Robot.elevator.getAngle() <  Konstanten.INTAKE_HEIGHT) && Robot.elevator.movingArm){
				Robot.IM.setState(IntakeManager.RELEASE);
			}
			else{
				Robot.IM.setState(IntakeManager.GRIP);
			}
			
			if(drive.state == AutoDrive.FINISHED){
				if (position == scalePosition){
					drive.startLine(8, 0.25);
					setState(DRIVING3);
				}
				else if (position == left){
					drive.startTurn(90, .5);
					setState(TURNING);
					
				}
				else {//position = right
					drive.startTurn(-90, .5);
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
					drive.startTurn(-90, .5);
				}
				else {
					drive.startTurn(90, .5);
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
				drive.startLine(8, 0.25);
				setState(DRIVING3);
			}
			break;
			
		case DRIVING3:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
				if (position == left){
					drive.startTurn(-90, .5);
				}
				else {
					drive.startTurn(90, .5);
				}
				setState(TURNING3);
			}
			break;
			
		case TURNING3:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
				drive.startLine(2.1,  0.25);
				setState(DRIVING4);
			}
			break;
			
		case DRIVING4:
			drive.update();
			Robot.EM.update();
			Robot.IM.update();
			Robot.CM.update();
			if (drive.state == AutoDrive.FINISHED){
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
