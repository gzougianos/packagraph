package com.github.gzougianos.packagraph2;

import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;

@Slf4j
public record GraphvizRenderer(Packagraph graph) {
    static {
        GraphvizV8Engine engine = new GraphvizV8Engine();
        Graphviz.useEngine(engine);
    }

    public File render() {
        final MutableGraph mainGraph = Factory.graph("Package Dependencies").directed().toMutable();

        for (var node : graph.nodes()) {
            mainGraph.add(createNode(node));
        }

        applyMainGraphStyle(mainGraph);
        try {
            return Graphviz.fromGraph(mainGraph)
                    .render(Format.PNG)
                    .toFile(new File("simple_main_graph_style.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private LinkSource createNode(Node node) {
        return Factory.node(node.packag().name());
    }


}
