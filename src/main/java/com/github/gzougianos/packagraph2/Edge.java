package com.github.gzougianos.packagraph2;

public record Edge(Node from, Node to) {

    public boolean isFrom(String packageName) {
        return from.packag().name().equals(packageName);
    }

    public boolean isTo(String packageName) {
        return to.packag().name().equals(packageName);
    }
}
