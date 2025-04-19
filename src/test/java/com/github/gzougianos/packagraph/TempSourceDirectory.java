package com.github.gzougianos.packagraph;

import java.io.*;
import java.nio.file.*;

public class TempSourceDirectory {
    private final File dir;

    public TempSourceDirectory() {
        try {
            dir = Files.createTempDirectory("test").toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String pathAsString() {
        return dir.getAbsolutePath();
    }

    public File file() {
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

    public Path path() {
        return dir.toPath();
    }
}
