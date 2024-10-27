package com.github.gzougianos.packagraph.graphviz;

import com.github.gzougianos.packagraph.GraphLibrary;
import com.github.gzougianos.packagraph.Packagraph;
import com.github.gzougianos.packagraph.PackagraphOptions;
import com.github.gzougianos.packagraph.ResourcesFolder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class GraphvizManualPlayground {
    private static final File MANUAL_TESTING_JSON = new File(ResourcesFolder.asFile(), "for_manual_testing.json");

    @Test
    @Disabled
    void manual_testing() throws IOException {

        PackagraphOptions options = PackagraphOptions.fromJson(MANUAL_TESTING_JSON);

        var packagraph = Packagraph.create(options);
        GraphLibrary graphLibrary = GraphvizFactory.create();
        graphLibrary.createGraph(packagraph, options);
    }
}