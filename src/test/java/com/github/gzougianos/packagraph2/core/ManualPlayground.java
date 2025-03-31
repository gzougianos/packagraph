package com.github.gzougianos.packagraph2.core;

import com.github.gzougianos.packagraph2.main.*;
import org.junit.jupiter.api.*;

public class ManualPlayground {
    @Test
    @Disabled
    void playground() throws Exception {
        var file = ResourcesFolder.get("for_manual_testing.pg");
        Main.main(new String[]{file.getAbsolutePath()});
    }
}
