package com.github.gzougianos.packagraph2;

import lombok.*;

import java.util.*;

@Builder
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
            return List.of();
        return list;
    }

    public Optional<String> findStyle(String styleName) {
        for (var style : defineStyles) {
            if (style.name.equals(styleName))
                return Optional.of(style.value);
        }
        return Optional.empty();
    }

    public record ShowNodes(String packag, String as, String style) {

    }

    public record ShowEdges(String packageFrom, String packageTo, String style, String fromNodeStyle,
                            String toNodeStyle) {

    }

    public record DefineStyle(String name, String value) {

    }

    public record DefineConstant(String name, String value) {

    }

    public record ExportInto(String filePath, String fileType, boolean overwrite) {

    }

}
