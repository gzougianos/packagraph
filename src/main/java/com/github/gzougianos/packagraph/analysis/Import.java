package com.github.gzougianos.packagraph.analysis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(fluent = true)
@Getter
@ToString
public final class Import {
    private final String value;
    private final boolean isStatic;
    private final boolean isAsterisk;

}
