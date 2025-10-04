package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.antlr4.*;
import com.github.gzougianos.packagraph.core.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing .pg file argument.");
        }
        var inputFile = new File(args[0]).getCanonicalFile();
        verifyExistsAndIsNotADirectory(inputFile);


        var inputFileDirectory = inputFile.getParentFile();
        byte[] bytes = Files.readAllBytes(inputFile.toPath());

        if (!isValidUTF8(bytes)) {
            throw new IllegalArgumentException("Input file: " + inputFile.getAbsolutePath() + " is not a text file.");
        }

        var inputContents = new String(bytes);

        Options options = PgLangInterpreter.interprete(inputContents).withBaseDir(inputFileDirectory);
        Packagraph packagraph = Packagraph.create(options);
        File output = new GraphvizRenderer(packagraph).render();
        log.warn("Output file: {}", output.getAbsolutePath());
    }

    public static boolean isValidUTF8(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            StandardCharsets.UTF_8.newDecoder().decode(buffer);
            return true;
        } catch (CharacterCodingException e) {
            return false;
        }
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
