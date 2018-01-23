//Importing things so we can use frequently used values in constant, access to the PID function, etc.
package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//create class that other files in autoDrive can access, extends to StateMachineBase to enable setting and creating states
public class Auto extends StateMachineBase{
	
	//DRIVING returns 1 in the switch block, FINISH returns 2.
	public static final int INIT = 1;
	public static final int STRATEGY1 = 2;
	public static final int STRATEGY2 = 3;
	public static final int STRATEGY3 = 4;
	public static final int STRATEGY4 = 5;
	public static Strategy2 s2;
	public static Strategy3 s3;
	public static Strategy4 s4;
	//create new object imported from AutoDrive called "drive"
	int position;
	int switchPosition;
	int strategy;
	public Auto(int p, int sp, int s) {
		position = p;
		switchPosition = sp;
		strategy = s;
		
		s2 = new Strategy2(position, switchPosition);
		s3 = new Strategy3();
		s4 = new Strategy4();
	}
	
	//each time update is called in AutoDrive
	 public void update () {
		 //Run switch block and check for number
	        switch (state) {
	        	case INIT:
	        		
	        		//Get strategy numberfrom smart dashboard
	        		s2.setState(Strategy2.INIT);
	        		s3.setState(Strategy3.INIT);
	        		s4.setState(Strategy4.INIT);
	        		setState(strategy);
	        		break;
	        		
	        	case STRATEGY1:
	        		//Get Left vs Right
	        		//Act accordingly
	        		break;
	        	case STRATEGY2:
	        		//Get robot position from smarrt dashboard
	        		//if in the correct spot drive forward and place
	        		s2.update();
	        		//else strategy 3
	        		break;
	        	case STRATEGY3:
	        		//drive forward
	        		s3.update();
        			break;
	        	case STRATEGY4:
	        		//do nothing
	        		s4.update();
        			break;
	        
	        }
	 }
}


    
	