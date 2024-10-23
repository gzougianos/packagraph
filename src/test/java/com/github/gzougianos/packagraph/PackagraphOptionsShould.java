package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.Package;
import com.github.gzougianos.packagraph.analysis.PackageFactoryForTests;
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

}