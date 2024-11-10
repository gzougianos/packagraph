package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.PackageName;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableMap;


@Accessors(fluent = true)
@Builder
@Slf4j
@AllArgsConstructor
public class PackagraphOptions {
    private static final Map<String, String> EMPTY_STYLE = Map.of();
    public static final String DEFAULT_STYLE_NAME = "default";
    private static final Style DEFAULT_STYLE = new Style(DEFAULT_STYLE_NAME, EMPTY_STYLE);
    private static final String COMMA = ",";

    private static final Output DEFAULT_OUTPUT = new Output("/packagraph.png", false, EMPTY_STYLE);

    @Getter
    private boolean includeOnlyFromDirectories;
    private String[] directories;
    private List<Definition> definitions;
    private List<Cluster> clusters;
    private Output output;
    private List<Style> nodeStyles;
    private List<Style> edgeStyles;


    public boolean allowsOverwriteOutput() {
        return output().overwrite();
    }

    public Map<String, String> nodeStyleOf(PackageName packag) {
        final var defaultStyle = nodeStyleWithName(DEFAULT_STYLE_NAME).orElse(DEFAULT_STYLE);

        return findDefinitionForRenamed(packag)
                .map(def -> getNodeStyleForDefinition(def, defaultStyle))
                .orElse(defaultStyle.attributes());
    }

    public Map<String, String> edgeInStyleOf(PackageName packag) {
        final var defaultStyle = edgeStyleWithName(DEFAULT_STYLE_NAME).orElse(DEFAULT_STYLE);

        return findDefinitionForRenamed(packag)
                .map(def -> {
                    return getEdgeInStyleForDefinition(def, defaultStyle);
                })
                .orElse(defaultStyle.attributes());
    }

    private Map<String, String> getEdgeInStyleForDefinition(Definition def, Style defaultStyle) {
        var defaultEdgeStyle = edgeStyleWithName(DEFAULT_STYLE_NAME).orElse(DEFAULT_STYLE);
        if (def.edgeInStyle() instanceof String edgeStyleName) {
            var edgeStyle = edgeStyleWithName(edgeStyleName).orElse(defaultEdgeStyle);
            return unmodifiableMap(inheritProperties(edgeStyle.attributes(), defaultStyle.attributes()));
        } else if (def.edgeInStyle() instanceof Map<?, ?> innerEdgeStyle) {
            var edgeStyle = inheritProperties(stringifyMap(innerEdgeStyle), defaultEdgeStyle.attributes());
            return unmodifiableMap(edgeStyle);
        }
        return defaultEdgeStyle.attributes();
    }

    private Map<String, String> getNodeStyleForDefinition(Definition def, final Style defaultStyle) {
        final var toolTip = def.packages();
        var style = defaultStyle.attributes();

        if (def.nodeStyle() instanceof String styleName) {
            var nodeStyle = nodeStyleWithName(styleName).orElseGet(() -> {
                log.warn("Node style with name {} not found. Will use default style.", styleName);
                return DEFAULT_STYLE;
            });
            style = inheritProperties(nodeStyle.attributes(), defaultStyle.attributes());
        } else if (def.nodeStyle() instanceof Map<?, ?> innerNodeStyle) {
            style = inheritProperties(stringifyMap(innerNodeStyle), defaultStyle.attributes());
        }

        return withTooltip(style, toolTip);
    }

    private static Map<String, String> withTooltip(Map<String, String> style, String tooltip) {
        var copy = new HashMap<>(style);
        copy.putIfAbsent("tooltip", tooltip);
        return unmodifiableMap(copy);
    }

    private static Map<String, String> stringifyMap(Map<?, ?> map) {
        return map.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Optional<Style> nodeStyleWithName(String name) {
        return nodeStyles().stream().filter(style -> style.name().equals(name))
                .findFirst();
    }

    private Optional<Style> edgeStyleWithName(String name) {
        return edgeStyles().stream().filter(style -> style.name().equals(name))
                .findFirst();
    }

    private List<Style> nodeStyles() {
        return isEmpty(nodeStyles) ? List.of(DEFAULT_STYLE) : nodeStyles;
    }

    private List<Style> edgeStyles() {
        return isEmpty(edgeStyles) ? List.of(DEFAULT_STYLE) : edgeStyles;
    }


    public Map<String, String> clusterStyleOf(String clusterName) {
        return alwaysNonNull(clusters).stream()
                .filter(cluster -> cluster.name().equals(clusterName))
                .findFirst()
                .map(Cluster::style)
                .orElse(EMPTY_STYLE);
    }

    private static Map<String, String> inheritProperties(Map<String, String> style, Map<String, String> defaultStyle) {
        Objects.requireNonNull(style);

        if (Objects.equals(style, defaultStyle)) {
            return style;
        }

        if (inheritGlobalExplictlyDisabled(style)) {
            return style;
        }

        Map<String, String> result = new HashMap<>(style);

        defaultStyle.forEach(result::putIfAbsent);
        return unmodifiableMap(result);
    }

    private static boolean inheritGlobalExplictlyDisabled(Map<String, String> style) {
        return "false".equalsIgnoreCase(String.valueOf(style.get("inheritDefault")));
    }


    private Optional<Definition> findDefinitionForRenamed(PackageName packag) {
        return definitions().stream()
                .filter(definition -> definition.refersToRenamed(packag))
                .findFirst();
    }

    public Map<String, String> mainGraphStyle() {
        return output().style() == null ? EMPTY_STYLE : output().style();
    }

    private Output output() {
        return output == null ? DEFAULT_OUTPUT : output;
    }

    public File outputFile() {
        return new File(output().path());
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


    PackageName rename(PackageName packag) {
        if (isEmpty(definitions))
            return packag;

        for (var rename : definitions()) {
            if (rename.refersTo(packag)) {
                String pattern = rename.findMatchingPattern(packag);
                String result = packag.name().replaceAll(pattern, rename.as()).trim();
                return new PackageName(result);
            }
        }
        return packag;
    }

    public File[] directories() {
        return Arrays.stream(directories).map(File::new).toArray(File[]::new);
    }


    public Optional<String> clusterOf(PackageName packag) {
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

        public boolean refersTo(PackageName packag) {
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

    private record Definition(String packages, String as, Object nodeStyle, Object edgeInStyle) {
        private boolean refersTo(PackageName packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .anyMatch(pattern -> packag.name().matches(pattern));
        }

        private boolean refersToRenamed(PackageName packag) {
            return refersTo(packag) || packag.name().equals(as.trim());
        }

        String findMatchingPattern(PackageName packag) {
            return Arrays.stream(packages.split(COMMA))
                    .filter(pattern -> !isEmpty(pattern))
                    .filter(pattern -> packag.name().matches(pattern))
                    .findFirst().orElseThrow();
        }

    }


    private record Output(String path, Boolean overwrite, Map<String, String> style) {
    }

    private record Style(String name, Map<String, String> attributes) {

        Style {
            if (isEmpty(name)) {
                throw new IllegalArgumentException("Style must have a name");
            }
        }

        public Map<String, String> attributes() {
            if (this.attributes == null) {
                return EMPTY_STYLE;
            }
            return unmodifiableMap(this.attributes);
        }
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

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<List<Style>>() {
        }.getType(), new StyleDeserializer());
        Gson gson = gsonBuilder.create();
        return verify(gson.fromJson(jsonWithConstantsReplaced, PackagraphOptions.class));
    }

    private static PackagraphOptions verify(PackagraphOptions options) {
        if (options.directories == null || options.directories.length == 0)
            throw new IllegalArgumentException("No directories specified");

        if (options.outputFile().exists() && options.outputFile().isDirectory())
            throw new IllegalArgumentException("Output file already exists and is directory: " + options.outputFile().getAbsolutePath());

        if (options.outputFile().exists() && !options.allowsOverwriteOutput())
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


    private static class StyleDeserializer implements JsonDeserializer<List<Style>> {
        @Override
        public List<Style> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<Style> styles = new ArrayList<>();
            JsonObject nodeStylesObject = json.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : nodeStylesObject.entrySet()) {
                String styleName = entry.getKey();
                JsonObject attributesObject = entry.getValue().getAsJsonObject();

                // Map attributes to a HashMap
                Map<String, String> attributes = new HashMap<>();
                for (Map.Entry<String, JsonElement> attributeEntry : attributesObject.entrySet()) {
                    attributes.put(attributeEntry.getKey(), attributeEntry.getValue().getAsString());
                }

                // Add the new Style object to the list
                styles.add(new Style(styleName, attributes));
            }

            return styles;
        }
    }
}
