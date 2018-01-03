package com.team5115.systems;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.team5115.Constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain {

    public boolean inuse;

    CANTalon frontleft;
    CANTalon frontright;
    CANTalon backleft;
    CANTalon backright;

    public double lastLeftSpeed = 0;
    public double lastRightSpeed = 0;

    public int direction;
    
    AHRS navx;

    public DriveTrain(){
    	navx = new AHRS(SPI.Port.kMXP);

        frontleft = new CANTalon(Constants.FRONT_LEFT_MOTOR_ID);
        frontright = new CANTalon(Constants.FRONT_RIGHT_MOTOR_ID);
        backleft = new CANTalon(Constants.BACK_LEFT_MOTOR_ID);
        backright = new CANTalon(Constants.BACK_RIGHT_MOTOR_ID);

        frontleft.changeControlMode(CANTalon.TalonControlMode.Follower);
        frontright.changeControlMode(CANTalon.TalonControlMode.Follower);
        frontleft.set(backleft.getDeviceID());
        frontright.set(backright.getDeviceID());
        direction = 1;
        
        
    }

    /**
     * Drive the robot with given forward and angular speeds, as a % of vBus
     * @param speed
     * @param turn
     */
    public void drive(double speed, double turn){
        double leftspeed = speed + turn;
        double rightspeed = speed - turn;
//        System.out.println(leftspeed + " left");
//        System.out.println(rightspeed + " right");

        if(Math.abs(leftspeed) > 1){
            leftspeed = 1;
        }
        if(Math.abs(rightspeed) > 1){
            rightspeed = 1;
        }
        //System.out.println(leftspeed);
        
        backleft.set(leftspeed);
        backright.set(-rightspeed);
    }
	public double leftDist() {
		double leftDist = -direction * backleft.getPosition();
		return leftDist / 1440 * 4 * Math.PI / 12;
	}
	
	public double rightDist() {
		double rightDist = direction * backright.getPosition();
		return rightDist / 1440 * 4 * Math.PI / 12;
	}
	
	public double distanceTraveled() {
		return -(leftDist() + rightDist()) / 2;
	}

	public double leftSpeed() {
		double leftspeed = backleft.getSpeed();
	    return ((leftspeed * 4 * Math.PI * 10) / (1440 * 12));
	}
	 
	public double rightSpeed() {
		double rightspeed = backright.getSpeed();
	    return ((rightspeed * 4 * Math.PI * 10) / (1440 * 12));
	}
	 
	public double averageSpeed() {
		return (frontright.getSpeed() + frontleft.getSpeed()) / 2;
	}

    public double getYaw() {
        return navx.getYaw();
    }

    public double getTurnVelocity() {
        return navx.getRate();
    }

    // This method resets the values given by the encoders to a default of 0
    public void resetEncoders() {
        frontleft.setPosition(0);
        frontright.setPosition(0);
    }
    public void resetGyro(){
    	navx.reset();
    }
}
