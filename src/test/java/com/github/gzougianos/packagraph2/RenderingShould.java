package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph.*;
import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;

import static com.github.gzougianos.packagraph2.FileComparator.assertFilesEquals;

public class RenderingShould {

    @Test
    void apply_maingraph_style() throws Exception {
        TempDir dir = new TempDir();
        File tempExportFile = Files.createTempFile("test", ".png").toFile();
        var includeSourceDirectory = includeSourceDirectory(dir);
        var script = includeSourceDirectory + """
                show maingraph with style 'main_graph_style';
                define style 'main_graph_style' as 'label=Testing;dpi=100;fontcolor=red';
                """;
        Options options = run(script);

        //A-->java.util
        dir.addJavaFile("A.java", """
                package packageA;
                
                public class A{ }
                """);

        var graph = Packagraph.create(options);
        File output = new GraphvizRenderer(graph).render();
        assertFilesEquals(renderingFile("simple_main_graph_style.png"), output);
    }

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }

    private static String includeSourceDirectory(TempDir dir) {
        return "include source directory '" + dir.path().toString() + "';";
    }

    private static File renderingFile(String file) {
        return new File(ResourcesFolder.asFile(), "2/" + file);
    }
}
