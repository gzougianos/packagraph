package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.style.EdgeStyle;
import com.github.gzougianos.packagraph.style.GraphStyle;
import com.github.gzougianos.packagraph.style.NodeStyle;
import guru.nidi.graphviz.attribute.Attributed;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableAttributed;
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

    static Link applyEdgeInStyle(EdgeStyle edgeInStyle, Link edge) {
        edge = applyAttributeIfNotNull(edgeInStyle.label(), "label", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.color(), "color", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.style(), "style", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.weight(), "weight", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.penwidth(), "penwidth", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.arrowhead(), "arrowhead", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.arrowsize(), "arrowsize", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.dir(), "dir", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.constraint(), "constraint", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.fontsize(), "fontsize", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.fontcolor(), "fontcolor", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.decorate(), "decorate", edge);
        edge = applyAttributeIfNotNull(edgeInStyle.url(), "url", edge);
        return edge;
    }

    @SuppressWarnings("unchecked")
    private static <T> T applyAttributeIfNotNull(Object value, String attrName, Attributed<T, ?> node) {
        if (isNullValue(value))
            return (T) node;//as is, nothing changes

        return node.with(attrName, value);
    }

    private static void applyAttributeIfNotNull(Object value, String attrName,
                                                MutableAttributed<MutableGraph, ?> node) {
        if (isNullValue(value))
            return;//as is, nothing changes

        node.add(attrName, value);
    }

    private static boolean isNullValue(Object value) {
        return value == null || "null".equalsIgnoreCase(String.valueOf(value));
    }
}
