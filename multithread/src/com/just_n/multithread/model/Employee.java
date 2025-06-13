package com.just_n.multithread.model;

import com.just_n.multithread.repository.util.Excel;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Employee implements Runnable, Entity {
    @Excel
    private final int id;
    @Excel
    private final String name;

    private final Queue<Task> taskQueue = new LinkedList<>();

    @Excel
    private int hoursWorkedToday;
    @Excel
    private int idleHoursToday;
    @Excel
    private int completedTasks;
    @Excel
    private int totalHoursWorked;

    public Employee(String name) {
        this.id = UUID.randomUUID().hashCode();
        this.name = name;
    }

    public Employee(int id, String name, int hoursWorkedToday, int idleHoursToday, int completedTasks, int totalHoursWorked) {
        this.id = id;
        this.name = name;
        this.hoursWorkedToday = hoursWorkedToday;
        this.idleHoursToday = idleHoursToday;
        this.completedTasks = completedTasks;
        this.totalHoursWorked = totalHoursWorked;
    }

    public void assignTask(Task task) {
        taskQueue.offer(task);
    }

    @Override
    public void run() {
        hoursWorkedToday = 0;
        idleHoursToday = 0;
        Task task;
        while (hoursWorkedToday + idleHoursToday < 12 && !Thread.currentThread().isInterrupted()) {
            task = taskQueue.poll();
            if (task != null) {
                System.out.printf("%s начал свою работу выполнения задания %s: %d hours%n", name, task.getName(), task.getTotalHours());
                task.run();
                completedTasks++;
                hoursWorkedToday += task.getTotalHours();
                totalHoursWorked += task.getTotalHours();
                System.out.printf("%s завершил работу над %s%n", name, task.getName());

            } else {
                idleHoursToday++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.printf("%s завершил рабочий день%n", name);
    }
    @Override
    public int getId() { return id; }
    
    @Override
    public String toString(){
        return String.format("id: %d%n Имя: %s%n Время работы: %d%n Время простоя: %d%n Всего отработано: %d%n Всего выполнено: %d%n",
                id, name, hoursWorkedToday, idleHoursToday, totalHoursWorked, completedTasks);
    }
}