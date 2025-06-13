package com.just_n.multithread.repository;

import com.just_n.multithread.repository.ObjectStorage;

import java.io.IOException;

public interface DataSaver {    // интерфейс для стратегии сохранения
    void save(ObjectStorage data) throws IOException;
}
