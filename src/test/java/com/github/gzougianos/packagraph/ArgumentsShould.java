package com.github.gzougianos.packagraph;


import org.apache.commons.cli.HelpFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ArgumentsShould {


    public static final File RESOURCES = ResourcesFolder.asFile();
    public static final File ARGUMENTS_TESTING_RESOURCES = new File(RESOURCES, "forArgumentsTesting");

    @Test
    void be_created() {
        var optionsTxt = new File(ARGUMENTS_TESTING_RESOURCES, "options.txt");

        var arguments = Arguments.of("-o", optionsTxt.getAbsolutePath());

        assertEquals(optionsTxt, arguments.optionsFile());
    }


    @Test
    void not_be_created_if_options_file_does_not_exist() {
        var optionsTxt = new File(ARGUMENTS_TESTING_RESOURCES, "optionsDOESNOTEXIST.txt");
        var dir1 = new File(ARGUMENTS_TESTING_RESOURCES, "dir1");

        assertThrows(RuntimeException.class, () -> Arguments.of("-o", optionsTxt.getAbsolutePath(), "-d", dir1.getAbsolutePath()));
    }

    @Test
    void not_be_created_if_any_dir_does_not_exist() {
        var dir1 = new File(ARGUMENTS_TESTING_RESOURCES, "dir1DOESNOTEXIST");

        assertThrows(RuntimeException.class, () -> Arguments.of("-d", dir1.getAbsolutePath()));
    }

    @Test
    void not_be_created_if_directory_is_a_file() {
        var dir1 = new File(ARGUMENTS_TESTING_RESOURCES, "options.txt");

        assertThrows(RuntimeException.class, () -> Arguments.of("-d", dir1.getAbsolutePath()));
    }

    @Test
    void not_be_created_if_no_directory_given() {
        assertThrows(RuntimeException.class, () -> Arguments.of());
    }

    @BeforeEach
    void overrideToSilentFormatter() {
        Arguments.setHelpFormatter(mock(HelpFormatter.class));
    }
}