package frc.team5115.auto;

import edu.wpi.first.wpilibj.Timer;
import frc.team5115.Constants;
import frc.team5115.robot.Robot;
import frc.team5115.statemachines.ElevatorManager;
import frc.team5115.statemachines.GripManager;
import frc.team5115.statemachines.IntakeManager;
import frc.team5115.statemachines.StateMachineBase;

import java.lang.reflect.Field;


public class UHOH extends StateMachineBase {


    public static final int INIT = 0;
    public static final int DRIVING = 1;
    public static final int FINISHED = 2;

    AutoDrive drive;
    double time;

    public UHOH(){
        drive = new AutoDrive();
    }

    protected void updateChildren() {
        drive.update();
        Robot.EM.update();
        Robot.IM.update();
        Robot.GM.update();
    }

    public void resetEverything(){
        Robot.drivetrain.resetEncoders();
        Robot.drivetrain.resetGyro();
    }

    public void update(){
        switch(state) {
            case INIT:
                System.out.println("cross line selected!");
                Robot.EM.setState(ElevatorManager.STOP);
                Robot.IM.setState(IntakeManager.PASSNOWHEELS);
                Robot.GM.setState(GripManager.GRIP);
                //Robot.drivetrain.drive(0.5, 0);
                drive.startLine(11.6, 0.5, false);
                //time = Timer.getFPGATimestamp();
                setState(DRIVING);
                break;
            case DRIVING:
                updateChildren();
//                System.out.println(Robot.drivetrain.distanceTraveled());
//                if(Robot.drivetrain.distanceTraveled() == 4){
//                    resetEverything();
//                    setState(FINISHED);
//                }
//                System.out.println();
                //System.out.println(drive.targetDist);
//                System.out.println((Robot.drivetrain.leftRaw() + Robot.drivetrain.rightRaw()) / 2);
                //System.out.println(Robot.drivetrain.distanceTraveled());
//                System.out.println("looking for time..." + (time + 1.5));
//                System.out.println(Timer.getFPGATimestamp());
//                if(Timer.getFPGATimestamp() >= time + 1.5){
//                    setState(FINISHED);
//                    System.out.println("time found!");
//                }
                if(drive.state == AutoDrive.FINISHED){
                    setState(FINISHED);
                }
                    break;
            case FINISHED:
                //drive.setState(drive.FINISHED);
                drive.setState(drive.STOP);
                System.out.println("target hit!");
                updateChildren();
                break;
        }
    }

}
