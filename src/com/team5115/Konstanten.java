package com.team5115;

public class Konstanten {

	/**
	 * This class exists solely to store constant values that will remain the same for the whole robot
	 * While referencing this class is not necessary, it is a good organizational habit
	 * That means it's mandatory because if you don't do it, brian will be sad
	 */
	
	

	public static final double DELAY = 0.005;

	// Define deadzones and axis
	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	public static final int AXIS_THROTTLE = 4;
	public static final double JOYSTICK_DEADBAND = 0.025;
	public static final int JOYSTICK_EXPO = 2;
	//Actual Button Binds
	public static final int UP = 4;
	public static final int DOWN = 2;
	public static final int KILL = 10;
	public static final int SPIT = 6;
	public static final int INTAKE = 9;
	public static final int EJECT = 1;
	public static final int CORRECTCUBE = 3;
	public static final int LOWER_INTAKE = 12;
	public static final int GRAB_INTAKE = 5;
	
	// PID values
	public static final double AUTO_FORWARD_KP = 0.4; //Constants.getAsDouble("auto_forward_kp");
	public static final double AUTO_FORWARD_KI = 0; //Constants.getAsDouble("auto_forward_ki");
	public static final double AUTO_FORWARD_KD = 0.1; //Constants.getAsDouble("auto_forward_kd");
	public static final double AUTO_LINE_KP = 0.125;
	public static final double AUTO_LINE_KI = 0;
	public static final double AUTO_LINE_KD = 0;
	public static final double AUTO_TURN_KP = 0.06; //Constants.getAsDouble("auto_turn_kp");
	public static final double AUTO_TURN_KI = 0; //Constants.getAsDouble("auto_turn_ki");
	public static final double AUTO_TURN_KD = 0.05; //Constants.getAsDouble("auto_turn_kd");
	public static final double FORWARD_KF = 0; //Constants.getAsDouble("forward_kf");
	public static final double TURN_KF = 0; // Constants.getAsDouble("turn_kf");
	public static final double TURN_KP = 0.3; //Constants.getAsDouble("turn_kp");
	public static final double TURN_KI = 0.15; //Constants.getAsDouble("turn_ki");
	public static final double ARM_KP = 0.015;
	public static final double ARM_KI = 0;
	public static final double ARM_KD = 0.02;
	
	// Tolerances for PID
	public static final double LINE_TOLERANCE = 0.25; //Constants.getAsDouble("line_torerance"); // ft
	public static final double LINE_DTOLERANCE = 0.25; //Constants.getAsDouble("line_dtolerance"); // ft/s
	public static final double FORWARD_TOLERANCE = 0.25; //Constants.getAsDouble("forward_tolerance");//ft
	public static final double FORWARD_DTOLERANCE = 0.05; //Constants.getAsDouble("forward_dtolerance");//ft/s
	public static final double TURN_TOLERANCE = 5; //Constants.getAsDouble("turn_tolerance"); // rad
	public static final double TURN_DTOLERANCE = 15; //Constants.getAsDouble("turn_dtolerance"); // rad/s
	public static final double ARM_TOLERANCE = 5;
	public static final double ARM_DTOLERANCE = 5;
	
	// Physical robot attributes
	public static final double TOP_SPEED = 1;
	public static final double TOP_TURN_SPEED = 0.75;
	
	// DIFFERENT BETWEEN ROBOTS - DO NOT COPY
	public static final double RETURN_HEIGHT = 0;
	public static final double INTAKE_HEIGHT = 160;
	public static final double SWITCH_HEIGHT = 250;
	public static final double SCALE_HEIGHT = 720;
	//
	
	public static final double ELEVATOR_SPEED_SCALE = 1;
	public static final double ELEVATOR_SPEED_SWITCH = 0.5;
	public static final double ELEVATOR_THRESHOLD = 20;
	public static final double ELEVATOR_SPEED = 0.75;
	public static final double INTAKE_SPEED = 0.65;
	public static final double SPIT_SPEED = -1;
	public static final double PASSOFF_TIME = 1.5;
	public static final double PASSBACK_TIME = 0.1;
	public static final double SPIT_DELAY = 1;
	public static final double TIP_THRESHOLD = -20;
	public static final double ACCEL_THRESHOLD = 0.75;
	public static final double SAFE_ANGLE = -15;
	
	
	public static final int POT_THRESHOLD = 900;
	public static final double ARC_RATIO = 2.675;
	public static final int ELEVATOR_MAX = 817;
	public static final int ELEVATOR_MIN = 0;
	public static final int ELEVATOR_STEP = 100;
	//PWM
	public static final int INTAKE_SPARK_LEFT = 2;
	public static final int INTAKE_SPARK_RIGHT = 1;
	
	
	
	//sensors
	//DIO
	public static final int CUBE_DETECTOR_L = 9;
	public static final int CUBE_DETECTOR_R = 8;
	public static final int TOP_LIMIT = 1;
	public static final int BOTTOM_LIMIT = 2;
	
	
	//PCM
	public static final int INTAKE_FORWARD_CHANNEL_LEFT = 5;	//retract; grip	//YOCANNOT COPY THIS TOTHE OTHER ROBOT
	public static final int INTAKE_REVERSE_CHANNEL_LEFT = 4;	//extend; release
	public static final int INTAKE_FORWARD_CHANNEL_RIGHT = 6;	//retract; grip
	public static final int INTAKE_REVERSE_CHANNEL_RIGHT = 7;	//extend, release
	
	public static final int CARRIAGE_FORWARD_CHANNEL  = 0;		//retract; grab
	public static final int CARRIAGE_REVERSE_CHANNEL = 1;		//extend; release

	public static final int LIFTER_FORWARD_CHANNEL_LEFT = 1;	//retract; lift
	public static final int LIFTER_REVERSE_CHANNEL_LEFT = 0;	//extend; lower
	public static final int LIFTER_FORWARD_CHANNEL_RIGHT = 3;	//retract; lift
	public static final int LIFTER_REVERSE_CHANNEL_RIGHT = 2;	//extend; lower
	
	
	
	
	//PDP
	public static final int FRONT_LEFT_CHANNEL = 1;
	public static final int FRONT_RIGHT_CHANNEL = 2;
	public static final int BACK_LEFT_CHANNEL = 3;
	public static final int BACK_RIGHT_CHANNEL = 4;
	
	// Can
	public static final int FRONT_LEFT_MOTOR_ID = 3;
	public static final int FRONT_RIGHT_MOTOR_ID = 4;
	public static final int BACK_LEFT_MOTOR_ID = 1;
	public static final int BACK_RIGHT_MOTOR_ID = 2;
	public static final int PHEUMATIC_PCM_0_ID = 7;
	public static final int PHEUMATIC_PCM_1_ID = 8;
	public static final int MOVER_MOTOR_ID = 5;
}
