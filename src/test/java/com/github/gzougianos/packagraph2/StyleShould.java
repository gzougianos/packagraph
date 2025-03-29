package com.github.gzougianos.packagraph2;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StyleShould {

    @Test
    void resolve_properties_ignoring_white_spaces() {
        Style style = new Style("fillcolor = green;shape = oval", List.of());

        assertEquals(2, style.values().size());
        assertEquals("green", style.values().get("fillcolor"));
        assertEquals("oval", style.values().get("shape"));
    }

    @Test
    void resolve_properties_with_constants() {
        Style style = new Style("shape = ${SHAPE_OVAL}",
                List.of(new Options.DefineConstant("SHAPE_OVAL", "oval")));

        assertEquals(1, style.values().size());
        assertEquals("oval", style.values().get("shape"));
    }

    @Test
    void let_value_pass_if_constant_does_not_exist() {
        Style style = new Style("shape = ${SHAPE_OVAL}", List.of());

        assertEquals(1, style.values().size());
        assertEquals("SHAPE_OVAL", style.values().get("shape"));
    }
}