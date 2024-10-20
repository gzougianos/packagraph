package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.Arrays;
import java.util.List;


@Accessors(fluent = true)
@Builder
@AllArgsConstructor
public class PackagraphOptions {

    private static final String COMMA = ",";
    @Getter
    private boolean includeOnlyFromDirectories;
    private String[] directories;
    private List<Rename> renamings;


    private static PackagraphOptions fromJson(File optionsFile) {

        return null;
    }

    Package rename(Package packag) {
        if (isEmpty(renamings))
            return packag;

        for (var rename : renamings) {
            if (rename.refersTo(packag)) {
                return packag.renamed(rename.to().trim());
            }
        }
        return packag;
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
}
