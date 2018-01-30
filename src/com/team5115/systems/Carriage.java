package com.team5115.systems;

import com.cruzsbrian.robolog.Log;
import com.ctre.CANTalon;
/*
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
*/
import com.kauailabs.navx.frc.AHRS;
import com.team5115.Constantos;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Carriage {
	DoubleSolenoid grabberSolenoid;
   

    public Carriage(){
    	grabberSolenoid = new DoubleSolenoid(Constantos.PNUMATIC_PCM_ID, Constantos.GRABBER_FORWARD_CHANNEL, Constantos.GRABBER_REVERSE_CHANNEL);
    }
    
    public void grab(){
    	grabberSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void eject(){
    	grabberSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void stop(){
    	grabberSolenoid.set(DoubleSolenoid.Value.kOff);
    }
    
}
