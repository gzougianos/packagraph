package com.github.gzougianos.packagraph.core;

record Edge(Node from, Node to) implements Comparable<Edge> {

    public boolean isFrom(String packageName) {
        return from.packag().name().equals(packageName);
    }

    public boolean isTo(String packageName) {
        return to.packag().name().equals(packageName);
    }

    @Override
    public int compareTo(Edge o) {
        String thisFromTo = this.from.packag().name() + this.to.packag().name();
        String otherFromTo = o.from.packag().name() + o.to.packag().name();
        return thisFromTo.compareTo(otherFromTo);
    }
}
