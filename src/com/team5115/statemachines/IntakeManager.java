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
    public static final int SPIT = 3;
    public static final int GRIP_DOWN = 4;
    public static final int GRIP_UP = 5;
    public static final int STOW_OPEN = 6;
    public static final int STOW_CLOSED = 7;

    PID turnController;
    
    public void update() {
	   switch (state) {
	   	case STOP:
	   		Robot.intake.relax();
	   		Robot.intake.intake(0);
	   		Robot.intake.liftIntake();
	   		break;
	   	case INTAKE:
	   		Robot.intake.relax();
		   	Robot.intake.intake(Konstanten.INTAKE_SPEED);
		   	Robot.intake.lowerIntake();
	   		break;
	   	case CORRECT:
	   		Robot.intake.relax();
		   	Robot.intake.bump();
		   	Robot.intake.lowerIntake();
	   		break;
	   	case SPIT:
	   		Robot.intake.relax();
	   		Robot.intake.intake(Konstanten.SPIT_SPEED);
	   		break;
	   	case GRIP_DOWN:
	   		Robot.intake.grip();
	   		Robot.intake.intake(0.1);
	   		Robot.intake.lowerIntake();
	   		break;
	   	case GRIP_UP:
	   		Robot.intake.grip();
	   		Robot.intake.intake(0.25);
	   		Robot.intake.liftIntake();
	   		break;
	   	case STOW_OPEN:
	   		Robot.intake.release();
	   		Robot.intake.intake(0);
	   		Robot.intake.liftIntake();
	   		break;
	   	case STOW_CLOSED:
	   		Robot.intake.grip();
	   		Robot.intake.intake(0);
	   		Robot.intake.liftIntake();
	   		break;
	   }
    }
    
}