package com.team5115.robot;
//
import com.cruzsbrian.robolog.Log;
import com.cruzsbrian.robolog.Constants;
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
//    	//Change back to normal
    	Constants.loadFromFile();
    	Log.startServer(5115);
    	Log.setDelay(150);
        // Initialize subsystems
        drivetrain = new DriveTrain();
        drive = new Drive();
        autoDrive = new DriveForwardSome();
        
        SmartDashboard.putNumber("Speed", drivetrain.distanceTraveled());
        
    }

    // Runs once when the autonomous phase of the game starts
    public void autonomousInit() {
    	 drivetrain.resetGyro();
    	 drivetrain.resetEncoders();
    	 Timer.delay(0.1);
    	 //System.out.println("Yaw Reset" + Robot.drivetrain.getYaw());
    	 autoDrive.setState(DriveForwardSome.INIT);
         drivetrain.inuse = false;
         
    }

    //Runs periodically while the game is in the autonomous phase
    public void autonomousPeriodic() {
        Timer.delay(.005);
        //System.out.println("yaw " + drivetrain.getYaw());
        Log.log("yaw", drivetrain.getYaw());
        //System.out.println("dist" + drivetrain.distanceTraveled());
        Log.log("Distance", drivetrain.distanceTraveled());

    	//Log.add("wqefwf", 2.0);
        //Log.add("Distance", drivetrain.distanceTraveled());
        
        autoDrive.update();
        
    }

    // Runs once when the game enters the driver operated stage
    public void teleopInit() {
    	drivetrain.drive(0,0);
        autoDrive.setState(DriveForwardSome.FINISHED);
    	drive.setState(Drive.DRIVING);
        drivetrain.inuse = false;
        drivetrain.resetGyro();
        drivetrain.resetEncoders();
       
    }

    // Runs periodically when the game is in the driver operated stage
    public void teleopPeriodic() {
    	//System.out.println(drivetrain.getYaw());
    	//System.out.println(drivetrain.distanceTraveled());
    	Timer.delay(.005);
        drive.update();
    }

    // Runs when the robot is disabled
    public void disabledInit() {
    	autoDrive.setState(DriveForwardSome.FINISHED);
    }
    
    	
    // Runs periodically while the robot is disabled
    public void disabledPeriodic() {
    	autoDrive.setState(DriveForwardSome.FINISHED);
    	drivetrain.drive(0,0);
        Timer.delay(5);
    }

}

