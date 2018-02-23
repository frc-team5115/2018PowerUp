//Importing things so we can use frequently used values in constant, access to the PID function, etc.
package com.team5115.auto;

import com.cruzsbrian.robolog.Log;
import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;
import com.team5115.statemachines.SwitchCross;
import com.team5115.auto.AutoDrive;
import com.team5115.systems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//create class that other files in autoDrive can access, extends to StateMachineBase to enable setting and creating states
public class Auto extends StateMachineBase{
	
	//DRIVING returns 1 in the switch block, FINISH returns 2.
	public static final int INIT = 1;
	public static final int SWITCH = 2;
	public static final int SCALE = 3;
	public static final int LINE = 4;
	public static final int DROPCUBE = 5;
	public static final int NOTHINGTODO = 6;
	public static final int SWITCHCROSS = 7;
	public static SwitchAutoCenter switchStrat;
	public static ScaleAuto scaleStrat;
	public static DropCubeAuto dropCubeStrat;
	public static LineAuto lineStrat;
	public static NothingToSeeHereAuto NothingToSeeHere;
	public static SwitchCross SwitchCross;
	//create new object imported from AutoDrive called "drive"
	int position;
	int switchPosition;
	int scalePosition;
	int strategy;
	public Auto(int p, int sp, int scp, int s) {
		position = p;
		switchPosition = sp;
		scalePosition = scp;
		strategy = s;

		switchStrat = new SwitchAutoCenter(switchPosition);
		scaleStrat = new ScaleAuto(scalePosition, position);
		dropCubeStrat = new DropCubeAuto(position, switchPosition);
		lineStrat = new LineAuto();
		NothingToSeeHere = new NothingToSeeHereAuto();
		SwitchCross = new SwitchCross(position, switchPosition);
	}
	
	//each time update is called in AutoDrive
	 public void update () {
		 //Run switch block and check for number
			switch (state) {
				case INIT:
					
					//Get strategy numberfrom smart dashboard
					switchStrat.setState(SwitchAutoCenter.INIT);
					scaleStrat.setState(ScaleAuto.INIT);
					dropCubeStrat.setState(DropCubeAuto.INIT);
					lineStrat.setState(LineAuto.INIT);
					NothingToSeeHere.setState(NothingToSeeHereAuto.INIT);
					SwitchCross.setState(SwitchCross.INIT);
					setState(strategy);
					break;
					
				case SWITCH:
					switchStrat.update();
					break;
				case SCALE:
					scaleStrat.update();
					break;
				case LINE:
					lineStrat.update();
					break;
				case DROPCUBE:
					dropCubeStrat.update();
					break;
				case NOTHINGTODO:
					NothingToSeeHere.update();
					break;
				case SWITCHCROSS:
					SwitchCross.update();
			}
	 }
}


	
	