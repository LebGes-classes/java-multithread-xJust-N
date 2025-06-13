package com.just_n.multithread.repository;


import java.io.IOException;

public interface DataLoader {   //Интерфейс для стратегии загрузки
    void load(ObjectStorage data) throws IOException;
}

