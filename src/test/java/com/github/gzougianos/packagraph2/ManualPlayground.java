package com.github.gzougianos.packagraph2;

import org.junit.jupiter.api.*;

public class ManualPlayground {
    @Test
    @Disabled
    void playground() throws Exception {
        var file = TestResourcesFolder.get("for_manual_testing.pg");
        Main.main(new String[]{file.getAbsolutePath()});
    }
}
