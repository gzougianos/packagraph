package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.PackageNode;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph.analysis.Package;
import guru.nidi.graphviz.attribute.*;
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
        Graph mainGraph = Factory.graph("Package Dependencies").directed();
        Map<String, Graph> clusterGraphs = createClusterGraphs(nodes);

        Map<Package, Node> allNodesForAllPackages = createNodesForAllPackages(nodes, options);

        for (Map.Entry<Package, Node> entry : allNodesForAllPackages.entrySet()) {
            PackageNode packageNode = nodes.stream()
                    .filter(p -> p.packag().equals(entry.getKey()))
                    .findFirst()
                    .orElseThrow();

            String clusterName = packageNode.cluster().orElse(null);
            boolean belongsToCluster = clusterName != null;
            if (belongsToCluster) {
                Graph clusterGraph = clusterGraphs.get(clusterName);
                clusterGraphs.put(clusterName, clusterGraph.with(entry.getValue()));
            } else {
                mainGraph = mainGraph.with(entry.getValue());
            }
        }

        mainGraph = addClusterGraphs(clusterGraphs, mainGraph, options);

        for (var node : nodes) {
            var graphNode = allNodesForAllPackages.get(node.packag());

            for (var dependency : node.dependencies()) {
                var dependencyNode = allNodesForAllPackages.get(dependency.packag());

                var edge = applyEdgeInStyle(options, to(dependencyNode), dependency.packag());
                mainGraph = mainGraph.with(graphNode.link(edge));
            }
        }


        try {
            if (fileExistsAndCantOverwrite(options))
                throw new IllegalArgumentException("Output file already exists: " + options.outputFile().getAbsolutePath());


            Graphviz.fromGraph(mainGraph)
                    .render(getFormatOutput(options))
                    .toFile(options.outputFile());

            log.info("Generated graph image: {}", options.outputFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to generate graph image", e);
        }
    }

    private Map<String, Graph> createClusterGraphs(Collection<PackageNode> nodes) {
        Map<String, Graph> clusterGraphs = new HashMap<>();

        for (var cluster : findAllClusters(nodes)) {
            //All cluster graphs must start with "cluster_"
            clusterGraphs.put(cluster, Factory.graph("cluster_" + cluster).directed());
        }
        return clusterGraphs;
    }

    private Graph addClusterGraphs(Map<String, Graph> clusterGraphs, Graph mainGraph, PackagraphOptions options) {
        for (var cluster : clusterGraphs.entrySet()) {
            Graph clusterGraph = cluster.getValue();
            String clusterName = cluster.getKey();

            clusterGraph = clusterGraph.with(createLegendForClusterGraph(clusterName, options));
            mainGraph = mainGraph.with(clusterGraph);
        }
        return mainGraph;
    }

    private Graph createLegendForClusterGraph(String legend, PackagraphOptions options) {
        var node = Factory.node("Legend");
        node = applyNodeStyle(node, options);
        node = node.with(Label.of(legend), Shape.NONE);
        return Factory.graph("legend_" + legend).with(node);
    }

    private Set<String> findAllClusters(Collection<PackageNode> nodes) {
        return nodes.stream()
                .map(PackageNode::cluster)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Link applyEdgeInStyle(PackagraphOptions options, Link edge, Package to) {
        var style = options.edgeInStyleOf(to);
        edge = applyAttributeIfNotNull(style::label, "label", edge);
        edge = applyAttributeIfNotNull(style::color, "color", edge);
        edge = applyAttributeIfNotNull(style::style, "style", edge);
        edge = applyAttributeIfNotNull(style::weight, "weight", edge);
        edge = applyAttributeIfNotNull(style::penwidth, "penwidth", edge);
        edge = applyAttributeIfNotNull(style::arrowhead, "arrowhead", edge);
        edge = applyAttributeIfNotNull(style::arrowsize, "arrowsize", edge);
        edge = applyAttributeIfNotNull(style::dir, "dir", edge);
        edge = applyAttributeIfNotNull(style::constraint, "constraint", edge);
        edge = applyAttributeIfNotNull(style::fontsize, "fontsize", edge);
        edge = applyAttributeIfNotNull(style::fontcolor, "fontcolor", edge);
        edge = applyAttributeIfNotNull(style::decorate, "decorate", edge);
        edge = applyAttributeIfNotNull(style::url, "url", edge);

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
                    var node = Factory.node(packag.name());
                    node = applyNodeStyle(node, options);
                    return new AbstractMap.SimpleEntry<>(packag, node);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Node applyNodeStyle(Node node, PackagraphOptions options) {
        var style = options.globalStyle();
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

    @SuppressWarnings("unchecked")
    private static <T> T applyAttributeIfNotNull(Supplier<Object> valueGetter, String attrName, Attributed<T, ?> node) {
        var value = valueGetter.get();
        var isNull = value == null || "null".equalsIgnoreCase(String.valueOf(value));

        if (isNull)
            return (T) node;//as is, nothing changes

        return node.with(attrName, value);
    }
}
