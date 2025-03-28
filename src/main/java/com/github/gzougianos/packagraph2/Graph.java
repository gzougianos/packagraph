package com.github.gzougianos.packagraph2;

import java.util.*;

public record Graph(Set<Node> nodes, Set<Edge> edges) {

    @Override
    public Set<Node> nodes() {
        return nonEmpty(nodes);
    }

    @Override
    public Set<Edge> edges() {
        return nonEmpty(edges);
    }

    private static <T> List<T> nonEmpty(List<T> list) {
        if (list == null)
            return List.of();
        return list;
    }

    private static <T> Set<T> nonEmpty(Set<T> set) {
        if (set == null)
            return Set.of();
        return set;
    }

}
