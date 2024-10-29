package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import com.github.gzougianos.packagraph.analysis.PackageFactoryForTests;
import com.github.gzougianos.packagraph.style.GraphStyle;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PackagraphOptionsShould {

    private static final File SAMPLE_JSON = new File(ResourcesFolder.asFile(), "sample_options.json");

    @Test
    void parse_directories_from_json() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        assertFalse(options.includeOnlyFromDirectories());
        assertEquals(2, options.directories().length);
        assertEquals(new File("src/main/java"), options.directories()[0]);
        assertEquals(new File("src/test/java"), options.directories()[1]);
    }

    @Test
    void parse_output_from_json() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);

        assertEquals(new File("target/packagraph.png"), options.outputFile());
        assertTrue(options.allowsOverwriteImageOutput());
    }

    @Test
    void rename_packages() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);

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
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
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
    void know_the_style_of_a_package() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);

        Package java = PackageFactoryForTests.create("java");
        var style = options.styleOf(java);
        assertEquals("green", style.fillcolor()); //package overwrites only fill color, rest is global
        assertEquals("rectangle", style.shape());
        assertEquals("rounded", style.style());
        assertEquals("black", style.color());
        assertEquals("black", style.fontcolor());
        assertEquals(10, style.fontsize());
        assertEquals("Arial", style.fontname());
        assertEquals(1, style.width());
        assertEquals(1, style.height());
        assertTrue(style.fixedsize());
        assertEquals("tooltip", style.tooltip());
        assertEquals("url", style.url());
        assertEquals("target", style.target());
        assertEquals(1, style.layer());
        assertEquals("group", style.group());
        assertEquals("same", style.rank());
        assertEquals(4, style.sides());
        assertEquals(1, style.peripheries());
    }

    @Test
    void know_the_global_style() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package notDefinedPackage = PackageFactoryForTests.create("com.something");
        var style = options.styleOf(notDefinedPackage);

        assertEquals("rectangle", style.shape());
        assertEquals("rounded", style.style());
        assertEquals("lightblue", style.fillcolor());
        assertEquals("black", style.color());
        assertEquals("black", style.fontcolor());
        assertEquals(10, style.fontsize());
        assertEquals("Arial", style.fontname());
        assertEquals(1, style.width());
        assertEquals(1, style.height());
        assertTrue(style.fixedsize());
        assertEquals("tooltip", style.tooltip());
        assertEquals("url", style.url());
        assertEquals("target", style.target());
        assertEquals(1, style.layer());
        assertEquals("group", style.group());
        assertEquals("same", style.rank());
        assertEquals(4, style.sides());
        assertEquals(1, style.peripheries());
    }


    @Test
    void not_inherit_global_style() throws Exception {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package github = PackageFactoryForTests.create("com.github.com");
        var style = options.styleOf(github);

        assertEquals("blue", style.fillcolor());
        assertNull(style.shape());
        assertNull(style.style());
        assertNull(style.color());
        assertNull(style.fontcolor());
        assertNull(style.fontsize());
        assertNull(style.fontname());
        assertNull(style.width());
        assertNull(style.height());
        assertNull(style.fixedsize());
        assertNull(style.tooltip());
        assertNull(style.url());
        assertNull(style.target());
        assertNull(style.layer());
        assertNull(style.group());
        assertNull(style.rank());
        assertNull(style.sides());
        assertNull(style.peripheries());
    }


    @Test
    void know_the_edge_in_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("java");
        var edgeStyle = options.edgeInStyleOf(java);

        assertEquals("Sample Edge", edgeStyle.label());
        assertEquals("red", edgeStyle.color());
        assertEquals("solid", edgeStyle.style());
        assertEquals(2, edgeStyle.weight());
        assertEquals(1.5, edgeStyle.penwidth());
        assertEquals("diamond", edgeStyle.arrowhead());
        assertEquals(1.2, edgeStyle.arrowsize());
        assertEquals("both", edgeStyle.dir());
        assertTrue(edgeStyle.constraint());
        assertEquals(12, edgeStyle.fontsize());
        assertEquals("black", edgeStyle.fontcolor());
        assertTrue(edgeStyle.decorate());
        assertEquals("https://example.com", edgeStyle.url());
    }

    @Test
    void not_inherit_edge_in_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("com.github.com");
        var edgeStyle = options.edgeInStyleOf(java);

        assertEquals("green", edgeStyle.color());
        assertNull(edgeStyle.label());
        assertNull(edgeStyle.style());
        assertNull(edgeStyle.weight());
        assertNull(edgeStyle.penwidth());
        assertNull(edgeStyle.arrowhead());
        assertNull(edgeStyle.arrowsize());
        assertNull(edgeStyle.dir());
        assertNull(edgeStyle.constraint());
        assertNull(edgeStyle.fontsize());
        assertNull(edgeStyle.fontcolor());
        assertNull(edgeStyle.decorate());
        assertNull(edgeStyle.url());

    }

    @Test
    void know_the_cluster_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        Package java = PackageFactoryForTests.create("java.util");
        assertEquals("something", options.clusterOf(java).orElseThrow());

        GraphStyle clusterStyle = options.clusterStyleOf("something");
        assertEquals("Something", clusterStyle.label());
        assertEquals("42", clusterStyle.fontsize());
        assertEquals("red", clusterStyle.color());
    }

    @Test
    void know_the_main_graph_style() throws IOException {
        PackagraphOptions options = PackagraphOptions.fromJson(SAMPLE_JSON);
        var style = options.mainGraphStyle();

        assertEquals("a_label", style.label());
        assertEquals("24", style.fontsize());
        assertEquals("Tahoma", style.fontname());
    }

    @Test
    void parse_human_json_format() {
        PackagraphOptions options = PackagraphOptions.fromJson("""
                {
                  //Some comments
                  "includeOnlyFromDirectories": false,
                  "directories": [
                    "src/main/java",
                  ],
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
        assertEquals("pink", style.fontcolor());
    }
}