package com.team5115.systems;

import com.cruzsbrian.robolog.Log;


import com.team5115.Constants;

import edu.wpi.first.wpilibj.Victor;
import  edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Intake {

   DigitalInput cubeDetector;
   DoubleSolenoid cubeSolenoid; 
   DoubleSolenoid intakeLifter;
   Victor intakeWheels;
    public Intake(){
    	cubeDetector = new DigitalInput(Constants.CUBE_DETECTOR);
    	cubeSolenoid = new DoubleSolenoid(Constants.PNUMATIC_PCM_ID, Constants.INTAKE_FORWARD_CHANNEL, Constants.INTAKE_REVERSE_CHANNEL);
    	intakeLifter = new DoubleSolenoid(Constants.PNUMATIC_PCM_ID, Constants.LIFTER_FORWARD_CHANNEL, Constants.LIFTER_REVERSE_CHANNEL);
    	intakeWheels = new Victor(Constants.INTAKE_VICTOR);
    }
    
    public void intake(){
    	
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
