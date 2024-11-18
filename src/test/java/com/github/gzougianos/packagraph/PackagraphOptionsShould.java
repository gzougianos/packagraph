package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.PackageName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

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

        PackageName java = new PackageName("java.util");
        PackageName renamedJava = options.rename(java);
        assertEquals("java", renamedJava.name());

        PackageName github = new PackageName("com.github.com");
        PackageName renamedGithub = options.rename(github);
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

        PackageName pack1 = new PackageName("com.something.pack1");
        PackageName pack2 = new PackageName("com.something.pack2");
        PackageName pack1Subpack = new PackageName("com.something.pack1.subpack");

        PackageName renamedPack1 = options.rename(pack1);
        assertEquals("pack1", renamedPack1.name());

        PackageName renamedPack2 = options.rename(pack2);
        assertEquals("pack2", renamedPack2.name());

        PackageName renamedPack1Subpack = options.rename(pack1Subpack);
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

        PackageName java = new PackageName("java");
        var style = options.nodeStyleOf(java);
        assertEquals("green", style.get("fillcolor"));
    }

    @Test
    void know_the_node_inner_style_of_a_package() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "nodeStyle": {
                        "fillcolor": "green"
                      }
                    }
                  ]
                """);

        PackageName java = new PackageName("java");
        var style = options.nodeStyleOf(java);
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

        PackageName java = new PackageName("java");
        var style = options.nodeStyleOf(java);
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

        PackageName java = new PackageName("java");
        var style = options.nodeStyleOf(java);
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

        PackageName java = new PackageName("java");
        var style = options.nodeStyleOf(java);
        assertEquals("pink", style.get("fillcolor"));
        assertNull(style.get("fontcolor"));
    }

    @Test
    void know_the_edge_in_style_of_a_package() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "edgeInStyle": "JAVA_STYLE"
                    }
                  ],
                  "edgeStyles": {
                    "JAVA_STYLE": {
                      "color": "green"
                    }
                  }
                """);

        PackageName java = new PackageName("java");
        var style = options.edgeInStyleOf(java);
        assertEquals("green", style.get("color"));
    }

    @Test
    void know_the_anonymous_inner_edge_in_style_of_a_package() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "edgeInStyle": {
                         "color": "green"
                      }
                    }
                  ]
                """);

        PackageName java = new PackageName("java");
        var style = options.edgeInStyleOf(java);
        assertEquals("green", style.get("color"));
    }

    @Test
    void give_default_edge_in_style_if_not_specified() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                    }
                  ],
                  "edgeStyles": {
                    "default": {
                      "color": "green"
                    }
                  }
                """);

        PackageName java = new PackageName("java");
        var style = options.edgeInStyleOf(java);
        assertEquals("green", style.get("color"));
    }

    @Test
    void inherit_default_edge_in_style() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "edgeInStyle": "JAVA_STYLE"
                    }
                  ],
                  "edgeStyles": {
                    "default": {
                      "fillcolor": "green",
                      "fontcolor": "blue"
                    },
                    "JAVA_STYLE": {
                      "fillcolor": "pink",
                    }
                  }
                """);

        PackageName java = new PackageName("java");
        var style = options.edgeInStyleOf(java);
        assertEquals("pink", style.get("fillcolor"));
        assertEquals("blue", style.get("fontcolor"));
    }

    @Test
    void not_inherit_default_edge_in_style() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java",
                      "edgeInStyle": "JAVA_STYLE"
                    }
                  ],
                  "edgeStyles": {
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

        PackageName java = new PackageName("java");
        var style = options.edgeInStyleOf(java);
        assertEquals("pink", style.get("fillcolor"));
        assertNull(style.get("fontcolor"));
    }

    @Test
    void know_the_cluster_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  "directories": [
                    "src/test/java"
                  ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java"
                    }
                  ],
                  "clusters": [
                    {
                      "packages": "java.*",
                      "name": "something",
                      "graphStyle": "MY_STYLE" 
                    }
                  ],
                  "graphStyles": {
                     "MY_STYLE":{
                        "label": "My Style",
                        "color": "blue"
                     }
                  }
                }""");
        PackageName java = new PackageName("java.util");
        assertEquals("something", options.clusterOf(java).orElseThrow());

        var clusterStyle = options.clusterStyleOf("something");
        assertEquals("My Style", clusterStyle.get("label"));
        assertEquals("blue", clusterStyle.get("color"));
    }

    @Test
    void know_the_anonymous_inner_cluster_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  "directories": [
                    "src/test/java"
                  ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java"
                    }
                  ],
                  "clusters": [
                    {
                      "packages": "java.util.*",
                      "name": "something",
                      "graphStyle": {
                        "color": "red",
                        "label": "Something",
                      }
                    }
                  ]
                }""");
        PackageName java = new PackageName("java.util");
        assertEquals("something", options.clusterOf(java).orElseThrow());

        var clusterStyle = options.clusterStyleOf("something");
        assertEquals("Something", clusterStyle.get("label"));
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
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  "constants": [
                    {
                        "name": "OUTPUT_FILE",
                        "value": "src/main/java.png"
                    }
                  ],
                  "directories": [
                    "src/main/java"
                  ],
                  "output": {
                    "path": "${OUTPUT_FILE}",
                    "overwrite": true,
                  }
                
                }""");

        assertEquals(new File("src/main/java.png"), options.outputFile());
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
        PackagraphOptions options = PackagraphOptions.fromJson("""
                  "directories": [
                       "src/main/java"
                   ],
                  "definitions": [
                    {
                      "packages": "java.util.*",
                      "as": "java"
                    }
                  ]
                """);

        PackageName java = new PackageName("java");

        var style = options.nodeStyleOf(java);
        assertEquals("java.util.*", style.get("tooltip"));
    }
}