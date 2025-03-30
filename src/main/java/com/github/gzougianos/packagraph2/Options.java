package com.github.gzougianos.packagraph2;

import lombok.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

@Builder
@Slf4j
public record Options(List<String> sourceDirectories, boolean excludeExternals,
                      List<ShowNodes> showNodes, List<ShowEdges> showEdges, List<DefineStyle> defineStyles,
                      List<DefineConstant> defineConstant, String mainGraphStyle, ExportInto exportInto) {

    @Override
    public List<String> sourceDirectories() {
        return nonEmpty(sourceDirectories);
    }

    @Override
    public List<ShowEdges> showEdges() {
        return nonEmpty(showEdges);
    }

    @Override
    public List<ShowNodes> showNodes() {
        return nonEmpty(showNodes);
    }

    @Override
    public List<DefineStyle> defineStyles() {
        return nonEmpty(defineStyles);
    }

    @Override
    public List<DefineConstant> defineConstant() {
        return nonEmpty(defineConstant);
    }

    @Override
    public ExportInto exportInto() {
        if (exportInto == null) {
            return new ExportInto("packagraph.png", "png", false);
        }
        return exportInto;
    }

    private static <T> List<T> nonEmpty(List<T> list) {
        if (list == null)
            return java.util.List.of();
        return list;
    }

    public Map<String, String> mainGraphStyleAttributes() {
        if (mainGraphStyle() == null || mainGraphStyle().isBlank())
            return Collections.emptyMap();


        return resolveStyle(mainGraphStyle());
    }

    private Map<String, String> resolveStyle(String styleName) {
        var styleVal = findStyleValue(styleName);
        if (styleVal == null) {
            log.warn("Style with name {} not found.", styleName);
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<>();
        String[] pairs = styleVal.split(";");
        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty())
                continue;

            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = resolveConstants(keyValue[1].trim());
                result.put(key, value);
            }
        }
        return unmodifiableMap(result);
    }

    private String resolveConstants(String value) {
        Pattern pattern = Pattern.compile("\\$\\{(\\w+)}");
        Matcher matcher = pattern.matcher(value);
        StringBuilder resolved = new StringBuilder();

        while (matcher.find()) {
            String constantName = matcher.group(1);
            String resolvedValue = findConstantValue(constantName);
            matcher.appendReplacement(resolved, Matcher.quoteReplacement(resolvedValue));
        }
        matcher.appendTail(resolved);
        return resolved.toString();
    }

    private String findConstantValue(String constantName) {
        for (Options.DefineConstant constant : reverse(defineConstant)) {
            if (constant.name().equals(constantName)) {
                return constant.value();
            }
        }
        return constantName;
    }

    private <T> List<T> reverse(List<T> list) {
        var copy = new ArrayList<>(list);
        Collections.reverse(copy);
        return copy;
    }

    private String findStyleValue(String styleName) {
        for (var style : reverse(defineStyles)) {
            if (style.name.equals(styleName)) {
                return style.value;
            }
        }
        return null;
    }

    public String nameOf(Node node) {
        for (var showNode : reverse(showNodes)) {
            if (showNode.covers(node) && showNode.as() != null) {
                return node.packag().name().replaceAll(showNode.packag(), showNode.as()).trim();
            }
        }
        return node.packag().name();
    }

    public Map<String, String> styleOf(Node node) {
        for (var showNode : reverse(showNodes)) {
            if (showNode.covers(node) && showNode.style() != null) {
                return resolveStyle(showNode.style());
            }
        }
        return Collections.emptyMap();
    }

    public Options withBaseDir(File dir) {
        if (!dir.isDirectory())
            throw new IllegalArgumentException(dir + " is not a directory.");

        List<String> relocatedSources = relocateSourceDirectories(dir);
        var relocatedExport = exportInto().withBaseDir(dir);

        return new Options(relocatedSources, excludeExternals(),
                showNodes(), showEdges(), defineStyles(),
                defineConstant, mainGraphStyle(), relocatedExport);
    }

    private List<String> relocateSourceDirectories(File dir) {
        List<String> sourceDirectories = new ArrayList<>();
        for (String sourceDir : this.sourceDirectories()) {
            if (new File(sourceDir).isAbsolute())
                sourceDirectories.add(sourceDir);
            else
                sourceDirectories.add(dir.toPath().resolve(sourceDir).toString());
        }
        return unmodifiableList(sourceDirectories);
    }

    public record ShowNodes(String packag, String as, String style) {

        public boolean covers(Node node) {
            return coversByPattern(packag, node.packag().name());
        }
    }

    public record ShowEdges(String packageFrom, String packageTo, String style, String fromNodeStyle,
                            String toNodeStyle) {

    }

    public record DefineStyle(String name, String value) {

    }

    public record DefineConstant(String name, String value) {

    }

    public record ExportInto(String filePath, String fileType, boolean overwrite) {

        public ExportInto withBaseDir(File baseDir) {
            if (new File(filePath).isAbsolute())
                return this;

            if (!baseDir.isDirectory())
                throw new IllegalArgumentException(baseDir + " is not a directory.");

            return new ExportInto(baseDir.toPath().resolve(filePath).toString(), fileType, overwrite);
        }
    }

    private static boolean coversByPattern(String val, String valueToCover) {
        return val.trim().equals(valueToCover) || valueToCover.matches(val.trim());
    }
}
