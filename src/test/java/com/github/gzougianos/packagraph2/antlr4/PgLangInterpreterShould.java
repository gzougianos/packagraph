package com.github.gzougianos.packagraph2.antlr4;

import com.github.gzougianos.packagraph2.core.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PgLangInterpreterShould {

    @Test
    void interprete_exclude_externals() throws Exception {
        String script = "exclude externals;";
        Options options = PgLangInterpreter.interprete(script);

        assertTrue(options.excludeExternals());
    }

    @Test
    void interprete_include_source_directories() throws Exception {
        String script = "include source directory 'src/main/java';";
        Options options = PgLangInterpreter.interprete(script);

        assertEquals(1, options.sourceDirectories().size());
        assertEquals("src/main/java", options.sourceDirectories().getFirst());
    }

    @Test
    void interprete_show_nodes() throws Exception {
        String script = """
                show nodes 'com.github.something.*' as 'Something' with style 'default';
                show nodes 'com.github.somethingelse';
                """;
        Options options = PgLangInterpreter.interprete(script);

        assertEquals(2, options.showNodes().size());
        Options.ShowNodes showNodesAsWithStyle = options.showNodes().getFirst();

        assertEquals("com.github.something.*", showNodesAsWithStyle.packag());
        assertEquals("Something", showNodesAsWithStyle.as());
        assertEquals("default", showNodesAsWithStyle.style());

        Options.ShowNodes showNodesWithoutStyle = options.showNodes().get(1);
        assertEquals("com.github.somethingelse", showNodesWithoutStyle.packag());
        assertNull(showNodesWithoutStyle.as());
        assertNull(showNodesWithoutStyle.style());
    }

    @Test
    void interprete_show_edges() throws Exception {
        String script = """
                show edges from 'com.github.something' to 'com.github.somethingelse' with style 'default_edge_style'
                     with from-node style 'from_node_style' with to-node style 'to_node_style';
                
                show edges from 'com.github.someelse' with style 'potato';
                """;
        Options options = PgLangInterpreter.interprete(script);

        assertEquals(2, options.showEdges().size());

        var first = options.showEdges().getFirst();
        assertEquals("com.github.something", first.packageFrom());
        assertEquals("com.github.somethingelse", first.packageTo());
        assertEquals("default_edge_style", first.style());
        assertEquals("from_node_style", first.fromNodeStyle());
        assertEquals("to_node_style", first.toNodeStyle());

        var second = options.showEdges().get(1);
        assertEquals("com.github.someelse", second.packageFrom());
        assertNull(second.packageTo());
        assertEquals("potato", second.style());
        assertNull(second.fromNodeStyle());
        assertNull(second.toNodeStyle());
    }

    @Test
    void interprete_define_style() throws Exception {
        String script = "define style 'default' as 'fillcolor=green;shape=oval';";
        Options options = PgLangInterpreter.interprete(script);

        var style = options.defineStyles().getFirst();

        assertEquals("default", style.name());
        assertEquals("fillcolor=green;shape=oval", style.value());
        assertFalse(style.isEdgeLegend());
        assertFalse(style.isNodeLegend());
    }

    @Test
    void interprete_define_style_with_legend() throws Exception {
        String script = "define style 'default' as 'fillcolor=green;shape=oval' with node legend 'my legend';";
        Options options = PgLangInterpreter.interprete(script);

        var style = options.defineStyles().getFirst();

        assertTrue(style.isNodeLegend());
    }

    @Test
    void interprete_define_constant() throws Exception {
        String script = "define constant 'blue_color' as 'lightblue';";
        Options options = PgLangInterpreter.interprete(script);

        var style = options.defineConstant().getFirst();

        assertEquals("blue_color", style.name());
        assertEquals("lightblue", style.value());
    }

    @Test
    void interprete_show_main_graph() throws Exception {
        String script = "show main graph with style 'main_graph_style';";
        Options options = PgLangInterpreter.interprete(script);

        assertEquals("main_graph_style", options.mainGraphStyle());
    }

    @Test
    void interprete_show_legend_graph() throws Exception {
        String script = "show legend graph with style 'color=red';";
        Options options = PgLangInterpreter.interprete(script);

        assertEquals("red", options.legendGraphStyleAttributes().get("color"));
    }

    @Test
    void interprete_export_into() throws Exception {
        String script = "export as 'png' into 'myfile.png' by overwriting;";
        Options options = PgLangInterpreter.interprete(script);

        assertEquals("png", options.exportInto().fileType());
        assertEquals("myfile.png", options.exportInto().filePath());
        assertTrue(options.exportInto().overwrite());
    }

    @Test
    void ignore_comments() throws Exception {
        String script = """
                //this is a comment
                include source directory /* this is another */ 'src/main/java';
                """;
        Options options = PgLangInterpreter.interprete(script);

        assertEquals(1, options.sourceDirectories().size());
        assertEquals("src/main/java", options.sourceDirectories().getFirst());
    }

    @Test
    void throw_syntax_error_as_exception() throws Exception {
        String script = "blabla;";
        assertThrows(Exception.class, () -> PgLangInterpreter.interprete(script));
    }
}