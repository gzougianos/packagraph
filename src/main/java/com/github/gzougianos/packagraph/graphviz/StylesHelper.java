package com.github.gzougianos.packagraph.graphviz;

import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;

import java.util.Map;

final class StylesHelper {
    private StylesHelper() {
    }

    static Node applyNodeStyle(Node node, Map<String, String> style) {
        for (var entry : style.entrySet()) {
            var value = entry.getValue();
            if (isNullValue(value)) {
                value = null;
            }
            node = node.with(entry.getKey(), value);
        }
        return node;
    }

    static void applyGraphStyle(MutableGraph graph, Map<String, String> styleAttributes) {
        for (Map.Entry<String, String> entry : styleAttributes.entrySet()) {
            graph.graphAttrs().add(entry.getKey(), entry.getValue());
        }
    }

    static Link applyEdgeInStyle(Map<String, String> edgeInStyle, Link edge) {
        for (var entry : edgeInStyle.entrySet()) {
            var value = entry.getValue();
            if (isNullValue(value)) {
                value = null;
            }
            edge = edge.with(entry.getKey(), value);
        }
        return edge;
    }

    private static boolean isNullValue(Object value) {
        return value == null || "null".equalsIgnoreCase(String.valueOf(value));
    }
}
