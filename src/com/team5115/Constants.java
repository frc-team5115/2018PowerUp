package com.team5115;

public class Constants {

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

    // Motors
    public static final int FRONT_LEFT_MOTOR_ID = 3;
    public static final int FRONT_RIGHT_MOTOR_ID = 4;
    public static final int BACK_LEFT_MOTOR_ID = 1;
    public static final int BACK_RIGHT_MOTOR_ID = 2;
    public static final double OPTIMUM_FLYWHEEL_RPM = 2750;

    // PID values
    public static final double AUTO_FORWARD_KP = 1;
    public static final double AUTO_FORWARD_KI = 0;
    public static final double AUTO_FORWARD_KD = 0;
    public static final double AUTO_LINE_KP = 1;
    public static final double AUTO_LINE_KI = 0;
    public static final double AUTO_LINE_KD = 0;
    public static final double AUTO_TURN_KP = 1;
    public static final double AUTO_TURN_KI = 0;
    public static final double AUTO_TURN_KD = 0;
    public static final double FORWARD_KF = 0.1;
    public static final double TURN_KF = 0.1;
    public static final double TURN_KP = 0;
    public static final double TURN_KI = 0;

    // Tolerances for PID
    public static final double LINE_TOLERANCE = 0.2; // ft
    public static final double LINE_DTOLERANCE = 0.2; // ft/s
    public static final double FORWARD_TOLERANCE = 0.05;//ft
    public static final double FORWARD_DTOLERANCE = 0.1;//ft/s
    public static final double TURN_TOLERANCE = 0.2; // rad
    public static final double TURN_DTOLERANCE = 0.2; // rad/s

    // Physical robot attributes
    public static final double TOP_SPEED = 10;
    public static final double TOP_TURN_SPEED = 5;

}
