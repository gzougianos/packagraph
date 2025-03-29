package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph.*;
import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;

import static com.github.gzougianos.packagraph2.FileComparator.assertFilesEquals;

class RenderingShould {

    TempDir tempDir = new TempDir();

    //      +------------+
    //      | packageA   |
    //      | (Testing)  |
    //      +------------+
    @Test
    void apply_maingraph_style() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show maingraph with style 'main_graph_style';
                define style 'main_graph_style' as 'label=Testing;dpi=100;fontcolor=red';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                
                public class A{ }
                """);

        var graph = Packagraph.create(run(script));
        File output = new GraphvizRenderer(graph).render();
        assertFilesEquals(preRenderedFile("simple_main_graph_style.png"), output);
    }

    //      +-----------+
    //      | packageA  |
    //      +-----------+
    @Test
    void exclude_external_nodes() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                exclude externals;
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        var graph = Packagraph.create(run(script));
        File output = new GraphvizRenderer(graph).render();
        assertFilesEquals(preRenderedFile("exclude_externals.png"), output);
    }

    //      +-----------+
    //      | packageA  |
    //      +-----------+
    //         /     \
    //        /       \
    //+------------+  +-----------+
    //| packageB   |  | java.util |
    //+------------+  +-----------+
    @Test
    void render_edges_with_external_dependencies() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                import packageB.B;
                
                public class A{ }
                """);

        tempDir.addJavaFile("B.java", """
                package packageB;
                
                public class B{ }
                """);
        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("edges_with_external_dependencies.png"), output);
    }

    //  +-----------+
    //  | packageA  |
    //  +-----------+
    //       |
    //       v
    //  +-----------+
    //  | packageB  |
    //  +-----------+
    @Test
    void render_edges_without_external_dependencies() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                exclude externals;
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                import packageB.B;
                
                public class A{ }
                """);

        tempDir.addJavaFile("B.java", """
                package packageB;
                
                public class B{ }
                """);
        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("edges_without_external_dependencies.png"), output);
    }

    //      +-----------+
    //      | packageA  |
    //      +-----------+
    //         /     \
    //        /       \
    //+------------+  +------+
    //| packageB   |  | util |
    //+------------+  +------+
    @Test
    void render_renamed_node() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show nodes 'java.util.*' as 'util';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.Set;
                import packageB.B;
                
                public class A{ }
                """);

        tempDir.addJavaFile("B.java", """
                package packageB;
                
                public class B{ }
                """);
        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("renamed_node.png"), output);
    }

    //  +-----------+
    //  | my_group  |
    //  +-----------+
    //       |
    //       v
    //  +------------+
    //  | java.util  |
    //  +------------+
    @Test
    void group_and_rename_nodes() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show nodes 'package.*' as 'my_group';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.Set;
                import packageB.B;
                
                public class A{ }
                """);

        tempDir.addJavaFile("B.java", """
                package packageB;
                
                public class B{ }
                """);
        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("renamed_node_as_group.png"), output);
    }

    private File outputOf(String script) throws Exception {
        var graph = Packagraph.create(run(script));
        return new GraphvizRenderer(graph).render();
    }

    private static File createTempImage() throws IOException {
        File file = Files.createTempFile("test", ".png").toFile();
        file.deleteOnExit();
        return file;
    }

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }

    private static File preRenderedFile(String file) {
        return new File(ResourcesFolder.asFile(), "pre_rendered/" + file);
    }
}
