package com.just_n.multithread.repository.loaders;

import com.just_n.multithread.model.Employee;
import com.just_n.multithread.model.Task;
import com.just_n.multithread.repository.DataLoader;
import com.just_n.multithread.repository.ObjectStorage;
import com.just_n.multithread.repository.util.ExcelDataHandler;

import java.io.File;
import java.io.IOException;

import static com.just_n.multithread.repository.FileStringsEnum.*;

public class XlsxLoader implements DataLoader {
    @Override
    public void load(ObjectStorage data) throws IOException {
        ExcelDataHandler edh = new ExcelDataHandler(new File(XLSX_FILE_NAME.getValue()));
        data.addListOfObjects(edh.parseObjectsFromSheetName(EMPLOYEES.getValue(), Employee.class));
        data.addListOfObjects(edh.parseObjectsFromSheetName(TASKS.getValue(), Task.class));
    }
}