package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.PackageNode;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph2.analysis.PackageName;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizV8Engine;
import guru.nidi.graphviz.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Link.to;

@Slf4j
class GraphvizAdapter implements GraphLibrary {
    static {
        GraphvizV8Engine engine = new GraphvizV8Engine();
        Graphviz.useEngine(engine);
    }

    @Override
    public void createGraph(Collection<PackageNode> nodes, PackagraphOptions options) {
        final MutableGraph mainGraph = Factory.graph("Package Dependencies").directed().toMutable();
        //Unfortunately, main graph style is applied also to Cluster styles
        //ClusterStyles have to be explicitly defined in order to override main graph's properties
        applyGraphStyle(mainGraph, options.mainGraphStyle());


        Map<String, Graph> clusterGraphs = createClusterGraphs(nodes, options);

        Map<PackageName, Node> allNodesForAllPackages = createNodesForAllPackages(nodes, options);

        for (Map.Entry<PackageName, Node> entry : allNodesForAllPackages.entrySet()) {
            var packag = entry.getKey();
            PackageNode packageNode = findNodeOfPackage(nodes, packag);

            String clusterName = packageNode.cluster().orElse(null);
            boolean belongsToCluster = clusterName != null;
            if (belongsToCluster) {
                Graph clusterGraph = clusterGraphs.get(clusterName);
                clusterGraphs.put(clusterName, clusterGraph.with(entry.getValue()));
            } else {
                mainGraph.add(entry.getValue());
            }
        }

        addAllClusterGraphs(mainGraph, clusterGraphs);

        for (var node : nodes) {
            var graphNode = allNodesForAllPackages.get(node.packag());

            for (var dependency : node.dependencies()) {
                var dependencyNode = allNodesForAllPackages.get(dependency.packag());

                var style = options.edgeInStyleOf(dependency.packag());
                var edge = applyEdgeInStyle(style, graphNode, to(dependencyNode));
                mainGraph.add(graphNode.link(edge));
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

    private void addAllClusterGraphs(MutableGraph mainGraph, Map<String, Graph> clusterGraphs) {
        for (var cluster : clusterGraphs.entrySet()) {
            mainGraph.add(cluster.getValue());
        }
    }

    private static PackageNode findNodeOfPackage(Collection<PackageNode> nodes, PackageName packag) {
        return nodes.stream()
                .filter(p -> p.packag().equals(packag))
                .findFirst()
                .orElseThrow();
    }

    private Map<String, Graph> createClusterGraphs(Collection<PackageNode> nodes, PackagraphOptions options) {
        Map<String, Graph> clusterGraphs = new HashMap<>();

        for (var cluster : findAllClusters(nodes)) {
            //All cluster graphs must start with "cluster_"
            var clusterGraph = Factory
                    .graph("cluster_" + cluster).toMutable();

            var clusterStyle = options.clusterStyleOf(cluster);
            applyGraphStyle(clusterGraph, clusterStyle);
            clusterGraphs.put(cluster, clusterGraph.toImmutable());
        }
        return clusterGraphs;
    }

    private Set<String> findAllClusters(Collection<PackageNode> nodes) {
        return nodes.stream()
                .map(PackageNode::cluster)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private static boolean fileExistsAndCantOverwrite(PackagraphOptions options) {
        return options.outputFile().exists() && !options.allowsOverwriteOutput();
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

    private Map<PackageName, Node> createNodesForAllPackages(Collection<PackageNode> nodes, PackagraphOptions options) {
        Set<PackageName> allPackages = nodes.stream()
                .map(PackageNode::packag)
                .collect(Collectors.toSet());

        Set<PackageName> dependencies = nodes.stream()
                .flatMap(packageNode -> packageNode.dependencies().stream())
                .map(PackageNode::packag)
                .collect(Collectors.toSet());

        allPackages.addAll(dependencies);

        return allPackages.stream()
                .map(packag -> {
                    var node = Factory.node(packag.name());
                    node = applyNodeStyle(node, options.nodeStyleOf(packag));
                    return new AbstractMap.SimpleEntry<>(packag, node);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private static Node applyNodeStyle(Node node, Map<String, String> style) {
        for (var entry : style.entrySet()) {
            var value = String.valueOf(entry.getValue());
            if (isNullOrStringNull(value)) {
                value = null;
            }
            node = node.with(entry.getKey(), value);
        }
        return node;
    }

    private static void applyGraphStyle(MutableGraph graph, Map<String, String> styleAttributes) {
        for (Map.Entry<String, String> entry : styleAttributes.entrySet()) {
            graph.graphAttrs().add(entry.getKey(), entry.getValue());
        }
    }

    private static Link applyEdgeInStyle(Map<String, String> edgeInStyle, Node fromNode, Link edge) {
        for (var entry : edgeInStyle.entrySet()) {
            var value = entry.getValue();
            if (isNullOrStringNull(value)) {
                value = null;
            }
            edge = edge.with(entry.getKey(), value);
        }
        if (!edgeInStyle.containsKey("tooltip")) {
            edge = edge.with("tooltip", fromNode.name() + " -> " + edge.to().name());
        }
        return edge;
    }

    private static boolean isNullOrStringNull(Object value) {
        return value == null || "null".equalsIgnoreCase(String.valueOf(value));
    }
}
