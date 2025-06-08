package com.just_n.multithread.repository;

public enum FileStringsEnum {
    TASKS("задачи"),
    EMPLOYEES("сотрудники"),

    FILENAME("task_app"),
    XLSX_FILE_NAME("multithread/resources/" + FILENAME.getValue() + ".xlsx"),
    JSON_FILE_NAME("multithread/resources/" + FILENAME.getValue() + ".json"),
    BINARY_FILE_NAME("multithread/resources/" + FILENAME.getValue() + ".bin");

    private final String value;

    FileStringsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FileStringsEnum fromString(String value) {
        for (FileStringsEnum e : values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + value);
    }
}