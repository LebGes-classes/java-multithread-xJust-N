package com.just_n.multithread.repository;

public enum FileStringsEnum {
    TASKS("задачи"),
    EMPLOYEES("сотрудники"),
    SUMMARY("итоги дня"),

    FILENAME("task_app"),
    XLSX_IN_FILE_NAME("resources/" + FILENAME.getValue() + "_in.xlsx"),
    XLSX_OUT_FILE_NAME("resources/" + FILENAME.getValue() + "_out.xlsx");

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