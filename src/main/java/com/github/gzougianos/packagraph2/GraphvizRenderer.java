package com.github.gzougianos.packagraph2;

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
        final MutableGraph mainGraph = Factory.graph("Package Dependencies").directed().toMutable();

        Map<Node, guru.nidi.graphviz.model.Node> graphvizNodes = new HashMap<>();
        for (var node : graph.nodes()) {
            if (node.isExternal() && options().excludeExternals())
                continue;

            guru.nidi.graphviz.model.Node graphvizNode = createNode(node);
            if (!mainGraph.nodes().contains(graphvizNode))
                mainGraph.add(graphvizNode);

            graphvizNodes.put(node, graphvizNode);
        }

        for (var edge : graph.edges()) {
            var from = graphvizNodes.get(edge.from());
            var to = graphvizNodes.get(edge.to());
            if (from == null || to == null || Objects.equals(from, to)) {
                continue;
            }

            var graphvizEdge = from.link(Link.to(to));
            mainGraph.add(graphvizEdge);
        }

        applyMainGraphStyle(mainGraph);
        try {
            return Graphviz.fromGraph(mainGraph)
                    .render(graphvizFormat())
                    .toFile(new File(graph().options().exportInto().filePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        var gNode = Factory.node(options().nameOf(node));

        var style = options().styleOf(node);
        for (var entry : style.entrySet()) {
            gNode = gNode.with(entry.getKey(), entry.getValue());
        }
        return gNode;
    }

    private Options options() {
        return graph().options();
    }

}
