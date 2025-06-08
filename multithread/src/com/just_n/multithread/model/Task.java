package com.just_n.multithread.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Runnable, Entity {
    private final String name;
    private final int totalHours;
    private int hoursCompleted;
    private volatile boolean isRunning;
    private volatile boolean isPaused;
    private volatile boolean isCompleted;

    public Task(String name, int hours) {
        this.name = name;
        this.totalHours = hours;
        this.hoursCompleted = 0;
        this.isRunning = false;
        this.isPaused = false;
        this.isCompleted = false;
    }

    public Task(String name, int totalHours, int hoursCompleted, boolean isRunning, boolean isPaused, boolean isCompleted) {
        this.name = name;
        this.totalHours = totalHours;
        this.hoursCompleted = hoursCompleted;
        this.isRunning = isRunning;
        this.isPaused = isPaused;
        this.isCompleted = isCompleted;
    }

    @Override
    public void run() {
        isRunning = true;
        Thread executionThread = Thread.currentThread();
        System.out.printf("Начато выполнение задачи '%s' (%d часов)%n",
               
                name, totalHours);

        try {
            while (hoursCompleted < totalHours && isRunning) {
                synchronized (this) {
                    while (isPaused) {
                        wait();
                    }
                }
                Thread.sleep(5000);
                hoursCompleted++;

                System.out.printf(
                        "Задача '%s': %d/%d часов выполнено%n",
                       
                        name, hoursCompleted, totalHours);
            }
            isCompleted = hoursCompleted >= totalHours;
            System.out.printf("Задача '%s' %s%n",
                    name, isCompleted ? "завершена" : "прервана");
        } catch (InterruptedException e) {
            System.out.printf("Задача '%s' прервана!%n",
                    name);
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

    public String getName() {
        return name;
    }
}