package com.team5115.systems;

import com.cruzsbrian.robolog.Log;

import com.team5115.Constants;


import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Elevator {

   AnalogPotentiometer angle;

    public Elevator(){
    	angle = new AnalogPotentiometer(Constants.POTENTIOMETER);
    }
    
    public double getAngle(){
    	return angle.get();
    }
    
    public void move(double dir){
    	//moving the elevator
    }
    
    public void moveTo(double pos){
    	double currentposition = getAngle();
    	
    }
}
