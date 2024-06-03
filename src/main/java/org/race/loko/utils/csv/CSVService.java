package org.race.loko.utils.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSVService {
    public static List<String> getHeader(Field[] fields) {
        List<String> header = new ArrayList<>();
        for (Field field : fields) {
            CsvBindName annotation = field.getAnnotation(CsvBindName.class);
            if (annotation != null && !annotation.value().isEmpty()) {
                header.add(annotation.value());
            } else {
                header.add(capitalize(field.getName()));
            }
        }
        return header;
    }

    public static <T> List<String> getRecords(Field[] fields, T obj, Class<?> clazz) throws Exception {
        List<String> record = new ArrayList<>();
        for (Field field : fields) {
            Method method = getGetterMethod(clazz, field.getName());
            Object value = method.invoke(obj);
            record.add(value.toString());
        }
        return record;
    }

    // Helper method to get the getter method based on the field name
    private static Method getGetterMethod(Class<?> clazz, String fieldName) throws NoSuchMethodException, NoSuchFieldException {
        CsvBindName annotation = clazz.getDeclaredField(fieldName).getAnnotation(CsvBindName.class);
        if (annotation != null && !annotation.value().isEmpty()) {
            return clazz.getMethod("get" + capitalize(annotation.value()));
        } else {
            return clazz.getMethod("get" + capitalize(fieldName));
        }
    }

    public static <T> void exportCollectionToCSV(Collection<T> set, String csvPath) throws Exception {
        if (set.isEmpty()) {
            return;
        }

        Class<?> clazz = set.iterator().next().getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> header = getHeader(fields);

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvPath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header.toArray(new String[0])));
        ) {
            List<String> record;
            for (T obj : set) {
                record = getRecords(fields, obj, clazz);
                csvPrinter.printRecord(record);
            }
            csvPrinter.flush();
        }
    }

    public static <T> List<T> readObjectsFromCSV(Class<T> clazz, Reader reader) throws Exception {
        return readObjectsFromCSV(clazz,reader,CSVFormat.DEFAULT);
    }

    public static <T> List<T> readObjectsFromCSV(Class<T> clazz, Reader reader, CSVFormat csvFormat) throws Exception {
        List<T> objects = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(reader, csvFormat)) {
            boolean headerSkipped = false;
            for (CSVRecord csvRecord : csvParser) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the header
                }
                T object = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < csvRecord.size(); i++) {
                    String fieldName = fields[i].getName();
                    Method setter = getSetterMethod(clazz, fieldName);
                    assert setter != null;
                    setter.invoke(object, csvRecord.get(i));
                }
                objects.add(object);
            }
        }
        return objects;
    }


    // Helper method to get the setter method based on the field name
    private static Method getSetterMethod(Class<?> clazz, String fieldName) throws NoSuchMethodException, NoSuchFieldException {
        CsvBindName annotation = clazz.getDeclaredField(fieldName).getAnnotation(CsvBindName.class);
        if (annotation != null && !annotation.value().isEmpty()) {
            return clazz.getMethod("set" + capitalize(fieldName), String.class);
        }
        return null;
    }

    // Helper method to capitalize the first letter of a string
    private static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}