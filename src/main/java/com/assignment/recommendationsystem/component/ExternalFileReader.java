package com.assignment.recommendationsystem.component;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExternalFileReader {
    List<String[]> readFile(File file) throws IOException;
}
