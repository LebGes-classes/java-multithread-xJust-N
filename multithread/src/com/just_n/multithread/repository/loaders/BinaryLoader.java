package com.journal_app.repository.loaders;

import com.journal_app.repository.DataLoader;
import com.journal_app.repository.JournalData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static com.journal_app.repository.FileStringsEnum.BINARY_FILE_NAME;

public class BinaryLoader implements DataLoader {
    public BinaryLoader(){}

    @Override
    public void load() throws IOException {
        try(
        ObjectInputStream ois =
                new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(BINARY_FILE_NAME.getValue()))))
        {
            JournalData data = (JournalData) ois.readObject();
            JournalData.getInstance().setAll(data);
        } catch (ClassNotFoundException e) {
            throw new IOException("Класс не найден: " + e);
        }
    }
}
