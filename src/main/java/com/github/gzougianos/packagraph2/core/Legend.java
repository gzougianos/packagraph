package com.github.gzougianos.packagraph2.core;

import java.util.*;

public record Legend(String name, Map<String, String> style) {
    public boolean hasName(String name) {
        return Objects.equals(this.name, name);
    }
}
