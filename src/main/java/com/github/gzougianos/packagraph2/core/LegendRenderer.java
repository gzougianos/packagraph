package com.github.gzougianos.packagraph2.core;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.model.Node;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.*;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.model.*;

import static guru.nidi.graphviz.model.Factory.node;

record LegendRenderer(Packagraph graph) {

    File embedLegendsInto(final MutableGraph mainGraph, final File destinationFile) throws IOException {
        if (!options().hasAtLeastOneLegend())
            throw new IllegalStateException("No legends to embed.");

        var tempImageWithLegendGraph = createLegendGraphToTempPng();

        mainGraph.add(imageNode(tempImageWithLegendGraph));

        Graphviz.fromGraph(mainGraph)
                .render(Format.SVG)
                .toFile(destinationFile);

        replaceImagePathWithBase64(destinationFile, tempImageWithLegendGraph);
        return destinationFile;

    }

    private LinkSource imageNode(File tempImageWithLegendGraph) {
        return node("")
                .with(Shape.RECTANGLE, Color.LIGHTGRAY, Image.of(tempImageWithLegendGraph.toPath().toAbsolutePath().toString()));
    }

    private File createLegendGraphToTempPng() throws IOException {
        var nodeLegends = options().nodeLegends();
        var edgeLegends = options().edgeLegends(); //not supported for now, its super tricky

        MutableGraph graph = Factory.graph("legends").toMutable();

        Grid grid = calculateGrid(nodeLegends.size());
        int rank = Math.min(grid.height, grid.width);
        Node previousNode = null;
        ArrayList<Legend> legends = new ArrayList<>(nodeLegends.values());
        for (int i = 0; i < legends.size(); i++) {
            var legendNode = createLegendNode(legends.get(i));
            if (previousNode == null) {
                graph.add(legendNode);
                previousNode = legendNode;
            } else {
                var edge = previousNode.link(invisibleEdgeTo(legendNode));
                previousNode = legendNode;
                graph.add(edge);
            }
            if ((i + 1) % rank == 0) {
                previousNode = null;
            }
        }

        applyGraphStyle(options().legendGraphStyleAttributes(), graph);

        File destinationFile = Files.createTempFile("legend_graph", ".png").toFile();
        destinationFile.deleteOnExit();
        Graphviz.fromGraph(graph)
                .render(Format.PNG)
                .toFile(destinationFile);

        return destinationFile;
    }

    private void applyGraphStyle(Map<String, String> legendGraphStyleAttributes, MutableGraph graph) {
        for (Map.Entry<String, String> entry : legendGraphStyleAttributes.entrySet()) {
            graph.graphAttrs().add(entry.getKey(), entry.getValue());
        }
    }

    private static Link invisibleEdgeTo(Node legendNode) {
        return to(legendNode).with("style", "invisible");
    }

    public record Grid(int width, int height) {
    }

    public static Grid calculateGrid(int nodeCount) {
        int width = (int) Math.ceil(Math.sqrt(nodeCount));
        int height = (int) Math.ceil((double) nodeCount / width);
        return new Grid(width, height);
    }


    private guru.nidi.graphviz.model.Node createLegendNode(Legend legend) {
        var gNode = Factory.node(legend.name());
        for (var entry : legend.style().entrySet()) {
            var value = entry.getValue();
            if ("label".equals(entry.getKey())) {
                value = legend.name() + " " + value;
            }
            gNode = gNode.with(entry.getKey(), value);
        }
        return gNode;
    }

    private Options options() {
        return graph.options();
    }

    private void replaceImagePathWithBase64(File mainGraphFile, File legendGraphImageFile) throws IOException {
        final Path temp = Files.createTempFile("temp_graph", ".svg");
        try (BufferedReader reader = Files.newBufferedReader(mainGraphFile.toPath());
             BufferedWriter writer = Files.newBufferedWriter(temp)) {

            String line;


            while ((line = reader.readLine()) != null) {
                if (line.contains(legendGraphImageFile.getAbsolutePath())) {
                    line = line.replace(legendGraphImageFile.getAbsolutePath(), toBase64Data(legendGraphImageFile));
                }
                writer.write(line);
                writer.newLine();
            }
        }
        Files.move(temp, mainGraphFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private CharSequence toBase64Data(File legendGraphImageFile) throws IOException {
        byte[] imageBytes = Files.readAllBytes(legendGraphImageFile.toPath());
        var base64 = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64;
    }
}
