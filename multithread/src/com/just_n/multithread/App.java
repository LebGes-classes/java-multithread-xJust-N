package com.just_n.multithread;

import com.just_n.multithread.model.Employee;
import com.just_n.multithread.model.Task;
import com.just_n.multithread.repository.DataLoader;
import com.just_n.multithread.repository.ObjectStorage;
import com.just_n.multithread.repository.loaders.XlsxLoader;
import com.just_n.multithread.repository.savers.XlsxSaver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class App {
    private boolean isRunning;
    private final ObjectStorage objectStorage;

    public App() {
        isRunning = false;
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
        print("Старт рабочего дня..\n");
        List<Employee> employeeList = objectStorage.getListOfObjects(Employee.class);
        List<Task> taskList = objectStorage.getListOfObjects(Task.class);
        Iterator<Task> taskIterator = taskList.iterator();
        synchronized (employeeList) {
            new Thread(
                    () -> employeeList.forEach(e -> {
                        if (taskIterator.hasNext())
                            e.assignTask(taskIterator.next());
                        new Thread(e).start();
                    })
            ).start();
        }
        employeeList.forEach(System.out::println);

    }

    private void print(String s) {
        System.out.println(s);
    }

    private void save() throws IOException {
        new XlsxSaver().save(objectStorage);
    }
}
