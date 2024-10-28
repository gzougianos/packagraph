package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.style.ClusterStyle;
import com.github.gzougianos.packagraph.style.EdgeStyle;
import com.github.gzougianos.packagraph.style.NodeStyle;
import guru.nidi.graphviz.attribute.Attributed;
import guru.nidi.graphviz.model.*;

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

    static Graph applyClusterStyle(Graph clusterGraph, ClusterStyle clusterStyle) {
        var mutableGraph = clusterGraph.toMutable();

        applyAttributeIfNotNull(clusterStyle.damping(), "Damping", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.k(), "K", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.url(), "URL", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.background(), "background", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.bb(), "bb", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.bgcolor(), "bgcolor", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.center(), "center", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.charset(), "charset", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.clusterrank(), "clusterrank", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.color(), "color", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.colorscheme(), "colorscheme", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.comment(), "comment", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.compound(), "compound", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.concentrate(), "concentrate", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.dpi(), "dpi", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.epsilon(), "epsilon", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.esep(), "esep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.fontcolor(), "fontcolor", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.fontname(), "fontname", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.fontpath(), "fontpath", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.fontsize(), "fontsize", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.id(), "id", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.label(), "label", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.labeljust(), "labeljust", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.labelloc(), "labelloc", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.landscape(), "landscape", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.layers(), "layers", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.layersep(), "layersep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.layout(), "layout", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.margin(), "margin", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.maxiter(), "maxiter", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.mclimit(), "mclimit", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.mindist(), "mindist", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.mode(), "mode", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.model(), "model", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.rankdir(), "rankdir", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.ranksep(), "ranksep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.ratio(), "ratio", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.remincross(), "remincross", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.rotate(), "rotate", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.scale(), "scale", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.searchsize(), "searchsize", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.sep(), "sep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.splines(), "splines", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.stylesheet(), "stylesheet", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.target(), "target", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.tooltip(), "tooltip", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.truecolor(), "truecolor", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.viewport(), "viewport", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle.xdotversion(), "xdotversion", mutableGraph.graphAttrs());

        return mutableGraph.toImmutable();
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
