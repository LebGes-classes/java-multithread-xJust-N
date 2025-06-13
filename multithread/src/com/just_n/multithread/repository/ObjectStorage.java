package com.just_n.multithread.repository;

import com.just_n.multithread.model.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectStorage {
    private static final ObjectStorage instance = new ObjectStorage();
    private final Map<Class<?>, Map<Integer, Object>> objectMap;

    public static ObjectStorage getInstance(){
        return instance;
    }
    private ObjectStorage(){
        objectMap = new HashMap<>();
    }

    public <T extends Entity> void add(T object) {
        Objects.requireNonNull(object, "попытка передать null");
        Map<Integer, Object> map = objectMap.computeIfAbsent(object.getClass(), k -> new LinkedHashMap<>());
        map.put(object.getId(), object);
    }

    public <T extends Entity> void remove(T object) {
        Objects.requireNonNull(object, "попытка удалить null");
        Map<Integer, Object> map = objectMap.get(object.getClass());
        Objects.requireNonNull(map, "попытка удалить в несуществующем хранилище");
        map.remove(object.getId());
    }

    public <T extends Entity> void remove(Class<T> tClass, int id) {
        Map<Integer, Object> map = objectMap.get(tClass);
        Objects.requireNonNull(map, "попытка удалить в несуществующем хранилище");
        map.remove(id);
    }

    public <T> T get(Class<T> tClass, int id) {
        Map<Integer, Object> map = objectMap.get(tClass);
        if(map != null){
            Object o = map.get(id);
            return tClass.isInstance(o) ? tClass.cast(o) : null;
        }
        return null;
    }
    public <T extends Entity> void addListOfObjects(List<T> list){
        list.forEach(this::add);
    }
    public <T extends Entity> List<T> getListOfObjects(Class<T> tClass){
        if(objectMap.containsKey(tClass))
            return objectMap.get(tClass).values().stream().map(tClass::cast).collect(Collectors.toList());
        return null;
    }
}