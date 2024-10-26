package com.github.gzougianos.packagraph;

public record EdgeStyle(
        // Text label on the edge
        String label,

        // Color of the edge, e.g., "red" or "#FF0000"
        String color,

        // Defines the line style of the edge: "solid", "dashed", "dotted", "bold", "invis"
        String style,

        // Affects layout; edges with higher weights tend to be shorter
        Integer weight,

        // Specifies the thickness of the edge line
        Double penwidth,

        // Shape of the arrowhead on the end of the edge: "normal", "inv", "dot", "diamond", "none"
        String arrowhead,

        // Scales the size of the arrowhead
        Double arrowsize,

        // Direction of the edge: "forward", "back", "both", "none"
        String dir,

        // Determines if the edge should influence layout constraints: true or false
        Boolean constraint,

        // Font size of the edge label
        Integer fontsize,

        // Color of the edge label
        String fontcolor,

        // Draws a line connecting the label to the edge if set to true
        Boolean decorate,

        // Hyperlink attached to the edge (useful for interactive formats like SVG)
        String url
) {
    public static final EdgeStyle DEFAULT = new EdgeStyle(
            null, null, null, null, null, null, null, null, null, null, null, null, null
    );

    public EdgeStyle {

    }
}