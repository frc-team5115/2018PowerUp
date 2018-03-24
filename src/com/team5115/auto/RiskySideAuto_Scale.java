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

//starts on a side and goes to whichever side of the switch is ours
public class RiskySideAuto_Scale extends StateMachineBase {
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
	
	public RiskySideAuto_Scale(int p, int sp) {
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

		}
	}
}
