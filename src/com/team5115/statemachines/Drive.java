package com.team5115.statemachines;

//import com.cruzsbrian.robolog.Constants;
import com.team5115.Constants;

import com.team5115.PID;
import com.team5115.robot.InputManager;
import com.team5115.robot.Robot;

public class Drive extends StateMachineBase {

	public static final int STOP = 0;
    public static final int DRIVING = 1;

    PID turnController;
    public void setState(int s) {
        switch (s) {
            case DRIVING:

                // run once when entering DRIVING state
                // construct the PID every time we start driving in case constants have changed
                //turnController = new PID(Constants.getAsDouble("turn_kp"), Constants.getAsDouble("turn_ki"), 0);
                turnController = new PID(Constants.TURN_KP, Constants.TURN_KI, 0);

        }
        
        state = s;
    }

    public void update() {
        switch (state) {
            case STOP:
                Robot.drivetrain.drive(0, 0);
                break;

            case DRIVING:
           
                if (!Robot.drivetrain.inuse) {
                    // find desired forward and turning speeds in ft/s
                    double forwardSpeed = InputManager.getForward() * InputManager.getThrottle() * Constants.TOP_SPEED;
                    double turnSpeed = InputManager.getTurn() * InputManager.getThrottle() * Constants.TOP_TURN_SPEED;
                    System.out.println(InputManager.getForward());

                    // open loop control for forward
                    // vForward is negative because y on the joystick is reversed
                    double vForward = forwardSpeed * Constants.FORWARD_KF;
                    //System.out.println(forwardSpeed);
                    System.out.println(turnSpeed);
                    
                    Robot.drivetrain.drive(forwardSpeed, turnSpeed);
                }

        }
    }

}
