package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PackagraphShould {

    @Test
    void create_node_from_internal_source() throws Exception {
        TempDir dir = new TempDir();
        Options options = includeSourceDir(dir);

        dir.addJavaFile("A.java", """
                package assumeP1;
                
                public class HelloWorld{
                }
                """);

        Graph graph = Packagraph.create(options).graph();
        assertEquals(1, graph.nodes().size());

        Node node = graph.nodes().iterator().next();
        assertEquals("assumeP1", node.packag().name());
    }

    @Test
    void create_node_with_internal_dependency() throws Exception {
        TempDir dir = new TempDir();
        Options options = includeSourceDir(dir);

        //A-->B
        dir.addJavaFile("A.java", """
                package packageA;
                import packageB.B;
                
                public class A{ }
                """);

        dir.addJavaFile("B.java", """
                package packageB;
                
                public class B{}
                """);

        Graph graph = Packagraph.create(options).graph();
        assertEquals(2, graph.nodes().size());

        assertEquals(1, graph.edges().size());
        Edge edge = graph.edges().iterator().next();
        assertEquals("packageA", edge.from().packag().name());
        assertEquals("packageB", edge.to().packag().name());
    }

    @Test
    void create_node_with_external_dependency() throws Exception {
        TempDir dir = new TempDir();
        Options options = includeSourceDir(dir);

        //A-->java.util
        dir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        Graph graph = Packagraph.create(options).graph();
        assertEquals(2, graph.nodes().size());
        assertContainsNode(graph.nodes(), "java.util");

        assertEquals(1, graph.edges().size());
        Edge edge = graph.edges().iterator().next();
        assertEquals("packageA", edge.from().packag().name());
        assertEquals("java.util", edge.to().packag().name());
    }

    private static Options includeSourceDir(TempDir dir) throws Exception {
        return PgLangInterpreter.interprete("include source directory '" + dir.path().toString() + "';");
    }

    private static boolean assertContainsNode(Set<Node> nodes, String packageName) {
        return nodes.stream().anyMatch(node -> node.packag().name().equals(packageName));
    }
}