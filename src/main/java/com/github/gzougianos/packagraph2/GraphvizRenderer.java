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
            mainGraph.add(graphvizNode);
            graphvizNodes.put(node, graphvizNode);
        }

        for (var edge : graph.edges()) {
            var from = graphvizNodes.get(edge.from());
            var to = graphvizNodes.get(edge.to());
            if (from == null || to == null) {
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
        String mainGraphStyleName = graph.options().mainGraphStyle();
        if (mainGraphStyleName == null)
            return;

        graph.options().findStyle(mainGraphStyleName)
                .map(style -> new Style(style, graph.options().defineConstant()))
                .ifPresentOrElse(style -> {
                    for (Map.Entry<String, String> entry : style.values().entrySet()) {
                        mainGraph.graphAttrs().add(entry.getKey(), entry.getValue());
                    }
                }, () -> log.warn("Main graph style {} is not defined.", mainGraphStyleName));
    }

    private guru.nidi.graphviz.model.Node createNode(Node node) {
        var gNode = Factory.node(node.packag().name());

        return gNode;
    }

    private Options options() {
        return graph().options();
    }

}
