package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph.*;
import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;

import static com.github.gzougianos.packagraph2.FileComparator.assertFilesEquals;

class RenderingShould {

    TempDir tempDir = new TempDir();

    @Test
    void apply_maingraph_style() throws Exception {
        File tempExportFile = Files.createTempFile("test", ".png").toFile();
        var script = """
                include source directory '%s';
                show maingraph with style 'main_graph_style';
                define style 'main_graph_style' as 'label=Testing;dpi=100;fontcolor=red';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        Options options = run(script);

        //A-->java.util
        tempDir.addJavaFile("A.java", """
                package packageA;
                
                public class A{ }
                """);

        var graph = Packagraph.create(options);
        File output = new GraphvizRenderer(graph).render();
        assertFilesEquals(preRenderedFile("simple_main_graph_style.png"), output);
    }

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }

    private static File preRenderedFile(String file) {
        return new File(ResourcesFolder.asFile(), "2/" + file);
    }
}
