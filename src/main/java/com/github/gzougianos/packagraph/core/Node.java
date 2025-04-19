package com.github.gzougianos.packagraph.core;

import com.github.gzougianos.packagraph.analysis.*;

public record Node(PackageName packag, boolean isInternal) {

    public boolean isExternal() {
        return !isInternal;
    }
}
