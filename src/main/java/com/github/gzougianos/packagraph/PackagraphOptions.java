package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Accessors(fluent = true)
@Builder
@AllArgsConstructor
public class PackagraphOptions {

    private static final String COMMA = ",";
    @Getter
    private boolean includeOnlyFromDirectories;
    private String[] directories;
    private List<Definition> definitions;
    private OutputImage outputImage;


    public static PackagraphOptions fromJson(File optionsFile) throws IOException {
        verifyExistsAndIsFile(optionsFile);

        String jsonContent = Files.readAllLines(optionsFile.toPath())
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));

        return fromJson(jsonContent);
    }

    public static PackagraphOptions fromJson(String optionsJson) {
        Gson gson = new Gson();
        return verify(gson.fromJson(optionsJson, PackagraphOptions.class));
    }

    private static PackagraphOptions verify(PackagraphOptions options) {
        if (options.directories == null || options.directories.length == 0)
            throw new IllegalArgumentException("No directories specified");

        if (options.outputImage.path == null)
            throw new IllegalArgumentException("No output image file specified");

        if (options.outputFile().exists() && options.outputFile().isDirectory())
            throw new IllegalArgumentException("Output file already exists and is directory: " + options.outputFile().getAbsolutePath());

        if (options.outputFile().exists() && !options.outputImage.overwrite)
            throw new IllegalArgumentException("Output file already exists: " + options.outputFile().getAbsolutePath());

        return options;
    }

    public boolean allowsOverwriteImageOutput() {
        return outputImage.overwrite;
    }


    public File outputFile() {
        return new File(outputImage.path);
    }

    public String outputFileType() {
        return getFileExtension(outputFile());
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf('.');

        if (lastIndexOfDot > 0 && lastIndexOfDot < name.length() - 1) {
            return name.substring(lastIndexOfDot + 1);
        }
        return ""; // or return null if you prefer
    }

    private static void verifyExistsAndIsFile(File optionsFile) {
        if (!optionsFile.exists())
            throw new IllegalArgumentException("Options file not found: " + optionsFile.getAbsolutePath());

        if (optionsFile.isDirectory())
            throw new IllegalArgumentException("Options file is a directory: " + optionsFile.getAbsolutePath());
    }

    Package rename(Package packag) {
        if (isEmpty(definitions))
            return packag;

        for (var rename : definitions) {
            if (rename.refersTo(packag)) {
                return packag.renamed(rename.as().trim());
            }
        }
        return packag;
    }

    public File[] directories() {
        return Arrays.stream(directories).map(File::new).toArray(File[]::new);
    }

    List<Definition> definitions() {
        if (isEmpty(definitions))
            return Collections.emptyList();

        return definitions;
    }

    record Definition(String packages, String as) {
        private boolean refersTo(Package packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .anyMatch(pattern -> packag.name().matches(pattern));
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private record OutputImage(String path, boolean overwrite) {

    }
}
