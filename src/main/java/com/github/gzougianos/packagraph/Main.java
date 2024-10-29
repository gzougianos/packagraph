package com.github.gzougianos.packagraph;

public class Main {

    public static void main(String[] args) {
        PackagraphOptions options = PackagraphOptions.parse(args);
        Packagraph.packagraph(options);
    }
}
