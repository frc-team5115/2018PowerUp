package com.team5115.systems;

import com.cruzsbrian.robolog.Log;

import com.team5115.Konstanten;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;


public class Elevator {

	AnalogPotentiometer angle;
	DigitalInput topLimit;
	DigitalInput bottomLimit;
	double lastAngle = 0;

	 public Elevator(){
	 	angle = new AnalogPotentiometer(Konstanten.POTENTIOMETER);
	 	topLimit = new DigitalInput(Konstanten.TOP_LIMIT);
	 	bottomLimit = new DigitalInput(Konstanten.BOTTOM_LIMIT);
	 }
	 
	 public double getAngle(){
	 	return angle.get();
	 }
	 
	 public double getDAngle(){
	 	double dAngle = (getAngle() - lastAngle)/Konstanten.DELAY;
	 	lastAngle = getAngle();
	 	return dAngle;
	 }
	 
	 public void move(double dir){
	 	//moving the elevator
	 }
	 
	 public boolean maxHeight(){
	 	return topLimit.get();
	 }
	 public boolean minHeight(){
	 	return bottomLimit.get();
	 }
}
