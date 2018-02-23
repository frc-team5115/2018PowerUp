package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Konstanten;
import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class IntakeManager extends StateMachineBase {
	public static final int STOP = 0;
    public static final int INTAKE = 1;
    public static final int CORRECT = 2;
    public static final int LOWER_INTAKE = 3;
    public static final int RAISE_INTAKE = 4;
    public static final int SPIT = 5;
    public static final int GRIP = 6;
    public static final int RELEASE = 7;

    PID turnController;
    
    double time;
    
    public void setState(int s) {
    	switch (state) {
    	case INTAKE:
    		Robot.intake.lowerIntake();
    		time = Timer.getFPGATimestamp();
    		break;
    	case GRIP:
    		Robot.intake.grip();
    		time = Timer.getFPGATimestamp();
    		break;
    	}
    	state = s;
    }
    
    public void update() {
	   switch (state) {
	   	case STOP:
	   		Robot.intake.relax();
	   		Robot.intake.intake(0);
	   		break;
	   	case INTAKE:
	   		Robot.intake.relax();
		   	Robot.intake.intake(Konstanten.INTAKE_SPEED);
	   		break;
	   	case CORRECT:
	   		Robot.intake.relax();
		   	Robot.intake.bump();
	   		break;
	   	case LOWER_INTAKE:
	   		Robot.intake.lowerIntake();
	   		break;
//	   	case RAISE_INTAKE:
//	   		Robot.intake.liftIntake();
//	   		break;
	   	case SPIT:
	   		Robot.intake.relax();
	   		Robot.intake.intake(-Konstanten.INTAKE_SPEED);
	   		break;
	   	case GRIP:
	   		Robot.intake.grip();
	   		Robot.intake.intake(0.25);
	   		Robot.intake.liftIntake();
	   		break;
	   	case RELEASE:
	   		Robot.intake.release();
	   		Robot.intake.liftIntake();
	   		Robot.intake.intake(0);
	   		break;
	   }
    }
    
}