package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.antlr4.*;
import com.github.gzougianos.packagraph.core.*;

import java.io.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing .pg file argument.");
        }
        var inputFile = new File(args[0]).getCanonicalFile();
        verifyExistsAndIsNotADirectory(inputFile);

        var inputFileDirectory = inputFile.getParentFile();
        var inputContents = new String(Files.readAllBytes(inputFile.toPath()));

        Options options = PgLangInterpreter.interprete(inputContents).withBaseDir(inputFileDirectory);
        Packagraph packagraph = Packagraph.create(options);
        new GraphvizRenderer(packagraph).render();
    }

    private static void verifyExistsAndIsNotADirectory(File inputFile) {
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Input file: " + inputFile.getAbsolutePath() + " does not exist.");
        }

        if (!inputFile.isFile()) {
            throw new IllegalArgumentException("Input file: " + inputFile.getAbsolutePath() + " is not a file.");
        }
    }
}
