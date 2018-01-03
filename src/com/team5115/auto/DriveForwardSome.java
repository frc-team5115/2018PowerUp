//Importing things so we can use frequently used values in constant, access to the PID function, etc.
package com.team5115.auto;

import com.team5115.Constants;
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
	AutoDrive turn;
	AutoDrive drive2;
	
	public DriveForwardSome() {
		drive = new AutoDrive();
		turn = new AutoDrive();
		drive2 = new AutoDrive();
	}
	
	//each time update is called in AutoDrive
	 public void update () {
		 //Run switch block and check for number
	        switch (state) {
	        	case INIT:
	        		drive.startLine(10.0, .5);
	        		
	        		setState(DRIVING);
	        		break;
	        		
	        //when in case driving
	            case DRIVING:
	            	if(drive.state == AutoDrive.FINISHED){
	            		turn.startTurn(180, .5);
	            		setState(TURNING);
	       
	            	}
	            	drive.update();
	            	System.out.println("DRIVING");
	            	SmartDashboard.putNumber("left speed", Robot.drivetrain.leftSpeed());
	            	SmartDashboard.putNumber("right speed", Robot.drivetrain.rightDist());
	            	break;
	            case TURNING:
	            	if(turn.state == AutoDrive.FINISHED){
	            		drive2.startLine(10.0, .5);
	            		setState(DRIVING2);
	            	}
	            	System.out.println("TURNING");
	            	turn.update();
	            	break;
	            	
	            case DRIVING2:
	            	if(drive2.state == AutoDrive.FINISHED){
	            		setState(FINISHED);
	            	}
	            	drive2.update();
	            	System.out.println("DRIVING2");
	            	break;
	            	
	        }
	 }
}


    
	