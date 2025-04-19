package com.github.gzougianos.packagraph.core;

import com.github.gzougianos.packagraph.*;
import com.github.gzougianos.packagraph.antlr4.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PackagraphShould {

    @Test
    void create_node_from_internal_source() throws Exception {
        TempSourceDirectory dir = new TempSourceDirectory();
        Options options = includeSourceDir(dir);

        dir.addJavaFile("A.java", """
                package assumeP1;
                
                public class HelloWorld{
                }
                """);

        var graph = Packagraph.create(options);
        assertEquals(1, graph.nodes().size());
        assertTrue(graph.containsNode("assumeP1"));
    }

    @Test
    void create_node_with_internal_dependency() throws Exception {
        TempSourceDirectory dir = new TempSourceDirectory();
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
        assertTrue(graph.containsEdge("packageA", "packageB"));
    }

    @Test
    void create_node_with_external_dependency() throws Exception {
        TempSourceDirectory dir = new TempSourceDirectory();
        Options options = includeSourceDir(dir);

        //A-->java.util
        dir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        var graph = Packagraph.create(options);
        assertEquals(2, graph.nodes().size());
        assertTrue(graph.containsNode("java.util"));

        assertEquals(1, graph.edges().size());
        assertTrue(graph.containsEdge("packageA", "java.util"));
    }

    @Test
    void know_if_a_node_is_internal_or_not() throws Exception {
        TempSourceDirectory dir = new TempSourceDirectory();
        Options options = includeSourceDir(dir);

        //A-->java.util
        dir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        var graph = Packagraph.create(options);
        var internalnode = graph.findNode("packageA");
        assertTrue(internalnode.isInternal());

        var externalnode = graph.findNode("java.util");
        assertFalse(externalnode.isInternal());
    }

    @Test
    void understand_all_kinds_of_imports() throws Exception {
        TempSourceDirectory dir = new TempSourceDirectory();
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
        assertTrue(graph.containsNode("packageA"));
        assertTrue(graph.containsNode("java.util"));
        assertTrue(graph.containsNode("java.lang"));
        assertTrue(graph.containsNode("java.lang.annotation"));
        assertTrue(graph.containsNode("java.time.temporal"));
    }

    private static Options includeSourceDir(TempSourceDirectory dir) throws Exception {
        return PgLangInterpreter.interprete("include source directory '" + dir.path().toString() + "';");
    }
}