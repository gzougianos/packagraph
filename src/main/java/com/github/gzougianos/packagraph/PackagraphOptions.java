package com.github.gzougianos.packagraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.Arrays;

@Getter
@Accessors(fluent = true)
@Builder
@AllArgsConstructor
public class PackagraphOptions {

    private boolean includeOnlyFromDirectories;
    private String[] directories;


    private static PackagraphOptions fromJson(File optionsFile) {

        return null;
    }

    public File[] directories() {
        return Arrays.stream(directories).map(File::new).toArray(File[]::new);
    }
}
