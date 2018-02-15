//Importing things so we can use frequently used values in constant, access to the PID function, etc.
package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//create class that other files in autoDrive can access, extends to StateMachineBase to enable setting and creating states
public class DriveForwardSome extends StateMachineBase{
	
	//DRIVING returns 1 in the switch block, FINISH returns 2.
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int TURNING = 3;
	public static final int DRIVING2 = 4;
	public static final int FINISHED = 5;
	//create new object imported from AutoDrive called "drive"
	AutoDrive drive;
	
	public DriveForwardSome() {
		drive = new AutoDrive();
	}
	
	//each time update is called in AutoDrive
	 public void update () {
		 //Run switch block and check for number
			switch (state) {
				case INIT:
					//drive.startLine(10, .5);
					//setState(TURNING);
					drive.startArc(4, 180, .125);
					setState(DRIVING);
					break;
					
			//when in case driving
				case DRIVING:
					if(drive.state == AutoDrive.FINISHED){
						//drive2.startLine(10.0, 0.25);
						setState(FINISHED);
						//setState(FINISHED);
		   
					}
					drive.update();
					//System.out.println("dist " + Robot.drivetrain.distanceTraveled());
					//System.out.println("DRIVING");
					SmartDashboard.putNumber("left speed", Robot.drivetrain.leftSpeed());
					SmartDashboard.putNumber("right speed", Robot.drivetrain.rightSpeed());
					break;

				case FINISHED:
					Robot.drivetrain.drive(0,0);
					break;
			}
	 }
}


	
	