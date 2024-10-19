package com.github.gzougianos.packagraph.analysis;

import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(fluent = true)
@Getter
@ToString
@EqualsAndHashCode
public final class Package {
    static final Package ROOT = new Package("<no_package>");
    private final String name;

}
