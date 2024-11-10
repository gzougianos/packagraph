package com.github.gzougianos.packagraph.analysis;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@ToString
@EqualsAndHashCode
public final class PackageName {
    static final PackageName ROOT = new PackageName("<no_package>");
    private final String name;

    PackageName(String name) {
        this.name = name.trim();
    }

    public PackageName renamed(String name) {
        return new PackageName(name.trim());
    }
}
