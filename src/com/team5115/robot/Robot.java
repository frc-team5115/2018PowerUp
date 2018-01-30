package com.team5115.robot;
//
import com.cruzsbrian.robolog.Log;
import com.cruzsbrian.robolog.Constants;
import com.team5115.statemachines.CarriageManager;
import com.team5115.statemachines.CubeManipulatorManager;
import com.team5115.statemachines.Drive;
import com.team5115.statemachines.ElevatorManager;
import com.team5115.systems.Carriage;
import com.team5115.systems.DriveTrain;
import com.team5115.systems.Elevator;
import com.team5115.auto.Auto;
import com.team5115.auto.DriveForwardSome;
import com.team5115.systems.Intake;
import com.team5115.statemachines.IntakeManager;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
public class Robot extends IterativeRobot {

    // Define subsystems
    public static DriveTrain drivetrain;
    
    public static DriveForwardSome autoDrive;
    public static Drive drive;
    public static DriverStation ds;
    public static SendableChooser<Integer> positionChooser;
    public static SendableChooser<Integer> switchPositionChooser;
    public static SendableChooser<Integer> strategyChooser;
    
    public static CubeManipulatorManager CMM;
	public static int position;
	public static int switchPosition;
	public static int strategy;
	public static Auto auto;
	public static Intake intake;
	public static IntakeManager IM;
	public static Carriage carriage;
    public static CarriageManager CM;
	public static Elevator elevator;
	public static ElevatorManager EM;
    
	public String gameData;
	public char L = 'L';
	
    // Initialization phase of the robot class
    // This method runs once when the robot turns on and does not run again until the robot reboots
    public void robotInit() {
//    	//Change back to normal
    	Constants.loadFromFile();
    	Log.startServer(5115);
    	Log.setDelay(500);
        // Initialize subsystems
        drivetrain = new DriveTrain();
        drive = new Drive();
        autoDrive = new DriveForwardSome();
        intake = new Intake();
        IM = new IntakeManager();
        carriage = new Carriage();
        CM = new CarriageManager();
        elevator = new Elevator();
        EM = new ElevatorManager();
        CMM = new CubeManipulatorManager();
        ds = DriverStation.getInstance();
//       
//        positionChooser = new SendableChooser<Integer>();
//        positionChooser.addDefault("Left", 1);
//        positionChooser.addObject("Right", 2);
//        positionChooser.addObject("Center", 3);
//        
//        switchPositionChooser = new SendableChooser<Integer>();
//        switchPositionChooser.addDefault("Left", 1);
//        switchPositionChooser.addObject("Right", 2);
//        
//        strategyChooser = new SendableChooser<Integer>();
//        strategyChooser.addDefault("Strategy 1", 2);//these are the state numbers in Auto.java
//        strategyChooser.addObject("Strategy 2", 3);  
//        strategyChooser.addObject("Strategy 3", 4);
//        strategyChooser.addObject("Strategy 4", 5);
//        

        
      drive.setState(Drive.STOP);
        
    }

    // Runs once when the autonomous phase of the game starts
    public void autonomousInit() {
    	 drivetrain.resetGyro();
    	 drivetrain.resetEncoders();
    	 Timer.delay(0.1);
    	 //System.out.println("Yaw Reset" + Robot.drivetrain.getYaw());
    	 /*
    	 position = positionChooser.getSelected();
    	 switchPosition = switchPositionChooser.getSelected();
    	 strategy = strategyChooser.getSelected();
    	 auto = new Auto(position, switchPosition, strategy);
    	 */
    	 //autoDrive.setState(DriveForwardSome.INIT);
         drivetrain.inuse = true;
         
 		gameData =  ds.getGameSpecificMessage();
		switchPosition = (L == gameData.charAt(0)) ? 1 : 2;
// 		position = positionChooser.getSelected();
// 		strategy = strategyChooser.getSelected();
 		//auto = new Auto(position, switchPosition, strategy);

 		auto = new Auto(3, switchPosition, 2);
 		
 		auto.setState(Auto.INIT);
    }

    //Runs periodically while the game is in the autonomous phase
    public void autonomousPeriodic() {
        Timer.delay(.005);
        //System.out.println("yaw " + drivetrain.getYaw());
        Log.log("yaw", drivetrain.getYaw());
        //System.out.println("dist" + drivetrain.distanceTraveled());
        Log.log("Distance", drivetrain.distanceTraveled());
        
    	//Log.add("wqefwf", 2.0);
        Log.add("Yaw", drivetrain.getYaw() * (180 / Math.PI));
        System.out.println("Yaw "+ drivetrain.getYaw());
        
        auto.update();
        //autoDrive.update();
        //System.out.println(gameData);
        
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
        
    }

}

