package com.github.gzougianos.packagraph;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PackagraphOptionsShould {

    private static final File SAMPLE_JSON = new File(ResourcesFolder.asFile(), "sample_options.json");

    @Test
    void be_parsed_from_json() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);

        assertFalse(options.includeOnlyFromDirectories());
        assertEquals(2, options.directories().length);
        assertEquals(new File("src/main/java"), options.directories()[0]);
        assertEquals(new File("src/test/java"), options.directories()[1]);
        
        assertEquals(2, options.renames().size());
        var rename1 = options.renames().get(0);
        assertEquals("java.util.*", rename1.rename());
        assertEquals("java", rename1.to());

        var rename2 = options.renames().get(1);
        assertEquals("com.github.com.", rename2.rename());
        assertEquals("", rename2.to());
    }

}