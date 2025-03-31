package com.github.gzougianos.packagraph2.core;

import java.io.*;
import java.net.*;

public class ResourcesFolder {

    public static File asFile() {
        URL resource = ResourcesFolder.class.getClassLoader().getResource("");

        if (resource != null) {
            var file = new File(resource.getFile());
            if (file.isDirectory())
                return file;
        }

        throw new RuntimeException("Test resources folder could not be found");
    }

    public static File get(String fileName) {
        return new File(asFile(), fileName);
    }
}
