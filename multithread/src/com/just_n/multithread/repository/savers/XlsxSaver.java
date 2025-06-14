package com.just_n.multithread.repository.savers;

import com.just_n.multithread.model.Employee;
import com.just_n.multithread.repository.DataSaver;
import com.just_n.multithread.repository.ObjectStorage;
import com.just_n.multithread.repository.util.ExcelDataHandler;

import java.io.File;
import java.io.IOException;

import static com.just_n.multithread.repository.FileStringsEnum.*;

public class XlsxSaver implements DataSaver {
    @Override
    public void save(ObjectStorage data) throws IOException {
        ExcelDataHandler edh = new ExcelDataHandler(new File(XLSX_OUT_FILE_NAME.getValue()));
        edh.saveObjectsToExcel(SUMMARY.getValue(), data.getListOfObjects(Employee.class));
    }
}
