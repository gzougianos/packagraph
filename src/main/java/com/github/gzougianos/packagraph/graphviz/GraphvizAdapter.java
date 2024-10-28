package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.PackageNode;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph.analysis.Package;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Link.to;

@Slf4j
class GraphvizAdapter implements GraphLibrary {
    @Override
    public void createGraph(Collection<PackageNode> nodes, PackagraphOptions options) {
        final MutableGraph mainGraph = Factory.graph("Package Dependencies").directed().toMutable();

        Map<String, Graph> clusterGraphs = createClusterGraphs(nodes, options);

        Map<Package, Node> allNodesForAllPackages = createNodesForAllPackages(nodes, options);

        for (Map.Entry<Package, Node> entry : allNodesForAllPackages.entrySet()) {
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
                var edge = StylesHelper.applyEdgeInStyle(style, to(dependencyNode));
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

    private static PackageNode findNodeOfPackage(Collection<PackageNode> nodes, Package packag) {
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
                    .graph("cluster_" + cluster);

            var clusterStyle = options.clusterStyleOf(cluster);
            clusterGraph = StylesHelper.applyClusterStyle(clusterGraph, clusterStyle);
            clusterGraphs.put(cluster, clusterGraph);
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
                    node = StylesHelper.applyNodeStyle(node, options.styleOf(packag));
                    return new AbstractMap.SimpleEntry<>(packag, node);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
