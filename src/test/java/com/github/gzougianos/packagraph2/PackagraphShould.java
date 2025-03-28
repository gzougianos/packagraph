package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

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

        var graph = Packagraph.create(options);
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

        var graph = Packagraph.create(options);
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

        var graph = Packagraph.create(options);
        assertEquals(2, graph.nodes().size());
        assertContainsNode(graph.nodes(), "java.util");

        assertEquals(1, graph.edges().size());
        Edge edge = graph.edges().iterator().next();
        assertEquals("packageA", edge.from().packag().name());
        assertEquals("java.util", edge.to().packag().name());
    }

    @Test
    void understand_all_kinds_of_imports() throws Exception {
        TempDir dir = new TempDir();
        Options options = includeSourceDir(dir);

        dir.addJavaFile("A.java", """
                package packageA;
                import java.util.*; //asterisk import
                import java.lang.Thread; //"standard" import
                import static java.lang.annotation.RetentionPolicy.RUNTIME; //"Static" import
                import static java.time.temporal.ChronoUnit.*;//"Static asterisk" import
                
                public class A{ }
                """);
        var graph = Packagraph.create(options);
        assertEquals(5, graph.nodes().size());

        assertContainsNode(graph.nodes(), "packageA");
        assertContainsNode(graph.nodes(), "java.util");
        assertContainsNode(graph.nodes(), "java.lang.Thread");
        assertContainsNode(graph.nodes(), "java.lang.annotation");
        assertContainsNode(graph.nodes(), "java.time.temporal");
    }

    private static Options includeSourceDir(TempDir dir) throws Exception {
        return PgLangInterpreter.interprete("include source directory '" + dir.path().toString() + "';");
    }

    private static boolean assertContainsNode(Set<Node> nodes, String packageName) {
        return nodes.stream().anyMatch(node -> node.packag().name().equals(packageName));
    }
}