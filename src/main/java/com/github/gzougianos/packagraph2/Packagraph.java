package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph2.analysis.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@Slf4j
public record Packagraph(Options options, Set<Node> nodes, Set<Edge> edges) {

    public static Packagraph create(Options options) {
        List<File> javaFiles = JavaFilesFinder.findWithin(sourceDirectories(options));
        final List<JavaClass> analyzed = javaFiles.stream()
                .map(Packagraph::asClass)
                .filter(Objects::nonNull)
                .toList();

        final var internalPackages = analyzed.stream()
                .map(JavaClass::packag)
                .collect(Collectors.toSet());

        Set<Node> nodes = new HashSet<>();
        Set<Edge> edges = new HashSet<>();
        for (var javaClass : analyzed) {
            Node node = new Node(javaClass.packag(), internalPackages.contains(javaClass.packag()));
            nodes.add(node);

            List<Node> dependencies = javaClass.imports().stream()
                    .map(importt -> new Node(importt, internalPackages.contains(importt))).toList();

            nodes.addAll(dependencies);
            edges.addAll(dependencies.stream().map(dependency -> new Edge(node, dependency)).toList());
        }

        return new Packagraph(options, nodes, edges);
    }

    private static List<File> sourceDirectories(Options options) {
        return options.sourceDirectories()
                .stream()
                .map(Packagraph::toExistingFile)
                .toList();
    }

    private static File toExistingFile(String dir) {
        File file = new File(dir);
        if (!file.exists())
            throw new IllegalArgumentException("Directory not found: " + dir);
        return file;
    }

    private static JavaClass asClass(File javaFile) {
        try {
            return JavaClass.of(javaFile);
        } catch (ClassAnalysisFailedException ex) {
            log.warn("Failed to analyze {}. Message: {}", javaFile.getAbsolutePath(), ex.getMessage());
        }
        return null;
    }

    public Node findNode(String packageName) {
        return nodes().stream()
                .filter(node -> node.packag().name().equals(packageName))
                .findFirst()
                .orElseThrow();
    }

    public boolean containsNode(String packageName) {
        return nodes().stream()
                .anyMatch(node -> node.packag().name().equals(packageName));
    }

    public boolean containsEdge(String fromPackage, String toPackage) {
        return edges().stream()
                .anyMatch(edge -> edge.isFrom(fromPackage) && edge.isTo(toPackage));
    }

    public Edge findEdge(String from, String to) {
        return edges().stream()
                .filter(edge -> edge.isFrom(from) && edge.isTo(to))
                .findFirst()
                .orElseThrow();
    }
}
