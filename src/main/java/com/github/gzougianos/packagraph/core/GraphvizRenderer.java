package com.github.gzougianos.packagraph.core;

import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@Slf4j
public record GraphvizRenderer(Packagraph graph) {
    static {
        GraphvizV8Engine engine = new GraphvizV8Engine();
        Graphviz.useEngine(engine);
    }

    public File render() {
        final File destinationFile = new File(graph().options().exportInto().filePath());
        if (destinationFile.exists() && !options().exportInto().overwrite()) {
            throw new IllegalStateException("File already exists: " + destinationFile.getAbsolutePath());
        }

        final MutableGraph mainGraph = Factory.graph("Package Dependencies").directed().toMutable();

        Map<Node, guru.nidi.graphviz.model.Node> graphvizNodes = new HashMap<>();
        for (var node : graph.nodes()) {
            if (node.isExternal() && options().excludeExternals())
                continue;

            guru.nidi.graphviz.model.Node graphvizNode = createNode(node);
            if (graphvizNode == null) {
                continue;
            }

            if (!containsNode(mainGraph, graphvizNode))
                mainGraph.add(graphvizNode);

            graphvizNodes.put(node, graphvizNode);
        }

        for (var edge : graph.edges()) {
            var from = graphvizNodes.get(edge.from());
            var to = graphvizNodes.get(edge.to());

            if (from != null) {
                mainGraph.add(applyNodeStyle(from, options().styleOfFromNode(edge)));
            }

            if (to != null) {
                mainGraph.add(applyNodeStyle(to, options().styleOfToNode(edge)));
            }

            if (from != null && to != null && !Objects.equals(from, to)) {
                mainGraph.add(from.link(createEdge(edge, from, to)));
            }
        }

        applyMainGraphStyle(mainGraph);

        writeGraphToFile(mainGraph, destinationFile);

        if (!options().hasAtLeastOneLegend()) {
            return destinationFile;
        }

        if (!isSvgFormat()) {
            log.warn("Legends are not supported for SVG format.");
            return destinationFile;
        }

        try {
            return new LegendRenderer(graph).embedLegendsInto(mainGraph, destinationFile);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not embed legend graph into main graph.", e);
        }
    }

    private boolean isSvgFormat() {
        return graphvizFormat() == Format.SVG;
    }

    private File writeGraphToFile(MutableGraph graph, File destinationFile) {
        try {
            return Graphviz.fromGraph(graph)
                    .render(graphvizFormat())
                    .toFile(destinationFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Link createEdge(Edge edge, guru.nidi.graphviz.model.Node fromNode, guru.nidi.graphviz.model.Node toNode) {
        var graphvizEdge = Link.to(toNode);

        var style = options().styleOf(edge);
        for (var entry : style.entrySet()) {
            graphvizEdge = graphvizEdge.with(entry.getKey(), entry.getValue());
        }
        if (!style.containsKey("tooltip")) {
            graphvizEdge = graphvizEdge.with("tooltip", fromNode.name() + " -> " + toNode.name());
        }
        return graphvizEdge;
    }

    private boolean containsNode(MutableGraph mainGraph, guru.nidi.graphviz.model.Node node) {
        return mainGraph.nodes().stream()
                .anyMatch(n -> n.name().equals(node.name()));
    }

    private Format graphvizFormat() {
        var fileSuffix = graph().options().exportInto().fileType();
        fileSuffix = fileSuffix.replace(".", ""); //In case of .png

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


    private void applyMainGraphStyle(MutableGraph mainGraph) {
        for (Map.Entry<String, String> entry : options().mainGraphStyleAttributes().entrySet()) {
            mainGraph.graphAttrs().add(entry.getKey(), entry.getValue());
        }
    }

    private guru.nidi.graphviz.model.Node createNode(Node node) {
        String name = options().nameOf(node);
        if (isBlankOrNull(name))
            return null;

        var gNode = Factory.node(name);

        var style = options().styleOf(node);
        return applyNodeStyle(gNode, style);
    }

    private guru.nidi.graphviz.model.Node applyNodeStyle(guru.nidi.graphviz.model.Node node, Map<String, String> style) {
        for (var entry : style.entrySet()) {
            var value = entry.getValue();
            if (isBlankOrNull(value)) {
                value = null;
            } else if ("label".equalsIgnoreCase(entry.getKey())) {
                value = node.name().value() + " " + value;
            }
            node = node.with(entry.getKey(), value);
        }
        return node;
    }

    private boolean isBlankOrNull(String name) {
        return name == null || name.trim().isEmpty();
    }

    private Options options() {
        return graph().options();
    }

}
