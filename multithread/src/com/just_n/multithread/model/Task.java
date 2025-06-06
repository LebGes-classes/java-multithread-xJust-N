package com.just_n.multithread.model;

public class Task implements Runnable{
    private final String name;
    private final Thread taskTread;

    public Task(String name) {
        this.name = name;
        taskTread = new Thread(this);
        taskTread.start();
    }

    @Override
    public void run(){

    }
}
