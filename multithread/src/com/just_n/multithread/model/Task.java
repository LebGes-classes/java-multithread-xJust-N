package com.just_n.multithread.model;

import com.just_n.multithread.repository.util.Excel;

public class Task implements Runnable, Entity {
    @Excel
    private final String name;
    @Excel
    private final int totalHours;
    @Excel
    private int hoursCompleted;

    private volatile boolean isRunning;
    private volatile boolean isCompleted;

    public Task(String name, int hours) {
        this(name, hours, 0);
    }

    public Task(String name, int hours, int hoursCompleted) {
        this.name = name;
        this.totalHours = hours;
        this.hoursCompleted = hoursCompleted;
        this.isRunning = false;
        this.isCompleted = false;
    }

    @Override
    public void run() {
        isRunning = true;
        try {
            while (hoursCompleted < totalHours && isRunning) {
                Thread.sleep(1000);
                hoursCompleted++;
                System.out.printf("- Задача '%s': %d/%d часов выполнено%n", name, hoursCompleted, totalHours);
            }
            isCompleted = hoursCompleted >= totalHours;
        } catch (InterruptedException e) {
            System.out.printf("- Задача '%s' прервана!%n",name);
            Thread.currentThread().interrupt();
        } finally {
            isRunning = false;
        }
    }
    public int getId() {
        return name.hashCode();
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public int getTotalHours() {
        return totalHours;
    }
    public String getName() {
        return name;
    }
}