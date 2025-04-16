package com.github.gzougianos.packagraph2.core;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

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
                .with(Shape.NONE, Image.of(tempImageWithLegendGraph.toPath().toAbsolutePath().toString()));
    }

    private File createLegendGraphToTempPng() throws IOException {
        var nodeLegends = options().nodeLegends();
        var edgeLegends = options().edgeLegends();

        MutableGraph cluster = Factory.graph("legends").toMutable();

        for (var legend : nodeLegends.values()) {
            var legendNode = createLegendNode(legend);
            cluster.add(legendNode);
        }

        File destinationFile = Files.createTempFile("legend_graph", ".png").toFile();
        destinationFile.deleteOnExit();
        Graphviz.fromGraph(cluster)
                .render(Format.PNG)
                .toFile(destinationFile);

        return destinationFile;
    }

    private guru.nidi.graphviz.model.Node createLegendNode(Legend legend) {
        var gNode = Factory.node(legend.name());
        for (var entry : legend.style().entrySet()) {
            gNode = gNode.with(entry.getKey(), entry.getValue());
        }
        return gNode.with("constraint", "false");
    }

    private Options options() {
        return graph.options();
    }

    private void applyMainGraphStyle(MutableGraph mainGraph) {
        for (Map.Entry<String, String> entry : options().mainGraphStyleAttributes().entrySet()) {
            mainGraph.graphAttrs().add(entry.getKey(), entry.getValue());
        }
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
