package com.github.gzougianos.packagraph2.core;

import java.io.*;
import java.nio.file.*;

public class TempDir {
    private final File dir;

    public TempDir() {
        try {
            dir = Files.createTempDirectory("test").toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String pathAsString() {
        return dir.getAbsolutePath();
    }

    File file() {
        return dir;
    }

    public File addJavaFile(String fileName, String contents) throws IOException {
        File file = new File(dir, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contents);
        }
        file.deleteOnExit();
        return file;
    }

    Path path() {
        return dir.toPath();
    }
}
