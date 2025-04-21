package com.github.gzougianos.packagraph.core;

record Edge(Node from, Node to) {

    public boolean isFrom(String packageName) {
        return from.packag().name().equals(packageName);
    }

    public boolean isTo(String packageName) {
        return to.packag().name().equals(packageName);
    }
}
