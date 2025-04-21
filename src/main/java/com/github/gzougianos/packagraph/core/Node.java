package com.github.gzougianos.packagraph.core;

import com.github.gzougianos.packagraph.analysis.*;

record Node(PackageName packag, boolean isInternal) {

    public boolean isExternal() {
        return !isInternal;
    }
}
