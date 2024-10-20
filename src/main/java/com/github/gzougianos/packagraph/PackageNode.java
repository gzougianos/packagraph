package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PackageNode {

    @Getter
    @EqualsAndHashCode.Include
    private final Package packag;
    private final Collection<PackageNode> dependencies = new HashSet<>();

    public PackageNode(Package packag) {
        this.packag = packag;
    }


    public void dependOn(PackageNode node) {
        if (node == this) {
            return;
        }
        dependencies.add(node);
    }

    public boolean dependsOn(PackageNode node) {
        return dependencies.contains(node);
    }


    public Collection<PackageNode> dependencies() {
        return dependencies;
    }
}
