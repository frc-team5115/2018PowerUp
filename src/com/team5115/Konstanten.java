package com.team5115;


import com.cruzsbrian.robolog.Constants;

public class Konstanten {

	/**
	 * This class exists solely to store constant values that will remain the same for the whole robot
	 * While referencing this class is not necessary, it is a good organizational habit
	 * That means it's mandatory because if you don't do it, brian will be sad
	 */
	
	

	public static final double DELAY = 0.005;

	// Buttons and Axes
	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	public static final double JOYSTICK_DEADBAND = 0.2;
	public static final int JOYSTICK_EXPO = 2;
	public static final int UP = 5;
	public static final int DOWN = 3;
	public static final int KILL = 7;
	public static final int SPIT = 10;
	public static final int SCALE = 12;
	public static final int SWITCH = 11;
	public static final int RETURN = 9;
	public static final int INTAKE = 4;
	public static final int EJECT = 1;
	public static final int SWITCHDIR = 2;
	public static final int TEST = 6;
	
	// PID values
	public static final double AUTO_FORWARD_KP = 1; //Constants.getAsDouble("auto_forward_kp");
	public static final double AUTO_FORWARD_KI = 0; //Constants.getAsDouble("auto_forward_ki");
	public static final double AUTO_FORWARD_KD = 0; //Constants.getAsDouble("auto_forward_kd");
	public static final double AUTO_LINE_KP = 1;
	public static final double AUTO_LINE_KI = 0;
	public static final double AUTO_LINE_KD = 0;
	public static final double AUTO_TURN_KP = .05; //Constants.getAsDouble("auto_turn_kp");
	public static final double AUTO_TURN_KI = 0; //Constants.getAsDouble("auto_turn_ki");
	public static final double AUTO_TURN_KD = 0.005; //Constants.getAsDouble("auto_turn_kd");
	public static final double FORWARD_KF = 0; //Constants.getAsDouble("forward_kf");
	public static final double TURN_KF = 0; // Constants.getAsDouble("turn_kf");
	public static final double TURN_KP = .0625; //Constants.getAsDouble("turn_kp");
	public static final double TURN_KI = 0; //Constants.getAsDouble("turn_ki");
	public static final double ARM_KP = 0.02;
	public static final double ARM_KI = 0;
	public static final double ARM_KD = 0;
	
	// Tolerances for PID
	public static final double LINE_TOLERANCE = 0.25; //Constants.getAsDouble("line_torerance"); // ft
	public static final double LINE_DTOLERANCE = 0.25; //Constants.getAsDouble("line_dtolerance"); // ft/s
	public static final double FORWARD_TOLERANCE = 0.25; //Constants.getAsDouble("forward_tolerance");//ft
	public static final double FORWARD_DTOLERANCE = 0.25; //Constants.getAsDouble("forward_dtolerance");//ft/s
	public static final double TURN_TOLERANCE = 2.5; //Constants.getAsDouble("turn_tolerance"); // rad
	public static final double TURN_DTOLERANCE = 5; //Constants.getAsDouble("turn_dtolerance"); // rad/s
	public static final double ARM_TOLERANCE = 5;
	public static final double ARM_DTOLERANCE = 5;
	
	// Physical robot attributes
	public static final double TOP_SPEED = 1;
	public static final double TOP_TURN_SPEED = 1;
	public static final double RETURN_HEIGHT = 60;
	public static final double RUNG_HEIGHT = 0;
	public static final double SWITCH_HEIGHT = 160;
	public static final double SCALE_HEIGHT = 850;
	public static final double ELEVATOR_SPEED = 0.5;
	public static final int SPEED_OF_NOT_MOVING = 0;
	public static final int INTAKE_SPEED = 1;
	public static final double INTAKE_DELAY = 0.25;
	public static final double DUMPING_DELAY = 1;
	public static final int POT_THRESHOLD = 900;
	public static final double ARC_RATIO = 2.675;
	public static final int ELEVATOR_MAX = 850;
	public static final int ELEVATOR_MIN = 60;
	
	//PWM
	public static final int INTAKE_VICTOR = 0;
	
	
	//sensors
	//DIO
	public static final int CUBE_DETECTOR = 0;
	public static final int TOP_LIMIT = 1;
	public static final int BOTTOM_LIMIT = 2;
	
	
	//PCM
	public static final int INTAKE_FORWARD_CHANNEL = 0;
	public static final int INTAKE_REVERSE_CHANNEL = 1;
	public static final int GRABBER_FORWARD_CHANNEL  = 2;
	public static final int GRABBER_REVERSE_CHANNEL = 3;
	public static final int LIFTER_FORWARD_CHANNEL = 4;
	public static final int LIFTER_REVERSE_CHANNEL = 5;
	
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
	public static final int PNUMATIC_PCM_ID = 5;
	public static final int MOVER_MOTOR_ID = 0;
}
