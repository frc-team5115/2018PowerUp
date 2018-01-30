package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constantos;
import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class IntakeManager extends StateMachineBase {
	public static final int STOP = 0;
    public static final int INTAKE = 1;
    public static final int SPIT = 2;
    public static final int GRIP = 3;
    public static final int RELEASE = 4;
    
    

    PID turnController;
    public void update() {
        switch (state) {
        	case STOP:
        		Robot.intake.relax();
        		Robot.intake.intake(0);
        		break;
        	case INTAKE:
        		Robot.intake.lowerIntake();
        		Timer.delay(.25);
        		Robot.intake.relax();
        		Robot.intake.intake(1);
        		break;
        	case SPIT:
        		Robot.intake.lowerIntake();
        		Timer.delay(.25);
        		Robot.intake.relax();
        		Robot.intake.intake(-1);
        		break;
        	case GRIP:
        		Robot.intake.grip();
        		Timer.delay(.25);
        		Robot.intake.liftIntake();
        		Robot.intake.intake(0);
        		break;
        	case RELEASE:
        		Robot.intake.release();
        		Robot.intake.liftIntake();
        		Robot.intake.intake(0);
        		break;
        }
    }
}