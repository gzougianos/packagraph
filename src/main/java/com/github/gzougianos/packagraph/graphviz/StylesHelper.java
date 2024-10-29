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

    static void applyGraphStyle(MutableGraph graph, GraphStyle style) {

        applyAttributeIfNotNull(style.damping(), "Damping", graph.graphAttrs());
        applyAttributeIfNotNull(style.k(), "K", graph.graphAttrs());
        applyAttributeIfNotNull(style.url(), "URL", graph.graphAttrs());
        applyAttributeIfNotNull(style.background(), "background", graph.graphAttrs());
        applyAttributeIfNotNull(style.bb(), "bb", graph.graphAttrs());
        applyAttributeIfNotNull(style.bgcolor(), "bgcolor", graph.graphAttrs());
        applyAttributeIfNotNull(style.center(), "center", graph.graphAttrs());
        applyAttributeIfNotNull(style.charset(), "charset", graph.graphAttrs());
        applyAttributeIfNotNull(style.clusterrank(), "clusterrank", graph.graphAttrs());
        applyAttributeIfNotNull(style.color(), "color", graph.graphAttrs());
        applyAttributeIfNotNull(style.colorscheme(), "colorscheme", graph.graphAttrs());
        applyAttributeIfNotNull(style.comment(), "comment", graph.graphAttrs());
        applyAttributeIfNotNull(style.compound(), "compound", graph.graphAttrs());
        applyAttributeIfNotNull(style.concentrate(), "concentrate", graph.graphAttrs());
        applyAttributeIfNotNull(style.dpi(), "dpi", graph.graphAttrs());
        applyAttributeIfNotNull(style.epsilon(), "epsilon", graph.graphAttrs());
        applyAttributeIfNotNull(style.esep(), "esep", graph.graphAttrs());
        applyAttributeIfNotNull(style.fontcolor(), "fontcolor", graph.graphAttrs());
        applyAttributeIfNotNull(style.fontname(), "fontname", graph.graphAttrs());
        applyAttributeIfNotNull(style.fontpath(), "fontpath", graph.graphAttrs());
        applyAttributeIfNotNull(style.fontsize(), "fontsize", graph.graphAttrs());
        applyAttributeIfNotNull(style.id(), "id", graph.graphAttrs());
        applyAttributeIfNotNull(style.label(), "label", graph.graphAttrs());
        applyAttributeIfNotNull(style.labeljust(), "labeljust", graph.graphAttrs());
        applyAttributeIfNotNull(style.labelloc(), "labelloc", graph.graphAttrs());
        applyAttributeIfNotNull(style.landscape(), "landscape", graph.graphAttrs());
        applyAttributeIfNotNull(style.layers(), "layers", graph.graphAttrs());
        applyAttributeIfNotNull(style.layersep(), "layersep", graph.graphAttrs());
        applyAttributeIfNotNull(style.layout(), "layout", graph.graphAttrs());
        applyAttributeIfNotNull(style.margin(), "margin", graph.graphAttrs());
        applyAttributeIfNotNull(style.maxiter(), "maxiter", graph.graphAttrs());
        applyAttributeIfNotNull(style.mclimit(), "mclimit", graph.graphAttrs());
        applyAttributeIfNotNull(style.mindist(), "mindist", graph.graphAttrs());
        applyAttributeIfNotNull(style.mode(), "mode", graph.graphAttrs());
        applyAttributeIfNotNull(style.model(), "model", graph.graphAttrs());
        applyAttributeIfNotNull(style.rankdir(), "rankdir", graph.graphAttrs());
        applyAttributeIfNotNull(style.ranksep(), "ranksep", graph.graphAttrs());
        applyAttributeIfNotNull(style.ratio(), "ratio", graph.graphAttrs());
        applyAttributeIfNotNull(style.remincross(), "remincross", graph.graphAttrs());
        applyAttributeIfNotNull(style.rotate(), "rotate", graph.graphAttrs());
        applyAttributeIfNotNull(style.scale(), "scale", graph.graphAttrs());
        applyAttributeIfNotNull(style.searchsize(), "searchsize", graph.graphAttrs());
        applyAttributeIfNotNull(style.sep(), "sep", graph.graphAttrs());
        applyAttributeIfNotNull(style.splines(), "splines", graph.graphAttrs());
        applyAttributeIfNotNull(style.stylesheet(), "stylesheet", graph.graphAttrs());
        applyAttributeIfNotNull(style.target(), "target", graph.graphAttrs());
        applyAttributeIfNotNull(style.tooltip(), "tooltip", graph.graphAttrs());
        applyAttributeIfNotNull(style.truecolor(), "truecolor", graph.graphAttrs());
        applyAttributeIfNotNull(style.viewport(), "viewport", graph.graphAttrs());
        applyAttributeIfNotNull(style.xdotversion(), "xdotversion", graph.graphAttrs());
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
