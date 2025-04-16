package com.github.gzougianos.packagraph2.core;

import com.github.gzougianos.packagraph2.*;
import com.github.gzougianos.packagraph2.antlr4.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;

import static com.github.gzougianos.packagraph2.core.FileComparator.*;

class RenderingShould {

    TempSourceDirectory tempDir = new TempSourceDirectory();

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
    //       v         v
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
    //       v         v
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

    //  +-----------+
    //  | packageA  |
    //  +-----------+
    // Yellow, filled node
    @Test
    void apply_node_style() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show nodes 'packageA' with style 'some_style';
                define style 'some_style' as 'style=filled;fillcolor=yellow';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                
                public class A{ }
                """);

        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("node_style.png"), output);
    }

    @Test
    void not_render_node_at_all_if_defined_with_empty_name() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show nodes 'packageB' as ''; //completely ignore node
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.pathAsString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import packageB;
                
                public class A{ }
                """);

        tempDir.addJavaFile("B.java", """
                package packageB;
                
                public class B{ }
                """);
        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("unrendered_node.png"), output);
    }

    //  +-----------+
    //  | packageA  |
    //  +-----------+
    //       |  ^
    //       |  |
    //       v  |
    //  +-----------+
    //  | packageB  |
    //  +-----------+
    @Test
    void render_circular_dependencies() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.pathAsString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import packageB;
                
                public class A{ }
                """);

        tempDir.addJavaFile("B.java", """
                package packageB;
                import packageA;
                
                public class B{ }
                """);
        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("circular_dependency.png"), output);
    }

    //  +-----------+
    //  | packageA  |
    //  +-----------+
    //       | (red color edge)
    //       v
    //  +------------+
    //  | java.util  |
    //  +------------+
    @Test
    void render_edge_styles() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show edges to 'java.util' with style 'some_style';
                define style 'some_style' as 'color=red';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("edge_style.png"), output);
    }

    //  +-----------+
    //  | packageA  | (yellow node)
    //  +-----------+
    //       |
    //       v
    //  +------------+
    //  | java.util  |
    //  +------------+
    @Test
    void apply_from_node_edge_style() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show edges to 'java.util' with from-node style 'some_style';
                define style 'some_style' as 'style=filled;fillcolor=yellow';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("from_node_edge_style.png"), output);
    }

    //  +-----------+
    //  | packageA  |
    //  +-----------+
    //       |
    //       v
    //  +------------+
    //  | java.util  | (yellow node)
    //  +------------+
    @Test
    void apply_to_node_edge_style() throws Exception {
        File tempExportFile = createTempImage();
        var script = """
                include source directory '%s';
                show edges to 'java.util' with to-node style 'some_style';
                define style 'some_style' as 'style=filled;fillcolor=yellow';
                export as 'png' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        tempDir.addJavaFile("A.java", """
                package packageA;
                import java.util.*;
                
                public class A{ }
                """);

        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("to_node_edge_style.png"), output);
    }

    @Test
    void create_legend_graph_with_node_style() throws Exception {
        File tempExportFile = createTempSvg();
        var script = """
                include source directory '%s';
                define style 'some_style' as 'style=filled;fillcolor=yellow' with node legend;
                export as 'svg' into '%s' by overwriting;
                """.formatted(tempDir.path().toString(), tempExportFile.toString());

        File output = outputOf(script);
        assertFilesEquals(preRenderedFile("simple_node_legend.svg"), output);
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

    private static File createTempSvg() throws IOException {
        File file = Files.createTempFile("test", ".svg").toFile();
        file.deleteOnExit();
        return file;
    }

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }

    private static File preRenderedFile(String file) {
        return TestResourcesFolder.get("pre_rendered/" + file);
    }
}
