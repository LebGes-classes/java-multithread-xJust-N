package com.just_n.multithread.model;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Employee implements Runnable, Entity {
    private final int id;
    private final String name;
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();

    private int hoursWorkedToday;
    private int idleHoursToday;
    private int completedTasks;
    private int totalHoursWorked;
    private boolean isWorking;

    public Employee(String name) {
        this.id = UUID.randomUUID().hashCode();
        this.name = name;
    }

    public Employee(int id, String name, int hoursWorkedToday, int idleHoursToday, int completedTasks, int totalHoursWorked, boolean isWorking) {
        this.id = id;
        this.name = name;
        this.hoursWorkedToday = hoursWorkedToday;
        this.idleHoursToday = idleHoursToday;
        this.completedTasks = completedTasks;
        this.totalHoursWorked = totalHoursWorked;
        this.isWorking = isWorking;
    }


    public void assignTask(Task task) {
        taskQueue.offer(task);
    }

    @Override
    public void run() {
        isWorking = true;
        hoursWorkedToday = 0;
        idleHoursToday = 0;

        while (hoursWorkedToday < 24 && !Thread.currentThread().isInterrupted()) {
            Task task = taskQueue.poll();
            if (task != null) {
                System.out.printf("%s начал свою работу выполнения задания %s%n", name, task.getName());
                task.run();
                hoursWorkedToday++;
                totalHoursWorked++;

                if (task.isCompleted()) {
                    completedTasks++;
                } else {
                    taskQueue.offer(task);
                }
            } else {
                idleHoursToday++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        isWorking = false;
    }
    @Override
    public int getId() { return id; }
    
    @Override
    public String toString(){
        return String.format("id: %d%n Имя:%s%n Время работы: %d%n Время простоя: %d%n Всего отработано: %d%n Всего выполнено: %d%n",
                id, name, hoursWorkedToday, idleHoursToday, totalHoursWorked, completedTasks);
    }
}