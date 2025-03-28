package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph.analysis.*;
import lombok.*;
import lombok.experimental.*;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Node {

    @Getter
    @EqualsAndHashCode.Include
    private final PackageName packag;

    Node(PackageName packag) {
        this.packag = packag;
    }
}
