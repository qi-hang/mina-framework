package com.test.mina.task;

public interface DistributeTask extends Distributable {

    /**
     * name of the task
     */
    String getName();

    /**
     * execute logic
     */
    void action();

}
