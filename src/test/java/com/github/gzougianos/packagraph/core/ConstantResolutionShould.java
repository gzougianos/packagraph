package com.github.gzougianos.packagraph.core;

import com.github.gzougianos.packagraph.antlr4.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ConstantResolutionShould {

    private Options run(String script) throws Exception {
        return PgLangInterpreter.interprete(script);
    }

    @Test
    void resolve_properties() throws Exception {
        var script = """
                show maingraph with style 'myStyle';
                define style 'myStyle' as 'fillcolor=green;shape=oval';
                """;

        Options options = run(script);
        var attributes = options.mainGraphStyleAttributes();

        assertEquals(2, attributes.size());
        assertEquals("green", attributes.get("fillcolor"));
        assertEquals("oval", attributes.get("shape"));
    }

    @Test
    void resolve_properties_ignoring_white_spaces() throws Exception {
        var script = """
                show maingraph with style 'myStyle';
                define style 'myStyle' as 'fillcolor = green;shape = oval';
                """;

        Options options = run(script);
        var attributes = options.mainGraphStyleAttributes();

        assertEquals(2, attributes.size());
        assertEquals("green", attributes.get("fillcolor"));
        assertEquals("oval", attributes.get("shape"));
    }


    @Test
    void resolve_properties_with_constants() throws Exception {
        var script = """
                show maingraph with style 'myStyle';
                define style 'myStyle' as 'fillcolor = ${GREEN_CLR};shape = oval';
                define constant 'GREEN_CLR' as 'green';
                """;

        Options options = run(script);
        var attributes = options.mainGraphStyleAttributes();

        assertEquals(2, attributes.size());
        assertEquals("green", attributes.get("fillcolor"));
        assertEquals("oval", attributes.get("shape"));
    }

    @Test
    void let_value_pass_if_constant_does_not_exist() throws Exception {
        var script = """
                show maingraph with style 'myStyle';
                define style 'myStyle' as 'fillcolor = ${GREEN_CLR};shape = oval';
                """;

        Options options = run(script);
        var attributes = options.mainGraphStyleAttributes();

        assertEquals(2, attributes.size());
        assertEquals("GREEN_CLR", attributes.get("fillcolor"));
        assertEquals("oval", attributes.get("shape"));
    }
}