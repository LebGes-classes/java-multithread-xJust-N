package com.just_n.multithread.model;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WorkdaySimulation extends Thread {
    private final CountDownLatch cdl;
    private final List<Employee> employeeList;
    private final List<Task> taskList;

    public WorkdaySimulation(List<Employee> employeeList, List<Task> taskList) {
        super();
        this.employeeList = employeeList;
        this.taskList = taskList;
        cdl = new CountDownLatch(employeeList.size());
    }

    @Override
    public void run() {
        System.out.println("Старт рабочего дня...");
        System.out.printf("Всего задач: %d%n", cdl.getCount());
        Iterator<Employee> employeeIterator = employeeList.iterator();
        for (Task task : taskList) {
            if (!employeeIterator.hasNext())
                employeeIterator = employeeList.iterator();
            employeeIterator.next().assignTask(task);
        }
        System.out.println("Задачи в команде распределены");
        System.out.println("Начало работы...");
        employeeList.forEach(e -> new Thread(e) {
            @Override
            public void run() {
                e.run();
                cdl.countDown();
            }
        }.start());
        try {
            cdl.await();
        } catch (InterruptedException e) {
            System.out.println("Рабочий день прерван\n" + e);
        } finally {
            System.out.println("Конец рабочего дня");
            printStatistics();
        }
    }

    private void printStatistics() {
        System.out.println("%nПодведение итогов...");
        employeeList.forEach(System.out::println);
    }
}
