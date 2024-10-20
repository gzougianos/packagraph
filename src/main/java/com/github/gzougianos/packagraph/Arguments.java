package com.github.gzougianos.packagraph;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public final class Arguments {

    private File optionsFile;
    @Getter
    private Collection<File> directories;
    private static HelpFormatter helpFormatter = new HelpFormatter();

    private Arguments() {
    }

    public Optional<File> optionsFile() {
        return Optional.ofNullable(optionsFile);
    }

    static void setHelpFormatter(HelpFormatter helpFormatter) {
        Arguments.helpFormatter = helpFormatter;
    }

    public static Arguments of(String... args) {
        Options options = new Options();

        // Add option for the file path (-o)
        Option outputFile = new Option("o", "options", true, "Package diagram options");
        outputFile.setRequired(false);
        options.addOption(outputFile);

        // Add option for directory paths (-d)
        Option dirOption = new Option("d", "dir", true, "Multiple directory paths to scan for packages");
        dirOption.setRequired(true);
        dirOption.setArgs(Option.UNLIMITED_VALUES);  // Allow multiple -d options
        options.addOption(dirOption);

        // Create a parser
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            final var optionsFile = getOptionsFile(cmd);
            final var directories = getDirectories(cmd);

            return new Arguments(optionsFile, directories);

        } catch (ParseException e) {
            helpFormatter.printHelp("-o myOptions.json -d ./myproject1/src/main/java ./myproject1/src/main/java", options);
            throw new IllegalArgumentException("Failed to parse command line arguments.", e);
        }
    }

    private static List<File> getDirectories(CommandLine cmd) {
        if (!cmd.hasOption("d"))
            throw new IllegalArgumentException("No directory paths provided.");

        String[] dirPaths = cmd.getOptionValues("d");
        List<File> directories = Stream.of(dirPaths)
                .map(File::new)
                .toList();

        directories.stream().filter(f -> !f.exists())
                .findAny().ifPresent(f -> {
                    throw new IllegalArgumentException("Directory not found: " + f.getAbsolutePath());
                });

        directories.stream().filter(File::isFile)
                .findAny().ifPresent(f -> {
                    throw new IllegalArgumentException("Directory is a file: " + f.getAbsolutePath());
                });

        if (directories.isEmpty())
            throw new IllegalArgumentException("No directory paths provided.");

        return directories;
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
