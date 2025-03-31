package com.github.gzougianos.packagraph2.core;

import com.github.gzougianos.packagraph2.analysis.*;

public record Node(PackageName packag, boolean isInternal) {

    public boolean isExternal() {
        return !isInternal;
    }
}
