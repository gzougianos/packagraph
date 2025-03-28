package com.github.gzougianos.packagraph2;

import java.io.*;
import java.nio.file.*;

public class TempDir {
    private final File dir;

    TempDir() throws IOException {
        dir = Files.createTempDirectory("test").toFile();
    }

    void addJavaFile(String fileName, String contents) throws IOException {
        File file = new File(dir, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contents);
        }
        file.deleteOnExit();
    }

    Path path() {
        return dir.toPath();
    }
}
