package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import com.github.gzougianos.packagraph.analysis.PackageFactoryForTests;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PackagraphOptionsShould {

    private static final File SAMPLE_JSON = new File(ResourcesFolder.asFile(), "sample_options.json");

    @Test
    void parse_directories_from_json() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                        "directories": [
                           "src/main/java",
                           "src/test/java"
                        ]
                """);
        assertFalse(options.includeOnlyFromDirectories());
        assertEquals(2, options.directories().length);
        assertEquals(new File("src/main/java"), options.directories()[0]);
        assertEquals(new File("src/test/java"), options.directories()[1]);
    }

    @Test
    void parse_output_from_json() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                        "directories": [
                           "src/main/java",
                           "src/test/java"
                        ],
                        "output": {
                            "path": "target/packagraph.png",
                            "overwrite": true
                        }
                """);

        assertEquals(new File("target/packagraph.png"), options.outputFile());
        assertTrue(options.allowsOverwriteOutput());
    }

    @Test
    void rename_packages() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                    },
                    {
                      "packages": "com.github.com",
                      "as": "",
                    },
                    {
                      "packages": "com.something\\\\.(.*)",
                      "as": "$1"
                    }
                  ]
                """);

        Package java = PackageFactoryForTests.create("java.util");
        Package renamedJava = options.rename(java);
        assertEquals("java", renamedJava.name());

        Package github = PackageFactoryForTests.create("com.github.com");
        Package renamedGithub = options.rename(github);
        assertEquals("", renamedGithub.name());
    }

    //Suppose 3 packages: com.something.pack1 and com.something.pack2 and com.something.pack1.subpack
    //We want to trim the "com.something" part and let only pack1/pack2/pack1.subpack
    //    {
    //      "packages": "com.something\\.(.*)",
    //      "as": "$1"
    //    }
    @Test
    void rename_packages_with_regex_groups() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "com.something\\\\.(.*)",
                      "as": "$1"
                    }
                  ]
                """);

        Package pack1 = PackageFactoryForTests.create("com.something.pack1");
        Package pack2 = PackageFactoryForTests.create("com.something.pack2");
        Package pack1Subpack = PackageFactoryForTests.create("com.something.pack1.subpack");

        Package renamedPack1 = options.rename(pack1);
        assertEquals("pack1", renamedPack1.name());

        Package renamedPack2 = options.rename(pack2);
        assertEquals("pack2", renamedPack2.name());

        Package renamedPack1Subpack = options.rename(pack1Subpack);
        assertEquals("pack1.subpack", renamedPack1Subpack.name());
    }

    @Test
    void know_the_node_style_of_a_package() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "nodeStyle": "JAVA_STYLE"
                    }
                  ],
                  "nodeStyles": {
                    "JAVA_STYLE": {
                      "fillcolor": "green"
                    }
                  }
                """);

        Package java = PackageFactoryForTests.create("java");
        var style = options.styleOf(java);
        assertEquals("green", style.get("fillcolor"));
    }

    @Test
    void give_the_default_node_style_if_not_specified() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java"
                    }
                  ],
                  "nodeStyles": {
                    "default": {
                      "fillcolor": "green"
                    }
                  }
                """);

        Package java = PackageFactoryForTests.create("java");
        var style = options.styleOf(java);
        assertEquals("green", style.get("fillcolor"));
    }

    @Test
    void inherit_default_node_style() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "nodeStyle": "JAVA_STYLE"
                    }
                  ],
                  "nodeStyles": {
                    "default": {
                      "fillcolor": "green",
                      "fontcolor": "blue"
                    },
                    "JAVA_STYLE": {
                      "fillcolor": "pink",
                    }
                  }
                """);

        Package java = PackageFactoryForTests.create("java");
        var style = options.styleOf(java);
        assertEquals("pink", style.get("fillcolor"));
        assertEquals("blue", style.get("fontcolor"));
    }

    @Test
    void not_inherit_node_style_if_disabled() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "nodeStyle": "JAVA_STYLE"
                    }
                  ],
                  "nodeStyles": {
                    "default": {
                      "fillcolor": "green",
                      "fontcolor": "blue"
                    },
                    "JAVA_STYLE": {
                      "fillcolor": "pink",
                      "inheritDefault": false
                    }
                  }
                """);

        Package java = PackageFactoryForTests.create("java");
        var style = options.styleOf(java);
        assertEquals("pink", style.get("fillcolor"));
        assertNull(style.get("fontcolor"));
    }

    @Test
    void know_the_edge_in_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("java");
        var edgeStyle = options.edgeInStyleOf(java);

        assertEquals("Sample Edge", edgeStyle.get("label"));
        assertEquals("red", edgeStyle.get("color"));
        assertEquals("solid", edgeStyle.get("style"));
    }

    @Test
    void know_the_edge_in_style_without_inherit() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("com.github.com");
        var edgeStyle = options.edgeInStyleOf(java);

        assertEquals("green", edgeStyle.get("color"));
        assertNull(edgeStyle.get("label"));
        assertNull(edgeStyle.get("style"));
    }

    @Test
    void know_the_cluster_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("java.util");
        assertEquals("something", options.clusterOf(java).orElseThrow());

        var clusterStyle = options.clusterStyleOf("something");
        assertEquals("Something", clusterStyle.get("label"));
        assertEquals("42", clusterStyle.get("fontsize"));
        assertEquals("red", clusterStyle.get("color"));
    }

    @Test
    void know_the_main_graph_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        var style = options.mainGraphStyle();

        assertEquals("a_label", style.get("label"));
        assertEquals("24", style.get("fontsize"));
        assertEquals("Tahoma", style.get("fontname"));
    }

    @Test
    void parse_human_json_format() {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  //Some comments
                  #Some other
                  "includeOnlyFromDirectories": false,
                  "directories": [
                    "src/main/java",
                  ],
                  /*
                   * C style comments should be supported
                  */
                  "output": {
                    "path": "target/packagraph.png",
                    "overwrite": true,
                  }
                
                }""");
        assertFalse(options.includeOnlyFromDirectories());
    }

    @Test
    void replace_constants() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        var style = options.mainGraphStyle();
        assertEquals("pink", style.get("fontcolor"));
    }

    @Test
    void replace_constants_in_array() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  "constants": [
                    {
                        "name": "SRC_DIR",
                        "value": "src/main/java"
                    },
                    {
                        "name": "TEST_DIR",
                        "value": "src/test/java"
                    }
                  ],
                  "directories": [
                    "${SRC_DIR}",
                    "${TEST_DIR}"
                  ],
                  "output": {
                    "path": "target/packagraph.png",
                    "overwrite": true,
                  }
                
                }""");
        File[] directories = options.directories();
        assertEquals(2, directories.length);
        assertEquals(new File("src/main/java"), directories[0]);
        assertEquals(new File("src/test/java"), directories[1]);
    }

    @Test
    void throws_human_message_when_constant_does_not_exist() throws Exception {
        var exception = assertThrows(RuntimeException.class, () -> PackagraphOptions.fromJson("""
                {
                  "constants": [
                
                  ],
                  "directories": [
                    "${NON_EXISTENT_CONSTANT}/subfolder"
                  ],
                  "output": {
                    "path": "target/packagraph.png",
                    "overwrite": true,
                  }
                }"""));
        assertTrue(exception.getMessage().toLowerCase().contains("not found")
                && exception.getMessage().contains("${NON_EXISTENT_CONSTANT}"));
    }

    @Test
    void replace_multiple_constants_in_single_value() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  "constants": [
                    {
                        "name": "SRC_DIR",
                        "value": "src/main/java"
                    },
                    {
                        "name": "TEST_DIR",
                        "value": "src/test/java"
                    }
                  ],
                  "directories": [
                    "${SRC_DIR}/${TEST_DIR}"
                  ],
                  "output": {
                    "path": "target/packagraph.png",
                    "overwrite": true,
                  }
                
                }""");
        File[] directories = options.directories();
        assertEquals(1, directories.length);
        assertEquals(new File("src/main/java/src/test/java"), directories[0]);
    }

    @Test
    void adds_tooltip_to_nodes_from_definitions() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("java.util");

        Map<String, String> style = options.styleOf(java);
        assertEquals("java.util.*", style.get("tooltip"));
    }
}