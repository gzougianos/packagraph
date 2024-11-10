package com.github.gzougianos.packagraph.analysis;

public class PackageFactoryForTests {

    public static PackageName create(String name) {
        return new PackageName(name);
    }
}
