package com.just_n.multithread.model;

import java.util.UUID;

public class Employee implements Entity{
    private String name;
    private int taskId;

    public Employee(String name, int taskId) {
        this.name = name;
        this.taskId = taskId;
    }
    public Employee(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Имя: " + name + "\n" +
                "ID задачи: " + taskId + "\n";
    }

    @Override
    public int getId() {
        return taskId;
    }
}
