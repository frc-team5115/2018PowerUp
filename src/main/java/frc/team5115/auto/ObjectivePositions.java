package frc.team5115.auto;

import frc.team5115.robot.Robot;

public class ObjectivePositions {

    public static char start;
    public static char switchpos;
    public static char scalepos;

    public ObjectivePositions(char wheretostart){
        start = wheretostart;
        switchpos = Robot.DS.getGameSpecificMessage().charAt(0);
        scalepos = Robot.DS.getGameSpecificMessage().charAt(1);
    }
    public void updateTarget(char newtarget){
        start = newtarget;
    }
    public boolean isCenter(){return start == 'C';}
    public boolean switchOurs(){ return start == switchpos; }
    public boolean scaleOurs(){ return start == scalepos; }
    public boolean objectivesAligned(){return (switchpos == scalepos);}


}
