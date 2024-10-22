package com.github.gzougianos.packagraph.analysis;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@ToString
@EqualsAndHashCode
public final class Package {
    static final Package ROOT = new Package("<no_package>");
    private final String name;

    Package(String name) {
        this.name = name.trim();
    }

    public Package renamed(String name) {
        return new Package(name.trim());
    }
}
