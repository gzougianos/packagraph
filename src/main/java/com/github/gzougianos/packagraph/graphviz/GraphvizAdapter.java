package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.PackageNode;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph.analysis.Package;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Link.to;

@Slf4j
class GraphvizAdapter implements GraphLibrary {
    @Override
    public void createGraph(Collection<PackageNode> nodes, PackagraphOptions options) {
        Graph g = Factory.graph("Package Dependencies").directed();

        Map<Package, Node> allNodesForAllPackages = createNodesForAllPackages(nodes, options);

        for (Map.Entry<Package, Node> entry : allNodesForAllPackages.entrySet()) {
            Node node = entry.getValue();
            g = g.with(node);
        }

        for (var node : nodes) {
            var graphNode = allNodesForAllPackages.get(node.packag());

            for (var dependency : node.dependencies()) {
                var dependencyNode = allNodesForAllPackages.get(dependency.packag());
                Link edge = to(dependencyNode);
                edge = applyStyle(options, edge);
                g = g.with(graphNode.link(edge));
            }
        }

        try {
            if (fileExistsAndCantOverwrite(options))
                throw new IllegalArgumentException("Output file already exists: " + options.outputFile().getAbsolutePath());


            Graphviz.fromGraph(g)
                    .render(getFormatOutput(options))
                    .toFile(options.outputFile());

            log.info("Generated graph image: {}", options.outputFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to generate graph image", e);
        }
    }

    private Link applyStyle(PackagraphOptions options, Link edge) {
        var style = options.globalEdgeStyle();
        if (style.label() != null) {
            edge = edge.with("label", style.label());
        }
        if (style.color() != null) {
            edge = edge.with("color", style.color());
        }
        if (style.style() != null) {
            edge = edge.with("style", style.style());
        }
        if (style.weight() != null) {
            edge = edge.with("weight", style.weight().toString());
        }
        if (style.penwidth() != null) {
            edge = edge.with("penwidth", style.penwidth().toString());
        }
        if (style.arrowhead() != null) {
            edge = edge.with("arrowhead", style.arrowhead());
        }
        if (style.arrowsize() != null) {
            edge = edge.with("arrowsize", style.arrowsize().toString());
        }
        if (style.dir() != null) {
            edge = edge.with("dir", style.dir());
        }
        if (style.constraint() != null) {
            edge = edge.with("constraint", style.constraint().toString());
        }
        if (style.fontsize() != null) {
            edge = edge.with("fontsize", style.fontsize().toString());
        }
        if (style.fontcolor() != null) {
            edge = edge.with("fontcolor", style.fontcolor());
        }
        if (style.decorate() != null) {
            edge = edge.with("decorate", style.decorate().toString());
        }
        if (style.url() != null) {
            edge = edge.with("URL", style.url());
        }

        return edge;
    }

    private static boolean fileExistsAndCantOverwrite(PackagraphOptions options) {
        return options.outputFile().exists() && !options.allowsOverwriteImageOutput();
    }

    private static Format getFormatOutput(PackagraphOptions options) {
        var fileSuffix = options.outputFileType();
        for (Format format : Format.values()) {
            if (format.name().equalsIgnoreCase(fileSuffix)) {
                return format;
            }
        }

        var supportedFormats = Arrays.stream(Format.values())
                .map(Format::name)
                .collect(Collectors.joining(", "));
        throw new IllegalArgumentException("Unknown output file type: " + fileSuffix + ". Supported formats: " + supportedFormats);
    }

    private Map<Package, Node> createNodesForAllPackages(Collection<PackageNode> nodes, PackagraphOptions options) {
        Set<Package> allPackages = nodes.stream()
                .map(PackageNode::packag)
                .collect(Collectors.toSet());

        Set<Package> dependencies = nodes.stream()
                .flatMap(packageNode -> packageNode.dependencies().stream())
                .map(PackageNode::packag)
                .collect(Collectors.toSet());

        allPackages.addAll(dependencies);

        return allPackages.stream()
                .map(packag -> {
                    Node node = createNode(packag, options);
                    return new AbstractMap.SimpleEntry<>(packag, node);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Node createNode(Package packag, PackagraphOptions options) {
        var style = options.styleOf(packag);
        var node = Factory.node(packag.name());
        node = applyAttributeIfNotNull(style::shape, "shape", node);
        node = applyAttributeIfNotNull(style::style, "style", node);
        node = applyAttributeIfNotNull(style::fillcolor, "fillcolor", node);
        node = applyAttributeIfNotNull(style::color, "color", node);
        node = applyAttributeIfNotNull(style::fontcolor, "fontcolor", node);
        node = applyAttributeIfNotNull(style::fontsize, "fontsize", node);
        node = applyAttributeIfNotNull(style::fontname, "fontname", node);
        node = applyAttributeIfNotNull(style::width, "width", node);
        node = applyAttributeIfNotNull(style::height, "height", node);
        node = applyAttributeIfNotNull(style::fixedsize, "fixedsize", node);
        node = applyAttributeIfNotNull(style::tooltip, "tooltip", node);
        node = applyAttributeIfNotNull(style::url, "url", node);
        node = applyAttributeIfNotNull(style::target, "target", node);
        node = applyAttributeIfNotNull(style::layer, "layer", node);
        node = applyAttributeIfNotNull(style::group, "group", node);
        node = applyAttributeIfNotNull(style::rank, "rank", node);
        node = applyAttributeIfNotNull(style::sides, "sides", node);
        node = applyAttributeIfNotNull(style::peripheries, "peripheries", node);
        return node;
    }

    private static Node applyAttributeIfNotNull(Supplier<Object> valueGetter, String attrName, Node node) {
        var value = valueGetter.get();
        if (value != null) {
            return node.with(attrName, value);
        }
        return node;
    }
}
