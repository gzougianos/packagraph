package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph.*;
import com.github.gzougianos.packagraph.analysis.*;
import lombok.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Packagraph {

    private final Options options;

    public static Packagraph create(Options options) {
        return new Packagraph(options);
    }

    public Graph graph() {
        List<File> javaFiles = JavaFilesFinder.findWithin(sourceDirectories());
        final var analyzed = javaFiles.stream()
                .map(Packagraph::asClass)
                .filter(Objects::nonNull)
                .toList();

        Set<Node> nodes = new HashSet<>();
        Set<Edge> edges = new HashSet<>();
        for (var javaClass : analyzed) {
            Node node = new Node(javaClass.packag());
            nodes.add(node);
            
            List<Node> dependencies = javaClass.imports().stream().map(Node::new).toList();

            nodes.addAll(dependencies);
            edges.addAll(dependencies.stream().map(dependency -> new Edge(node, dependency)).toList());
        }
        return new Graph(nodes, edges);
    }

    private List<File> sourceDirectories() {
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
}
