package com.just_n.multithread.repository.util;

import com.just_n.multithread.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ExcelDataHandlerTest {
    private final File file = new File("test_resources/task_app.xlsx");
    private ExcelDataHandler edh;

    @BeforeEach
    void setUp(){
        edh = new ExcelDataHandler(file);
    }

    @Test
    void dataHandlerTest() throws IOException {
        Employee e1 = new Employee("Josh", 123);
        Employee e2 = new Employee("Dave", 321);
        List<Employee> list = List.of(e1,e2);
        edh.saveObjectsToExcel("Сотрудники", list);
        List<Employee> parsedList = edh.parseObjectsFromSheetName("Сотрудники", Employee.class).stream().toList();

        for(int i = 0; i < list.size(); i++){
            assertEquals(list.get(i).toString(), parsedList.get(i).toString());
        }
    }
}