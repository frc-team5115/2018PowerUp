package com.team5115.statemachines;

public class StateMachineBase {

    public static final int STOP = 0;

    public int state = 0;

    public StateMachineBase() {}

    public void setState(int s) {
		state = s;
    }

}
