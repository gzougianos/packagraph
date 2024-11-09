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
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableMap;


@Accessors(fluent = true)
@Builder
@AllArgsConstructor
public class PackagraphOptions {

    private static final Map<String, String> EMPTY_STYLE = Map.of();
    private static final String COMMA = ",";
    @Getter
    private boolean includeOnlyFromDirectories;
    private String[] directories;
    private List<Definition> definitions;
    private List<Cluster> clusters;
    private Output output;
    private Map<String, String> globalStyle;
    private Map<String, String> globalEdgeStyle;


    public boolean allowsOverwriteImageOutput() {
        return output.overwrite;
    }

    public Map<String, String> styleOf(Package packag) {
        return findDefinitionForRenamed(packag)
                .map(def -> {
                    var style = new HashMap<>(inheritProperties(def.style(), globalStyle()));
                    style.putIfAbsent("tooltip", def.packages());
                    return Collections.unmodifiableMap(style);
                })
                .orElse(globalStyle());
    }

    public Map<String, String> edgeInStyleOf(Package packag) {
        return findDefinitionForRenamed(packag)
                .map(Definition::edgeInStyle)
                .map(style -> inheritProperties(style, globalEdgeStyle()))
                .orElse(globalEdgeStyle());
    }

    public Map<String, String> clusterStyleOf(String clusterName) {
        return alwaysNonNull(clusters).stream()
                .filter(cluster -> cluster.name().equals(clusterName))
                .findFirst()
                .map(Cluster::style)
                .orElse(EMPTY_STYLE);
    }

    private static Map<String, String> inheritProperties(Map<String, String> style1, Map<String, String> style2) {
        if (style2 == null)
            return style1;

        if (inheritGlobalExplictlyDisabled(style1)) {
            return style1;
        }

        Map<String, String> result = new HashMap<>(style1);

        style2.forEach(result::putIfAbsent);
        return unmodifiableMap(result);
    }

    private static boolean inheritGlobalExplictlyDisabled(Map<String, String> style) {
        return "false".equalsIgnoreCase(String.valueOf(style.get("inheritGlobal")));
    }


    private Optional<Definition> findDefinitionForRenamed(Package packag) {
        return definitions().stream()
                .filter(definition -> definition.refersToRenamed(packag))
                .findFirst();
    }

    private Map<String, String> globalEdgeStyle() {
        return globalEdgeStyle == null ? EMPTY_STYLE : globalEdgeStyle;
    }

    public Map<String, String> globalStyle() {
        return globalStyle == null ? EMPTY_STYLE : globalStyle;
    }

    public Map<String, String> mainGraphStyle() {
        return output.style == null ? EMPTY_STYLE : output.style;
    }


    public File outputFile() {
        return new File(output.path);
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


    Package rename(Package packag) {
        if (isEmpty(definitions))
            return packag;

        for (var rename : definitions()) {
            if (rename.refersTo(packag)) {
                String pattern = rename.findMatchingPattern(packag);
                String result = packag.name().replaceAll(pattern, rename.as()).trim();
                return packag.renamed(result);
            }
        }
        return packag;
    }

    public File[] directories() {
        return Arrays.stream(directories).map(File::new).toArray(File[]::new);
    }


    public Optional<String> clusterOf(Package packag) {
        if (isEmpty(clusters))
            return Optional.empty();

        for (var cluster : clusters()) {
            if (cluster.refersTo(packag)) {
                return Optional.of(cluster.name());
            }
        }
        return Optional.empty();
    }

    private record Cluster(String packages, String name, Map<String, String> style) {

        public boolean refersTo(Package packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .anyMatch(pattern -> packag.name().matches(pattern));
        }

        public String name() {
            if (this.name != null) {
                return this.name;
            }
            return "CLUSTER:" + packages;
        }
    }

    private List<Definition> definitions() {
        return alwaysNonNull(definitions);
    }

    private List<Cluster> clusters() {
        return alwaysNonNull(clusters);
    }

    private record Definition(String packages, String as, Map<String, String> style, Map<String, String> edgeInStyle) {
        private boolean refersTo(Package packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .anyMatch(pattern -> packag.name().matches(pattern));
        }

        private boolean refersToRenamed(Package packag) {
            return refersTo(packag) || packag.name().equals(as.trim());
        }

        public String findMatchingPattern(Package packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .filter(pattern -> packag.name().matches(pattern))
                    .findFirst().orElseThrow();
        }
    }


    private record Output(String path, boolean overwrite, Map<String, String> style) {
    }

    public static PackagraphOptions fromJson(File optionsFile) throws IOException {
        verifyExistsAndIsFile(optionsFile);

        String jsonContent = Files.readAllLines(optionsFile.toPath())
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));

        return fromJson(jsonContent);
    }

    public static PackagraphOptions fromJson(String optionsJson) {
        String jsonWithConstantsReplaced = HJsonWrapper.readHJsonWithConstants(optionsJson);

        Gson gson = new Gson();
        return verify(gson.fromJson(jsonWithConstantsReplaced, PackagraphOptions.class));
    }

    private static PackagraphOptions verify(PackagraphOptions options) {
        if (options.directories == null || options.directories.length == 0)
            throw new IllegalArgumentException("No directories specified");

        if (options.output == null || options.output.path == null)
            throw new IllegalArgumentException("No output specified");

        if (options.outputFile().exists() && options.outputFile().isDirectory())
            throw new IllegalArgumentException("Output file already exists and is directory: " + options.outputFile().getAbsolutePath());

        if (options.outputFile().exists() && !options.output.overwrite)
            throw new IllegalArgumentException("Output file already exists: " + options.outputFile().getAbsolutePath());

        return options;
    }

    private static <T> List<T> alwaysNonNull(List<T> list) {
        return list == null ? List.of() : list;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private static void verifyExistsAndIsFile(File optionsFile) {
        if (!optionsFile.exists())
            throw new IllegalArgumentException("Options file not found: " + optionsFile.getAbsolutePath());

        if (optionsFile.isDirectory())
            throw new IllegalArgumentException("Options file is a directory: " + optionsFile.getAbsolutePath());
    }
}
