package com.just_n.multithread.repository.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JsonDataHandler {
    private final Gson gson;
    private final File file;

    public JsonDataHandler(File file){
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        this.file = file;
    }

    public <T> void saveToJson(T data) throws IOException {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("Невозможно создать файл: " + e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        }
    }

    public <T> T loadFromJson(Class<T> tClass) throws IOException{
        if(!file.exists())
            return null;

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, tClass);
        }
    }
}