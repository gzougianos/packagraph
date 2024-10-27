package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.PackageNode;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph.analysis.Package;
import com.github.gzougianos.packagraph.style.ClusterStyle;
import com.github.gzougianos.packagraph.style.NodeStyle;
import guru.nidi.graphviz.attribute.Attributed;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
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
        Map<String, Graph> clusterGraphs = createClusterGraphs(nodes, options);

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

    private Map<String, Graph> createClusterGraphs(Collection<PackageNode> nodes, PackagraphOptions options) {
        Map<String, Graph> clusterGraphs = new HashMap<>();

        for (var cluster : findAllClusters(nodes)) {
            //All cluster graphs must start with "cluster_"
            var clusterGraph = Factory
                    .graph("cluster_" + cluster);

            var clusterStyle = options.clusterStyleOf(cluster);
            clusterGraph = applyClusterStyle(clusterGraph, clusterStyle);
            clusterGraphs.put(cluster, clusterGraph);
        }
        return clusterGraphs;
    }

    private Graph applyClusterStyle(Graph clusterGraph, ClusterStyle clusterStyle) {
        var mutableGraph = clusterGraph.toMutable();
        applyAttributeIfNotNull(clusterStyle::damping, "Damping", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::k, "K", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::url, "URL", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::background, "background", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::bb, "bb", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::bgcolor, "bgcolor", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::center, "center", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::charset, "charset", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::clusterrank, "clusterrank", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::color, "color", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::colorscheme, "colorscheme", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::comment, "comment", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::compound, "compound", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::concentrate, "concentrate", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::dpi, "dpi", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::epsilon, "epsilon", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::esep, "esep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::fontcolor, "fontcolor", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::fontname, "fontname", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::fontpath, "fontpath", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::fontsize, "fontsize", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::id, "id", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::label, "label", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::labeljust, "labeljust", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::labelloc, "labelloc", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::landscape, "landscape", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::layers, "layers", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::layersep, "layersep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::layout, "layout", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::margin, "margin", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::maxiter, "maxiter", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::mclimit, "mclimit", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::mindist, "mindist", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::mode, "mode", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::model, "model", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::rankdir, "rankdir", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::ranksep, "ranksep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::ratio, "ratio", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::remincross, "remincross", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::rotate, "rotate", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::scale, "scale", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::searchsize, "searchsize", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::sep, "sep", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::splines, "splines", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::stylesheet, "stylesheet", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::target, "target", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::tooltip, "tooltip", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::truecolor, "truecolor", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::viewport, "viewport", mutableGraph.graphAttrs());
        applyAttributeIfNotNull(clusterStyle::xdotversion, "xdotversion", mutableGraph.graphAttrs());
        return mutableGraph.toImmutable();
    }


    private Graph addClusterGraphs(Map<String, Graph> clusterGraphs, Graph mainGraph, PackagraphOptions options) {
        for (var cluster : clusterGraphs.entrySet()) {
            Graph clusterGraph = cluster.getValue();
            mainGraph = mainGraph.with(clusterGraph);
        }
        return mainGraph;
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
                    node = applyNodeStyle(node, options.styleOf(packag));
                    return new AbstractMap.SimpleEntry<>(packag, node);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Node applyNodeStyle(Node node, NodeStyle style) {
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

    private static void applyAttributeIfNotNull(Supplier<Object> valueGetter, String attrName, MutableAttributed<MutableGraph, ?> node) {
        var value = valueGetter.get();
        var isNull = value == null || "null".equalsIgnoreCase(String.valueOf(value));

        if (isNull)
            return;//as is, nothing changes

        node.add(attrName, value);
    }
}
