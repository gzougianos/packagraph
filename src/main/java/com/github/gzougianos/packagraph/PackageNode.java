package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph2.analysis.PackageName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PackageNode {

    @Getter
    @EqualsAndHashCode.Include
    private final PackageName packag;
    private final String cluster;
    private final Collection<PackageNode> dependencies = new HashSet<>();

    PackageNode(PackageName packag, String cluster) {
        this.packag = packag;
        this.cluster = cluster;
    }

    public Optional<String> cluster() {
        return Optional.ofNullable(cluster);
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
