package com.github.gzougianos.packagraph;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JavaFilesFinderShould {

    private static final File RESOURCES_FOLDER = ResourcesFolder.asFile();
    private static final File PLACE_HOLDER_PATH = new File(RESOURCES_FOLDER, "placeholder");
    private static final File RESOURCES_FOLDER_FOR_TESTING = new File(RESOURCES_FOLDER, "forFileFinder");

    @Test
    void throw_exception_if_is_not_a_directory() {
        assertThrows(IllegalArgumentException.class, () -> JavaFilesFinder.findWithin(PLACE_HOLDER_PATH));
    }

    @Test
    void throw_exception_if_file_does_not_exist() {
        File doesntExist = new File(RESOURCES_FOLDER, "A_FILE_THAT_DOES_NOT_EXIST");
        assertThrows(IllegalArgumentException.class, () -> JavaFilesFinder.findWithin(doesntExist));
    }

    //See the tree below:
    //└── forFileFinder/
    //    ├── src1/
    //    │   ├── subdir/
    //    │   │   ├── MainSubDir.java
    //    │   │   └── placeholder
    //    │   ├── MainSrc1.java
    //    │   └── placeholder
    //    ├── src2/
    //    │   ├── MainSrc2.java
    //    │   └── placeholder
    //    └── placeholder
    @Test
    void find_java_files_from_dirs_and_subdirs() {
        var filesFound = JavaFilesFinder.findWithin(RESOURCES_FOLDER_FOR_TESTING);
        assertEquals(3, filesFound.size());

        assertContains("MainSubDir.java", filesFound);
        assertContains("MainSrc1.java", filesFound);
        assertContains("MainSrc2.java", filesFound);
    }

    private void assertContains(String name, List<File> filesFound) {
        assertEquals(1, filesFound.stream().filter(f -> f.getName().equals(name)).count());
    }
}