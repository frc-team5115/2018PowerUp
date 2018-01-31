package com.team5115.systems;

import com.cruzsbrian.robolog.Log;


import com.team5115.Konstanten;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Intake {

	DigitalInput cubeDetector;
	DoubleSolenoid cubeSolenoid; 
	DoubleSolenoid intakeLifter;
	Victor intakeWheels;
	public Intake(){
		cubeDetector = new DigitalInput(Konstanten.CUBE_DETECTOR);
		cubeSolenoid = new DoubleSolenoid(Konstanten.PNUMATIC_PCM_ID, Konstanten.INTAKE_FORWARD_CHANNEL, Konstanten.INTAKE_REVERSE_CHANNEL);
		intakeLifter = new DoubleSolenoid(Konstanten.PNUMATIC_PCM_ID, Konstanten.LIFTER_FORWARD_CHANNEL, Konstanten.LIFTER_REVERSE_CHANNEL);
		intakeWheels = new Victor(Konstanten.INTAKE_VICTOR);
	}
	
	public void grip(){
		cubeSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void relax(){
		cubeSolenoid.set(DoubleSolenoid.Value.kOff);
	}
	public void release(){
		cubeSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public void liftIntake(){
		intakeLifter.set(DoubleSolenoid.Value.kForward);
	}
	public void lowerIntake(){
		intakeLifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public boolean isCube(){
		return cubeDetector.get();
	}
	public void intake(double dir){
		intakeWheels.set(dir);
	}
	
}
