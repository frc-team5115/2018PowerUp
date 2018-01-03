package com.team5115.auto;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDrive extends StateMachineBase {

    public static final int DRIVING = 1;
    public static final int FINISHED = 2;

    double targetDist;
    double targetAngle;

    PID forwardController;
    PID turnController;

    public void startLine(double dist, double maxSpeed) {
        targetDist = Robot.drivetrain.distanceTraveled() + dist;
        targetAngle = Robot.drivetrain.getYaw();

        
        //Change back to our constants, this one doesn't work
        forwardController = new PID(Constants.AUTO_FORWARD_KP, Constants.AUTO_FORWARD_KI, Constants.AUTO_FORWARD_KD, maxSpeed);
        turnController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD, maxSpeed);
        setState(DRIVING);
    }

    public void startTurn(double angle, double maxSpeed) {
        targetDist = Robot.drivetrain.distanceTraveled();
        targetAngle = Robot.drivetrain.getYaw() + angle;

        forwardController = new PID(Constants.AUTO_FORWARD_KP, Constants.AUTO_FORWARD_KI, Constants.AUTO_FORWARD_KD, maxSpeed);
        turnController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD, maxSpeed);


        setState(DRIVING);
    }

    public void update() {
        switch (state) {
            case DRIVING:
                Robot.drivetrain.inuse = true;

        		
        		
                // run every Constants.getAsDouble()DELAY seconds while driving
                double vForward = forwardController.getPID(targetDist, Robot.drivetrain.distanceTraveled(), Robot.drivetrain.averageSpeed());

                double clearYaw = clearSteer(Robot.drivetrain.getYaw(), targetAngle);
                double vTurn = turnController.getPID(targetAngle, clearYaw, Robot.drivetrain.getTurnVelocity());

                Robot.drivetrain.drive(vForward, vTurn);
                System.out.println("vForward " + vForward);
                System.out.println("distance travelled:  " + Robot.drivetrain.distanceTraveled());
                
                SmartDashboard.putNumber("distance traveled", Robot.drivetrain.distanceTraveled());
                SmartDashboard.putNumber("velocity", Robot.drivetrain.averageSpeed());
                
                
//                System.out.println("vTurn " + vTurn);
                
                if (forwardController.isFinished(Constants.FORWARD_TOLERANCE, Constants.FORWARD_DTOLERANCE) && turnController.isFinished(Constants.TURN_TOLERANCE, Constants.TURN_DTOLERANCE)) {
                    Robot.drivetrain.drive(0, 0);
                    setState(FINISHED);
                }

                break;
            case FINISHED:
                Robot.drivetrain.inuse = false;
            	Robot.drivetrain.drive(0, 0);
            	break;
        }
    }

    private double clearSteer(double yaw, double target) {
        if (Math.abs(target - yaw) > Math.PI) {
            if (target < Math.PI) {	// yaw must be too high for target
                yaw -= Math.PI * 2;
            } else {	// yaw must be too low for target
                yaw += Math.PI * 2;
            }
        }

        return yaw;
    }

}
