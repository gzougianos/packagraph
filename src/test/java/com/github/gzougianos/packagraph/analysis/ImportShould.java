package com.github.gzougianos.packagraph.analysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportShould {


    @Test
    void know_the_referring_package_when_is_regular() {
        var importt = new Import("java.io.File", false, false);
        assertEquals(new Package("java.io"), importt.packag());
    }

    @Test
    void know_the_referring_package_when_is_static() {
        var importt = new Import("java.io.File.createTempFile", true, false);
        assertEquals(new Package("java.io"), importt.packag());
    }

    @Test
    void know_the_referring_package_when_is_wildcard() {
        var importt = new Import("java.io.*", false, true);
        assertEquals(new Package("java.io"), importt.packag());
    }

    @Test
    void know_the_referring_package_when_is_wildcard_static() {
        var importt = new Import("java.io.File.*", true, true);
        assertEquals(new Package("java.io"), importt.packag());
    }
}
