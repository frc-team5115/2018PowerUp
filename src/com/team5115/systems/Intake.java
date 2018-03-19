package com.team5115.systems;

import com.team5115.Konstanten;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Intake {

	DigitalInput cubeDetectorL;
	DigitalInput cubeDetectorR;
	DoubleSolenoid cubeSolenoidLeft;
	DoubleSolenoid cubeSolenoidRight;
	DoubleSolenoid intakeLifterLeft;
	DoubleSolenoid intakeLifterRight;
	Spark intakeWheelsLeft;
	Spark intakeWheelsRight;
	public Intake(){
		cubeDetectorL = new DigitalInput(Konstanten.CUBE_DETECTOR_L);
		cubeDetectorR = new DigitalInput(Konstanten.CUBE_DETECTOR_R);
		cubeSolenoidLeft = new DoubleSolenoid(Konstanten.PHEUMATIC_PCM_0_ID, Konstanten.INTAKE_FORWARD_CHANNEL_LEFT, Konstanten.INTAKE_REVERSE_CHANNEL_LEFT);
		cubeSolenoidRight = new DoubleSolenoid(Konstanten.PHEUMATIC_PCM_0_ID, Konstanten.INTAKE_FORWARD_CHANNEL_RIGHT, Konstanten.INTAKE_REVERSE_CHANNEL_RIGHT);
		intakeLifterLeft = new DoubleSolenoid(Konstanten.PHEUMATIC_PCM_0_ID, Konstanten.LIFTER_FORWARD_CHANNEL_LEFT, Konstanten.LIFTER_REVERSE_CHANNEL_LEFT);
		intakeLifterRight = new DoubleSolenoid(Konstanten.PHEUMATIC_PCM_0_ID, Konstanten.LIFTER_FORWARD_CHANNEL_RIGHT, Konstanten.LIFTER_REVERSE_CHANNEL_RIGHT);
		intakeWheelsLeft = new Spark(Konstanten.INTAKE_SPARK_LEFT);
		intakeWheelsRight = new Spark(Konstanten.INTAKE_SPARK_RIGHT);
	}
	
	public void grip(){
		System.out.println("grip");
		cubeSolenoidLeft.set(DoubleSolenoid.Value.kForward);
		cubeSolenoidRight.set(DoubleSolenoid.Value.kForward);
	}
	
	public void relax(){
		System.out.println("relax");
		cubeSolenoidLeft.set(DoubleSolenoid.Value.kOff);
		cubeSolenoidRight.set(DoubleSolenoid.Value.kOff);
	}
	public void release(){
		System.out.println("release");
		cubeSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
		cubeSolenoidRight.set(DoubleSolenoid.Value.kReverse);
	}
	public void liftIntake(){
		intakeLifterLeft.set(DoubleSolenoid.Value.kForward);
		intakeLifterRight.set(DoubleSolenoid.Value.kForward);
	}
	public void lowerIntake(){
		intakeLifterLeft.set(DoubleSolenoid.Value.kReverse);
		intakeLifterRight.set(DoubleSolenoid.Value.kReverse);
	}
	
	public boolean isCube(){
		//System.out.println("l" + cubeDetectorL.get());
		//System.out.println("r" + cubeDetectorR.get());
		return !cubeDetectorL.get() && !cubeDetectorR.get();
	}
	public void intake(double dir){
		intakeWheelsLeft.set(-dir);
		intakeWheelsRight.set(dir);
	}
	public void bump(){
		intakeWheelsLeft.set(1);
		intakeWheelsRight.set(1);
	}
	
}