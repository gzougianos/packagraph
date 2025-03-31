package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OptionsShould {
    TempDir tempDir = new TempDir();

    @Test
    void name_a_node_with_its_package_if_no_show_nodes_defined() throws Exception {
        var script = """
                include source directory '%s';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals("packageA", options.nameOf(graph.findNode("packageA")));
    }

    @Test
    void name_a_node_with_its_package_if_no_custom_name_defined() throws Exception {
        var script = """
                include source directory '%s';
                show nodes 'packageA';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals("packageA", options.nameOf(graph.findNode("packageA")));
    }

    @Test
    void name_a_node_with_custom_name() throws Exception {
        var script = """
                include source directory '%s';
                show nodes 'packageA' as 'custom_name';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals("custom_name", options.nameOf(graph.findNode("packageA")));
    }

    @Test
    void name_a_node_with_custom_name_based_on_last_definition() throws Exception {
        var script = """
                include source directory '%s';
                show nodes 'packageA' as 'custom_name';
                show nodes 'packageA' as 'custom_name_2';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals("custom_name_2", options.nameOf(graph.findNode("packageA")));
    }

    @Test
    void name_a_node_based_on_regex_group() throws Exception {
        var script = """
                include source directory '%s';
                show nodes 'com.testing\\.(.*)' as '$1';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package com.testing.apackage;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals("apackage", options.nameOf(graph.findNode("com.testing.apackage")));
    }

    @Test
    void know_the_style_of_a_node() throws Exception {
        var script = """
                include source directory '%s';
                show nodes 'packageA' with style 'some_style';
                define style 'some_style' as 'style=filled;fillcolor=yellow';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals(Map.of("style", "filled", "fillcolor", "yellow"),
                options.styleOf(graph.findNode("packageA")));
    }

    @Test
    void know_the_style_of_a_node_when_using_constants() throws Exception {
        var script = """
                include source directory '%s';
                show nodes 'packageA' with style 'some_style';
                define style 'some_style' as 'style=filled;fillcolor=${YELLOW_CLR}';
                define constant 'YELLOW_CLR' as 'yellow';
                """.formatted(tempDir.pathAsString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);

        assertEquals(Map.of("style", "filled", "fillcolor", "yellow"),
                options.styleOf(graph.findNode("packageA")));
    }

    @Test
    void change_relative_source_directory_references_on_base_dir() throws Exception {
        var script = """
                include source directory '%s';//absolute path
                include source directory '%s';//relative path
                """.formatted(tempDir.pathAsString(), "myfolder");

        var anotherTempDir = new TempDir();
        var options = run(script);
        options = options.withBaseDir(anotherTempDir.path().toFile());

        var expectedSource1 = tempDir.file();
        var expectedSource2 = new File(anotherTempDir.file(), "myfolder");

        assertTrue(options.sourceDirectories().contains(expectedSource1.toString()));
        assertTrue(options.sourceDirectories().contains(expectedSource2.toString()));
    }

    @Test
    void change_relative_export_file_on_base_dir() throws Exception {
        var script = """
                export as 'png' into 'myfile.png' by overwriting;
                """;

        var anotherTempDir = new TempDir();
        var options = run(script);
        options = options.withBaseDir(anotherTempDir.path().toFile());

        var expectedOutputFile = new File(anotherTempDir.file(), "myfile.png");
        assertEquals(expectedOutputFile.toString(), options.exportInto().filePath());
    }

    @Test
    void not_change_export_file_path_on_base_dir_if_export_file_path_is_absolute() throws Exception {
        File absolutePathFile = new File("myfile.png");
        var script = """
                export as 'png' into '%s' by overwriting;
                """.formatted(absolutePathFile.getAbsolutePath());

        var anotherTempDir = new TempDir();
        var options = run(script);
        options = options.withBaseDir(anotherTempDir.path().toFile());

        assertEquals(absolutePathFile.getAbsolutePath(), options.exportInto().filePath());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "show edges from 'packageA' to 'java.util.*' with style 'some_style';",
            "show edges from 'packageA' with style 'some_style';",
            "show edges to 'java.util.*' with style 'some_style';",
    })
    void know_the_style_of_an_edge(String showEdgeStatement) throws Exception {
        var script = """
                include source directory '%s';
                %s
                define style 'some_style' as 'style=filled;fillcolor=yellow;shape=${SHAPE}';
                define constant 'SHAPE' as 'box';
                """.formatted(tempDir.pathAsString(), showEdgeStatement);

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                public class A{ }
                """);

        var options = run(script);
        var graph = Packagraph.create(options);
        var edge = graph.findEdge("packageA", "java.util");
        var edgeStyle = options.styleOf(edge);

        assertEquals(Map.of("style", "filled", "fillcolor", "yellow", "shape", "box"),
                edgeStyle);
    }

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }
}