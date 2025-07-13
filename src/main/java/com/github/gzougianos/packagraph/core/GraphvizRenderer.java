package com.github.gzougianos.packagraph.core;

import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

@Slf4j
public record GraphvizRenderer(Packagraph graph) {

    //In style, if you do label=_%%label%%_ and the label was "abc", the label will be "_abc_"
    public static final Pattern STYLE_VALUE_PLACE_HOLDER_PATTERN = Pattern.compile("\\%\\%(\\w+)\\%\\%");

    static {
        GraphvizV8Engine engine = new GraphvizV8Engine();
        Graphviz.useEngine(engine);
    }

    public File render() {
        final File destinationFile = new File(graph().options().exportInto().filePath());
        if (destinationFile.exists() && !options().exportInto().overwrite()) {
            throw new IllegalStateException("Output file already exists: " + destinationFile.getAbsolutePath());
        }

        final MutableGraph mainGraph = Factory.graph("Package Dependencies").directed().toMutable();

        Map<Node, MutableNode> graphvizNodes = new TreeMap<>();
        for (var node : graph.nodes()) {
            if (node.isExternal() && options().excludeExternals())
                continue;

            MutableNode graphvizNode = createNode(node);
            if (graphvizNode == null) {
                continue;
            }

            if (!containsNode(mainGraph, graphvizNode))
                mainGraph.add(graphvizNode);

            graphvizNodes.put(node, graphvizNode);
        }
        var fromAppliedStyles = new HashMap<String, Set<Integer>>();
        var toAppliedStyles = new HashMap<String, Set<Integer>>();

        for (var edge : graph.edges()) {
            MutableNode from = graphvizNodes.get(edge.from());
            MutableNode to = graphvizNodes.get(edge.to());

            if (from != null) {
                String fromName = String.valueOf(from.name());
                fromAppliedStyles.putIfAbsent(fromName, new HashSet<>());

                var style = options().styleOfFromNode(edge);
                if (!fromAppliedStyles.get(fromName).contains(style.hashCode())) {
                    applyNodeStyle(from, options().styleOfFromNode(edge));
                    fromAppliedStyles.get(fromName).add(style.hashCode());
                }
            }

            if (to != null) {
                String toName = String.valueOf(to.name());
                toAppliedStyles.putIfAbsent(toName, new HashSet<>());

                var style = options().styleOfToNode(edge);
                if (!toAppliedStyles.get(toName).contains(style.hashCode())) {
                    applyNodeStyle(to, options().styleOfToNode(edge));
                    toAppliedStyles.get(toName).add(style.hashCode());
                }
            }

            if (from != null && to != null && !Objects.equals(from, to) && !Objects.equals(from.name(), to.name())) {
                var alreadyLinked = from.links().stream()
                        .filter(li -> li.to().name().equals(to.name()))
                        .filter(li -> li.from().name().equals(from.name()))
                        .findFirst().isPresent();

                if (!alreadyLinked) {
                    mainGraph.add(from.addLink(createEdge(edge, from, to)));
                }
            }
        }

        applyMainGraphStyle(mainGraph);

        writeGraphToFile(mainGraph, destinationFile);

        if (!options().hasAtLeastOneLegend()) {
            return destinationFile;
        }

        if (!isSvgFormat()) {
            log.warn("Legends are supported only for SVG format.");
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

    private Link createEdge(Edge edge, MutableNode fromNode, MutableNode toNode) {
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

    private boolean containsNode(MutableGraph mainGraph, MutableNode node) {
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

    private MutableNode createNode(Node node) {
        String name = options().nameOf(node);
        if (isBlankOrNull(name))
            return null;

        var gNode = Factory.mutNode(name);
        gNode = gNode.add("label", gNode.name().value());

        var style = options().styleOf(node);
        return applyNodeStyle(gNode, style);
    }

    private MutableNode applyNodeStyle(MutableNode node, Map<String, String> style) {

        for (var entry : style.entrySet()) {
            var value = entry.getValue();
            if (isBlankOrNull(value)) {
                value = null;
            } else {
                value = replaceAttrPlaceholders(value, node);
            }
            node = node.add(entry.getKey(), value);
        }
        return node;
    }

    private String replaceAttrPlaceholders(String value, MutableNode node) {
        if (!value.contains("%")) {
            return value;
        }

        Matcher placeHolderMatcher = STYLE_VALUE_PLACE_HOLDER_PATTERN.matcher(value);

        while (placeHolderMatcher.find()) {
            String attrName = placeHolderMatcher.group(1);
            String attrValue = isBlankOrNull(String.valueOf(node.get(attrName))) ? null : String.valueOf(node.get(attrName));
            if (attrValue == null) {
                continue;
            }
            value = placeHolderMatcher.replaceFirst(attrValue);
            placeHolderMatcher = STYLE_VALUE_PLACE_HOLDER_PATTERN.matcher(value);
        }
        return value;
    }

    private boolean isBlankOrNull(String name) {
        return name == null || name.trim().isEmpty() || "null".equals(name);
    }

    private Options options() {
        return graph().options();
    }

}
