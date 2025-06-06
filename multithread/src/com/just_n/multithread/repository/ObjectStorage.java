package com.just_n.multithread.repository;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectStorage {
    public static final ObjectStorage instance = new ObjectStorage();
    private final Map<Integer, Object> objectMap = new ConcurrentHashMap<>();

    public ObjectStorage getInstance(){
        return instance;
    }
    private ObjectStorage(){}

    public <T extends Entity> void add(T object) {
        Objects.requireNonNull(object, "попытка передать null");
        objectMap.put(object.getId(), object);
    }

    public <T extends Entity> void remove(T object) {
        Objects.requireNonNull(object, "попытка удалить null");
        objectMap.remove(object.getId());
    }

    public void remove(int id) {
        objectMap.remove(id);
    }

    public <T> T get(Class<T> tClass, int id) {
        Object obj = objectMap.get(id);
        if (tClass.isInstance(obj))
            return (T) obj;

        return null;
    }
}