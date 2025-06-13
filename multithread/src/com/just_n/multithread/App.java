package com.just_n.multithread;

import com.just_n.multithread.model.Employee;
import com.just_n.multithread.model.Task;
import com.just_n.multithread.model.WorkdaySimulation;
import com.just_n.multithread.repository.DataLoader;
import com.just_n.multithread.repository.ObjectStorage;
import com.just_n.multithread.repository.loaders.XlsxLoader;
import com.just_n.multithread.repository.savers.XlsxSaver;

import java.io.IOException;
import java.util.List;


public class App {
    private final ObjectStorage objectStorage;

    public App() {
        objectStorage = ObjectStorage.getInstance();
    }

    public void start() {
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить данные:\n" + e);
        }
        run();
    }

    private void load() throws IOException {
        DataLoader dl = new XlsxLoader();
        dl.load(objectStorage);
    }

    private void run() {
        List<Employee> employeeList = objectStorage.getListOfObjects(Employee.class);
        List<Task> taskList = objectStorage.getListOfObjects(Task.class);
        WorkdaySimulation sim = new WorkdaySimulation(employeeList, taskList);
        sim.start();
        try {
            sim.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            save();
            System.out.println("Статистика успешно сохранена в xlsx файл");
        } catch (IOException e) {
            System.out.println("Не удалось сохранить в файл:\n" + e);
        }
    }

    private void save() throws IOException {
        new XlsxSaver().save(objectStorage);
    }
}
