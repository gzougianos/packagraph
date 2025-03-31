package com.github.gzougianos.packagraph2.core;

import java.io.*;
import java.util.*;
import java.util.stream.*;

final class JavaFilesFinder {

    public static List<File> findWithin(Collection<File> directories) {
        return findWithin(directories.toArray(new File[0]));
    }

    public static List<File> findWithin(File... directories) {
        verifyAllExistAndAreDirectories(directories);

        return Arrays.stream(directories)
                .flatMap(JavaFilesFinder::findJavaFiles)
                .toList();

    }

    private static Stream<File> findJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();

        // Get all files and subdirectories in the current directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively search subdirectories
                    javaFiles.addAll(findJavaFiles(file).toList());
                } else if (file.getName().endsWith(".java")) {
                    // Add Java files to the list
                    javaFiles.add(file);
                }
            }
        }

        return javaFiles.stream();
    }

    private static void verifyAllExistAndAreDirectories(File[] directories) {
        for (File directory : directories) {
            if (!directory.exists()) {
                throw new IllegalArgumentException("Directory not found: " + directory.getAbsolutePath());
            }

            if (!directory.isDirectory()) {
                throw new IllegalArgumentException("Directory is a file: " + directory.getAbsolutePath());
            }
        }
    }
}
