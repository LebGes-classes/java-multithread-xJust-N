package com.journal_app.repository.loaders;

import com.journal_app.repository.DataLoader;
import com.journal_app.repository.FileStringsEnum;
import com.journal_app.repository.JournalData;
import com.journal_app.repository.util.JsonDataHandler;

import java.io.File;
import java.io.IOException;

public class JsonLoader implements DataLoader {
    private final JsonDataHandler parser;

    public JsonLoader() {
        parser = new JsonDataHandler(new File(FileStringsEnum.JSON_FILE_NAME.getValue()));
    }

    @Override
    public void load() throws IOException {
        JournalData.getInstance().setAll(parser.loadFromJson(JournalData.class));
    }
}
