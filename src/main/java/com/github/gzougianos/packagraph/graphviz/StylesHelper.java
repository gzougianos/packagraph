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

    static Node applyNodeStyle(Node node, NodeStyle style) {
        node = applyAttributeIfNotNull(style.shape(), "shape", node);
        node = applyAttributeIfNotNull(style.style(), "style", node);
        node = applyAttributeIfNotNull(style.fillcolor(), "fillcolor", node);
        node = applyAttributeIfNotNull(style.color(), "color", node);
        node = applyAttributeIfNotNull(style.fontcolor(), "fontcolor", node);
        node = applyAttributeIfNotNull(style.fontsize(), "fontsize", node);
        node = applyAttributeIfNotNull(style.fontname(), "fontname", node);
        node = applyAttributeIfNotNull(style.width(), "width", node);
        node = applyAttributeIfNotNull(style.height(), "height", node);
        node = applyAttributeIfNotNull(style.fixedsize(), "fixedsize", node);
        node = applyAttributeIfNotNull(style.tooltip(), "tooltip", node);
        node = applyAttributeIfNotNull(style.url(), "url", node);
        node = applyAttributeIfNotNull(style.target(), "target", node);
        node = applyAttributeIfNotNull(style.layer(), "layer", node);
        node = applyAttributeIfNotNull(style.group(), "group", node);
        node = applyAttributeIfNotNull(style.rank(), "rank", node);
        node = applyAttributeIfNotNull(style.sides(), "sides", node);
        node = applyAttributeIfNotNull(style.peripheries(), "peripheries", node);
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
