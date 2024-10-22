package com.github.gzougianos.packagraph;

import java.util.Collection;

public interface GraphLibrary {

    void createGraph(Collection<PackageNode> nodes, PackagraphOptions options);
}
