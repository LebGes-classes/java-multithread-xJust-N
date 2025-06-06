package com.journal_app.repository;

public enum FileStringsEnum {   //Строки связанные с входным файлом
    TEACHERS("преподаватели"),  //Названия ожидаемых страниц в xlsx
    SUBJECTS("предметы"),
    STUDENTS("студенты"),
    XLSX_FILE_NAME("resources/journal.xlsx"),
    JSON_FILE_NAME("resources/journal.json"),
    BINARY_FILE_NAME("resources/journal.bin");

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