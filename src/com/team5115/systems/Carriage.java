package com.team5115.systems;

/*
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
*/
import com.kauailabs.navx.frc.AHRS;
import com.team5115.Konstanten;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Carriage {
	DoubleSolenoid grabberSolenoid;
   

    public Carriage(){
    	grabberSolenoid = new DoubleSolenoid(Konstanten.PHEUMATIC_PCM_1_ID, Konstanten.CARRIAGE_FORWARD_CHANNEL, Konstanten.CARRIAGE_REVERSE_CHANNEL);
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
