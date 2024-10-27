package com.github.gzougianos.packagraph.style;

import java.util.Objects;

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
        String url,

        Boolean inheritGlobal
) {
    public static final EdgeStyle DEFAULT = new EdgeStyle(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            true
    );

    public EdgeStyle inheritFrom(EdgeStyle edgeStyle) {
        if (Objects.equals(Boolean.FALSE, this.inheritGlobal))
            return this;

        var label = this.label == null ? edgeStyle.label : this.label;
        var color = this.color == null ? edgeStyle.color : this.color;
        var style = this.style == null ? edgeStyle.style : this.style;
        var weight = this.weight == null ? edgeStyle.weight : this.weight;
        var penwidth = this.penwidth == null ? edgeStyle.penwidth : this.penwidth;
        var arrowhead = this.arrowhead == null ? edgeStyle.arrowhead : this.arrowhead;
        var arrowsize = this.arrowsize == null ? edgeStyle.arrowsize : this.arrowsize;
        var dir = this.dir == null ? edgeStyle.dir : this.dir;
        var constraint = this.constraint == null ? edgeStyle.constraint : this.constraint;
        var fontsize = this.fontsize == null ? edgeStyle.fontsize : this.fontsize;
        var fontcolor = this.fontcolor == null ? edgeStyle.fontcolor : this.fontcolor;
        var decorate = this.decorate == null ? edgeStyle.decorate : this.decorate;
        var url = this.url == null ? edgeStyle.url : this.url;
        return new EdgeStyle(label, color, style, weight, penwidth, arrowhead, arrowsize,
                dir, constraint, fontsize, fontcolor, decorate, url, false);
    }
}