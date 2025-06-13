package com.just_n.multithread.repository.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class ExcelDataHandler {
    private final File file;

    public ExcelDataHandler(File file){
        this.file = file;
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Файл не существует и его невозможно создать :(\n" + e);
            }
        }
    }

    public <T> List<T> parseObjectsFromSheetName(String sheetName, Class<T> tClass) throws IOException {    //Возвращает список спаршенных объектов, где строчка в xlsx - параметры конструктора
        List<T> listOfObjects = new LinkedList<>();

        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Objects.requireNonNull(sheet, "страница " + sheetName + " в .xlsx не найдена");

            int startRow = sheet.getTopRow() + 1;   // Пропуск верхней строчки с обозначениями
            int lastRow = sheet.getLastRowNum() + 1;
            for (int j = startRow; j < lastRow; j++) {
                Row row = sheet.getRow(j);

                int startCell = row.getFirstCellNum();
                int lastCell = row.getLastCellNum();
                Class<?>[] paramsTypes = new Class<?>[lastCell - startCell];
                Object[] params = new Object[lastCell - startCell];
                for (int k = startCell; k < lastCell; k++) {
                    Cell cell = row.getCell(k);
                    switch (cell.getCellType()) {
                        case STRING:
                            params[k - startCell] = cell.getStringCellValue();
                            paramsTypes[k - startCell] = String.class;
                            break;
                        case NUMERIC:
                            params[k - startCell] = (int) cell.getNumericCellValue();
                            paramsTypes[k - startCell] = int.class;
                            break;
                        case BOOLEAN:
                            params[k - startCell] = cell.getBooleanCellValue();
                            paramsTypes[k - startCell] = boolean.class;
                            break;
                        default:
                            throw new IOException("Ячейка " + (char) (k + 65) + (j) + " имеет неизвестный тип");
                    }

                }
                try {
                    Constructor<T> constructor = tClass.getConstructor(paramsTypes);
                    listOfObjects.add(constructor.newInstance(params));
                } catch (NoSuchMethodException e) {
                    throw new NoSuchMethodException("Класс <" + tClass.getSimpleName()+ ">не имеет нужного конструктора для типов параметров: " + (Arrays.toString(paramsTypes)));
                }

            }
        } catch (FileNotFoundException e) {
            throw new IOException("Файл не найден: " + e);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException e) {
            throw new IOException("Ошибка вызова конструктора:\n" + e);
        } catch (Exception e) {
            throw new IOException("Непредвиденная ошибка: " + e);
        }
        return listOfObjects;
    }

    public <T> void saveObjectsToExcel(String sheetName, Collection<T> objects) throws IOException {
        Objects.requireNonNull(objects, "передан null");
        try (XSSFWorkbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.getSheet(sheetName) == null? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
            List<Field> fields =
                    Arrays.stream(objects.iterator().next().getClass().getDeclaredFields())
                            .filter(field -> field.isAnnotationPresent(Excel.class))
                            .toList();

            int rowNum = sheet.getFirstRowNum();
            if(rowNum == -1)
                rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            for(int i = 0; i < fields.size(); i++) {
                String fieldName = fields.get(i).getName();
                row.createCell(i).setCellValue(fieldName);
            }

            for(T obj : objects) {
                row = sheet.createRow(rowNum++);
                for(int i = 0; i < fields.size(); i++){
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    try {
                        writeFieldToCell(row.createCell(i), field.getType(), field.get(obj));
                    } catch (IOException e){
                        throw new IOException("Не удалось записать значение в ячейку\n" + e);
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }
        } catch (IllegalAccessException e) {
            throw new IOException("Ошибка доступа при записи объекта в xlsx\n" + e);
        }
    }

    private void writeFieldToCell(Cell cell, Class<?> type, Object o) throws IOException {
        if(type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)){
            cell.setCellValue((int) o);
        }
        else if(type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)){
            cell.setCellValue((boolean) o);
        }
        else if(type.isAssignableFrom(String.class) || type.isAssignableFrom(UUID.class)){
            cell.setCellValue((String) o);
        }
        else if(type.isAssignableFrom(LocalDate.class)){
            cell.setCellValue((LocalDate) o);
        }
        else if(type.isAssignableFrom(LocalDateTime.class)){
            cell.setCellValue((LocalDateTime) o);
        }
        else{
            throw new IOException("Невозможно записать поле класса в ячейку: неизвестный тип: " + type.getSimpleName());
        }
    }
}