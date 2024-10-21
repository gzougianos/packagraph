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
    private List<Rename> renames;


    public static PackagraphOptions fromJson(File optionsFile) throws IOException {
        verifyExistsAndIsFile(optionsFile);

        String jsonContent = Files.readAllLines(optionsFile.toPath())
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));

        return fromJson(jsonContent);
    }

    public static PackagraphOptions fromJson(String optionsJson) {
        Gson gson = new Gson();
        return gson.fromJson(optionsJson, PackagraphOptions.class);
    }

    private static void verifyExistsAndIsFile(File optionsFile) {
        if (!optionsFile.exists())
            throw new IllegalArgumentException("Options file not found: " + optionsFile.getAbsolutePath());

        if (optionsFile.isDirectory())
            throw new IllegalArgumentException("Options file is a directory: " + optionsFile.getAbsolutePath());
    }

    Package rename(Package packag) {
        if (isEmpty(renames))
            return packag;

        for (var rename : renames) {
            if (rename.refersTo(packag)) {
                return packag.renamed(rename.to().trim());
            }
        }
        return packag;
    }

    List<Rename> renames() {
        return renames;
    }

    public File[] directories() {
        return Arrays.stream(directories).map(File::new).toArray(File[]::new);
    }

    record Rename(String rename, String to) {
        private boolean refersTo(Package packag) {
            return Arrays.stream(rename.split(COMMA))
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

    record Cluster(String name, String[] packages) {
    }
}
