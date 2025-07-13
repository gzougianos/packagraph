package com.github.gzougianos.packagraph.core;

import com.github.gzougianos.packagraph.analysis.*;

record Node(PackageName packag, boolean isInternal) implements Comparable<Node> {

    public boolean isExternal() {
        return !isInternal;
    }

    @Override
    public int compareTo(Node o) {
        return packag.name().compareTo(o.packag.name());
    }
}
