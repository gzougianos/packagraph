package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

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

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }
}