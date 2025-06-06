package com.journal_app.repository.savers;

import com.journal_app.repository.DataSaver;
import com.journal_app.repository.JournalData;
import com.just_n.multithread.repository.AppData;

import java.io.*;

import static com.journal_app.repository.FileStringsEnum.BINARY_FILE_NAME;

public class BinarySaver implements DataSaver {
    public BinarySaver() {}

    @Override
    public void save(AppData data) throws IOException {
        File file = new File(BINARY_FILE_NAME.getValue());
        if (!file.exists())
            file.createNewFile();

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new BufferedOutputStream(
                                     new FileOutputStream(file)))) {
            oos.writeObject(AppData.getInstance());
        }
    }
}
