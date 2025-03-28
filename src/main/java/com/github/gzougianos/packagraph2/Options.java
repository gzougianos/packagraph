package com.github.gzougianos.packagraph2;

import lombok.*;

import java.util.*;

@Builder
public record Options(List<String> sourceDirectories, boolean excludeExternals,
                      List<ShowNodes> showNodes, List<ShowEdges> showEdges, List<DefineStyle> defineStyles,
                      List<DefineConstant> defineConstant, String mainGraphStyle) {

    public record ShowNodes(String packag, String as, String style) {

    }

    public record ShowEdges(String packageFrom, String packageTo, String style, String fromNodeStyle,
                            String toNodeStyle) {

    }

    public record DefineStyle(String name, String value) {

    }

    public record DefineConstant(String name, String value) {

    }

}
