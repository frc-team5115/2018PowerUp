package com.team5115.systems;

import com.cruzsbrian.robolog.Log;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import com.team5115.Constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain {

    public boolean inuse;

    TalonSRX frontleft;
	TalonSRX frontright;
	TalonSRX backleft;
	TalonSRX backright;
	
    public double lastLeftSpeed = 0;
    public double lastRightSpeed = 0;

    public int direction;
    
    AHRS navx;

    public DriveTrain(){
    	navx = new AHRS(SPI.Port.kMXP);

        frontleft = new TalonSRX(Constants.FRONT_LEFT_MOTOR_ID);
        frontright = new TalonSRX(Constants.FRONT_RIGHT_MOTOR_ID);
        backleft = new TalonSRX(Constants.BACK_LEFT_MOTOR_ID);
        backright = new TalonSRX(Constants.BACK_RIGHT_MOTOR_ID);

        frontright.set(ControlMode.Follower, Constants.BACK_RIGHT_MOTOR_ID);
        frontleft.set(ControlMode.Follower, Constants.BACK_LEFT_MOTOR_ID);
        
       backright.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
       backleft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        
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
        //System.out.println(leftspeed + " left");
        //System.out.println(rightspeed + " right");

        if(Math.abs(leftspeed) > 1){
            leftspeed = 1;
        }
        if(Math.abs(rightspeed) > 1){
            rightspeed = 1;
        }
        
        backleft.set(ControlMode.PercentOutput, -leftspeed);
        //backleft.set(0.1);
        backright.set(ControlMode.PercentOutput, rightspeed);
        //backright.set(0.1);
    }
	public double leftDist() {
		double leftDist = -direction * backleft.getSelectedSensorPosition(0);
		return leftDist / 1440 * 4 * Math.PI / 12;
	}
	
	public double rightDist() {
		double rightDist = direction * backright.getSelectedSensorPosition(0);
		return rightDist / 1440 * 4 * Math.PI / 12;
	}
	
	public double distanceTraveled() {
		return (leftDist() + rightDist()) / 2;
	}

	public double leftSpeed() {
		double leftspeed = backleft.getSelectedSensorVelocity(0);
	    return ((leftspeed * 4 * Math.PI * 10) / (1440 * 12));
	    
	}
	 
	public double rightSpeed() {
		double rightspeed = backright.getSelectedSensorVelocity(0);
	    return ((rightspeed * 4 * Math.PI * 10) / (1440 * 12));
	}
	 
	public double averageSpeed() {
		return (rightSpeed() + leftSpeed()) / 2;
	}

    public double getYaw() {
        return navx.getYaw() * (Math.PI / 180);
    }

    public double getTurnVelocity() {
        return navx.getRate();
    }

    // This method resets the values given by the encoders to a default of 0
    public void resetEncoders() {
        backleft.setSelectedSensorPosition(0, 0, 0); //5 ms
        backright.setSelectedSensorPosition(0, 0, 0);
    }
    public void resetGyro(){
    	navx.reset(); //takes some time
    }
}
