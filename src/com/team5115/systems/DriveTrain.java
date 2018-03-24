package com.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import com.team5115.Konstanten;
import com.team5115.robot.Robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;


public class DriveTrain {
	double max = 0;
	double min = 0;
	double lastValue = 0;
	public boolean inuse;

	TalonSRX frontleft;
	TalonSRX frontright;
	TalonSRX backleft;
	TalonSRX backright;
	AHRS navx;
	
	public double lastLeftSpeed = 0;
	public double lastRightSpeed = 0;

	public int direction;
	
	public DriveTrain(){
		navx = new AHRS(SPI.Port.kMXP);
		frontleft = new TalonSRX(Konstanten.FRONT_LEFT_MOTOR_ID);
		frontright = new TalonSRX(Konstanten.FRONT_RIGHT_MOTOR_ID);
		backleft = new TalonSRX(Konstanten.BACK_LEFT_MOTOR_ID);
		backright = new TalonSRX(Konstanten.BACK_RIGHT_MOTOR_ID);

		frontright.set(ControlMode.Follower, Konstanten.BACK_RIGHT_MOTOR_ID);
		frontleft.set(ControlMode.Follower, Konstanten.BACK_LEFT_MOTOR_ID);
		
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

		if(Math.abs(leftspeed) > 1){
		 leftspeed = Math.signum(leftspeed);
		}
		if(Math.abs(rightspeed) > 1){
		 rightspeed = Math.signum(rightspeed);
		
		}
		System.out.println("left: " + leftspeed);
		System.out.println("right: " + rightspeed);
		backleft.set(ControlMode.PercentOutput, -leftspeed);
		backright.set(ControlMode.PercentOutput, rightspeed);
	}
	public double leftDist() {
		double leftDist = -direction * backleft.getSelectedSensorPosition(0);
		return leftDist / 1440 * 6 * Math.PI / 12;
	}
	
	public double rightDist() {
		System.out.println(backright.getSelectedSensorPosition(0));
		double rightDist = direction * backright.getSelectedSensorPosition(0);
		return rightDist / 1440 * 6 * Math.PI / 12;
	}
	
	public double distanceTraveled() {
		return rightDist();
	}

	public double leftSpeed() {
		double leftspeed = -backleft.getSelectedSensorVelocity(0);
		return ((leftspeed * 6 * Math.PI * 10) / (1440 * 12));
		
	}

	public double rightSpeed() {
		double rightspeed = backright.getSelectedSensorVelocity(0);
		return ((rightspeed * 6 * Math.PI * 10) / (1440 * 12));
	}

	
	public double averageSpeed() {
		return (leftSpeed() + rightSpeed()) / 2;
	}

	public double getPitch(){
		return navx.getPitch();
	}
	
	public double getRoll(){
		return navx.getRoll();
	}
	
	public double getYaw() {
		return navx.getYaw();	
	}

	public double getTurnVelocity() {
		return navx.getRate();	
	}
	public double forwarAccel(){
		return -navx.getRawAccelY(); //this way points forward on our robot
	}
	// This method resets the values given by the encoders to a default of 0
	public void resetEncoders() {
		backleft.setSelectedSensorPosition(0, 0, 5); //5 ms
		backright.setSelectedSensorPosition(0, 0, 5);
	}
	public void resetGyro(){
		navx.reset(); //takes some time
	}

}
