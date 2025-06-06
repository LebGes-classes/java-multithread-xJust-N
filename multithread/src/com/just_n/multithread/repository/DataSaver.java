package com.just_n.multithread.repository;

import java.io.IOException;

public interface DataSaver {    // интерфейс для стратегии сохранения
    void save(ObjectStorage data) throws IOException;
}
