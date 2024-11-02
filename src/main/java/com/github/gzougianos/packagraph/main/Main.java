package com.github.gzougianos.packagraph.main;

import com.github.gzougianos.packagraph.Arguments;
import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.Packagraph;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph.graphviz.GraphvizFactory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var arguments = Arguments.of(args);
        var options = PackagraphOptions.fromJson(arguments.optionsFile());
        var nodes = Packagraph.create(options);

        GraphLibrary graphLibrary = GraphvizFactory.create();
        graphLibrary.createGraph(nodes, options);
    }
}
