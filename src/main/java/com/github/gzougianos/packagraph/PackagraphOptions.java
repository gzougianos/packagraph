package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import com.github.gzougianos.packagraph.style.EdgeStyle;
import com.github.gzougianos.packagraph.style.GraphStyle;
import com.github.gzougianos.packagraph.style.NodeStyle;
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
import java.util.Map;
import java.util.Optional;
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
    private List<Cluster> clusters;
    private Output output;
    private NodeStyle globalStyle;
    private EdgeStyle globalEdgeStyle;


    public boolean allowsOverwriteImageOutput() {
        return output.overwrite;
    }

    public NodeStyle styleOf(Package packag) {
        return findDefinitionForRenamed(packag)
                .map(Definition::style)
                .map(style -> style.inheritFrom(globalStyle()))
                .orElse(globalStyle());
    }

    public EdgeStyle edgeInStyleOf(Package packag) {
        return findDefinitionForRenamed(packag)
                .map(Definition::edgeInStyle)
                .map(style -> style.inheritFrom(globalEdgeStyle()))
                .orElse(globalEdgeStyle());
    }

    private Optional<Definition> findDefinitionForRenamed(Package packag) {
        return definitions().stream()
                .filter(definition -> definition.refersToRenamed(packag))
                .findFirst();
    }

    private EdgeStyle globalEdgeStyle() {
        return globalEdgeStyle == null ? EdgeStyle.DEFAULT : globalEdgeStyle;
    }

    public NodeStyle globalStyle() {
        return globalStyle == null ? NodeStyle.DEFAULT : globalStyle;
    }

    public Map<String, String> mainGraphStyle() {
        return output.style == null ? Map.of() : output.style;
    }

    public GraphStyle clusterStyleOf(String clusterName) {
        return alwaysNonNull(clusters).stream()
                .filter(cluster -> cluster.name().equals(clusterName))
                .findFirst()
                .map(Cluster::style)
                .orElse(GraphStyle.DEFAULT);
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

    private record Cluster(String packages, String name, GraphStyle style) {

        public boolean refersTo(Package packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .anyMatch(pattern -> packag.name().matches(pattern));
        }
    }

    private List<Definition> definitions() {
        return alwaysNonNull(definitions);
    }

    private List<Cluster> clusters() {
        return alwaysNonNull(clusters);
    }

    private record Definition(String packages, String as, NodeStyle style, EdgeStyle edgeInStyle) {
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
