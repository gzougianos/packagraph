package com.github.gzougianos.packagraph2.analysis;


public record PackageName(String name) {
    static final PackageName ROOT = new PackageName("<no_package>");

    public PackageName(String name) {
        this.name = name.trim();
    }
}
