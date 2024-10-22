package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;

public class GraphvizFactory {

    public static GraphLibrary create() {
        return new GraphvizAdapter();
    }
}
