package com.assignment.recommendationsystem.component;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class CsvFileReader implements ExternalFileReader {
    @Override
    public List<String[]> readFile(File csvFile) throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            return csvReader.readAll();
        } catch (Exception e) {
            throw new IOException("Error reading CSV file", e);
        }
    }
}



