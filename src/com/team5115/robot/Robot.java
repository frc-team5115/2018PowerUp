package com.team5115.robot;

import com.team5115.Constants;
import com.team5115.statemachines.Drive;
import com.team5115.systems.DriveTrain;
import com.team5115.auto.DriveForwardSome;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {

    // Define subsystems
    public static DriveTrain drivetrain;
    
    public static DriveForwardSome autoDrive;
    public static Drive drive;

    // Initialization phase of the robot class
    // This method runs once when the robot turns on and does not run again until the robot reboots
    public void robotInit() {
        // Initialize subsystems
        drivetrain = new DriveTrain();
        drive = new Drive();
        autoDrive = new DriveForwardSome();
    }

    // Runs once when the autonomous phase of the game starts
    public void autonomousInit() {
    	autoDrive.setState(autoDrive.INIT);
    }

    //Runs periodically while the game is in the autonomous phase
    public void autonomousPeriodic() {
        Timer.delay(Constants.DELAY);
        autoDrive.update();
        
    }

    // Runs once when the game enters the driver operated stage
    public void teleopInit() {
    	
    	drive.setState(Drive.DRIVING);
        drivetrain.inuse = false;
    }

    // Runs periodically when the game is in the driver operated stage
    public void teleopPeriodic() {

        Timer.delay(Constants.DELAY);
        drive.update();
    }

    // Runs when the robot is disabled
    public void disabledInit() {}

    // Runs periodically while the robot is disabled
    public void disabledPeriodic() {
        Timer.delay(Constants.DELAY);
    }

}

