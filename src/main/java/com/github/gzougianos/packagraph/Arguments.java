package com.github.gzougianos.packagraph;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.cli.*;

import java.io.File;


@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public final class Arguments {

    private File optionsFile;
    private static HelpFormatter helpFormatter = new HelpFormatter();

    private Arguments() {
    }

    public File optionsFile() {
        return optionsFile;
    }

    static void setHelpFormatter(HelpFormatter helpFormatter) {
        Arguments.helpFormatter = helpFormatter;
    }

    public static Arguments of(String... args) {
        Options options = new Options();

        // Add option for the file path (-o)
        Option outputFile = new Option("o", "options", true, "Package diagram options");
        outputFile.setRequired(true);
        options.addOption(outputFile);

        // Create a parser
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            final var optionsFile = getOptionsFile(cmd);

            return new Arguments(optionsFile);

        } catch (ParseException e) {
            helpFormatter.printHelp("-o myOptions.json -d ./myproject1/src/main/java ./myproject2/src/main/java", options);
            throw new IllegalArgumentException("Failed to parse command line arguments.", e);
        }
    }

    private static File getOptionsFile(CommandLine cmd) {

        if (!cmd.hasOption("o"))
            return null;

        String optionsFilePath = cmd.getOptionValue("o");
        File optionsFile = new File(optionsFilePath);
        if (!optionsFile.exists())
            throw new IllegalArgumentException("Options file not found: " + optionsFilePath);

        if (optionsFile.isDirectory())
            throw new IllegalArgumentException("Options file is a directory: " + optionsFilePath);

        return optionsFile;

    }
}
