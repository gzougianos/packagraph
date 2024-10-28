package com.github.gzougianos.packagraph.style;

/**
 * Record representing Graphviz graph-level attributes with descriptions of accepted values.
 */
public record GraphStyle(
        /*
         * Adjusts the damping factor in the layout algorithm. Higher values spread nodes further apart.
         * Default is 0.99.
         */
        String damping,

        /*
         * Ideal edge length in layouts. Only applies to layouts using engines like "neato" and "fdp".
         * This should be a floating-point number.
         */
        String k,

        /*
         * Sets a hyperlink for the graph, often used in SVG or image map output.
         */
        String url,

        /*
         * Path to a background image to display in the graph.
         */
        String background,

        /*
         * Defines the bounding box for the layout. Format: "x0,y0,x1,y1".
         */
        String bb,

        /*
         * Background color for the graph. Accepts color names or hexadecimal values (e.g., "#FFFFFF" for white).
         */
        String bgcolor,

        /*
         * Centers the graph within the canvas if set to true.
         */
        String center,

        /*
         * Specifies the character encoding to use in output files (e.g., "UTF-8").
         */
        String charset,

        /*
         * Controls how clusters are organized. Accepted values: "local", "global", or "none".
         */
        String clusterrank,

        /*
         * Sets the default color for nodes and edges. Accepts color names or hexadecimal values.
         */
        String color,

        /*
         * Defines a color scheme for use in other color attributes (e.g., "set19").
         */
        String colorscheme,

        /*
         * Adds a comment for metadata purposes.
         */
        String comment,

        /*
         * Enables edges between clusters if set to true.
         */
        String compound,

        /*
         * Merges parallel edges between nodes if set to true.
         */
        String concentrate,

        /*
         * Sets the DPI (dots per inch) for output images. Higher DPI results in a more detailed image.
         */
        String dpi,

        /*
         * Minimum convergence tolerance for layout (affects "neato" and "fdp" engines).
         */
        String epsilon,

        /*
         * Specifies extra space for loops and parallel edges. Format: integer or "x,y".
         */
        String esep,

        /*
         * Default color for text within the graph.
         */
        String fontcolor,

        /*
         * Default font family for text within the graph (e.g., "Helvetica").
         */
        String fontname,

        /*
         * Directory path to locate fonts used in the graph.
         */
        String fontpath,

        /*
         * Default font size for text within the graph, in points.
         */
        String fontsize,

        /*
         * Unique identifier for the graph, used in web-based output formats.
         */
        String id,

        /*
         * Specifies a label for the graph, typically used as a title.
         */
        String label,

        /*
         * Horizontal justification of the label. Accepted values: "c" (center), "l" (left), "r" (right).
         */
        String labeljust,

        /*
         * Vertical positioning of the label. Accepted values: "t" (top) or "b" (bottom).
         */
        String labelloc,

        /*
         * Rotates the graph layout by 90 degrees if set to true.
         */
        String landscape,

        /*
         * Allows multiple layers within the graph.
         */
        String layers,

        /*
         * Separator character for layer names, default is a space.
         */
        String layersep,

        /*
         * Sets the layout engine to use. Common values: "dot", "neato", "fdp", etc.
         */
        String layout,

        /*
         * Margin around the graph layout, in points.
         */
        String margin,

        /*
         * Maximum number of iterations for convergence in layouts.
         */
        String maxiter,

        /*
         * Minimum cost limit for edge crossing minimization.
         */
        String mclimit,

        /*
         * Minimum distance between nodes, in points.
         */
        String mindist,

        /*
         * Controls layout mode, with values like "ipsep" or "spring" for specific engines.
         */
        String mode,

        /*
         * Distance model used by "neato". Options include "shortpath", "circuit", etc.
         */
        String model,

        /*
         * Layout direction. Accepted values: "TB" (top-to-bottom), "LR" (left-to-right).
         */
        String rankdir,

        /*
         * Space between ranks (vertical separation).
         */
        String ranksep,

        /*
         * Aspect ratio for the layout. Options include "fill", "compress", "expand", or a numeric ratio.
         */
        String ratio,

        /*
         * Minimizes edge crossings in the graph if set to true.
         */
        String remincross,

        /*
         * Rotates the graph layout by the specified number of degrees.
         */
        String rotate,

        /*
         * Scales the layout to fit the specified page size.
         */
        String scale,

        /*
         * Limits the search size for edges during layout.
         */
        String searchsize,

        /*
         * Extra space around nodes to prevent overlap. Format: integer or "x,y".
         */
        String sep,

        /*
         * If set, edges will be smooth or curved. Options include "polyline", "ortho", or "curved".
         */
        String splines,

        /*
         * URL to a stylesheet to apply to the graph.
         */
        String stylesheet,

        /*
         * Target window for links. Options include "_blank", "_self", etc.
         */
        String target,

        /*
         * Tooltip text for the graph.
         */
        String tooltip,

        /*
         * Enables truecolor rendering for high color quality.
         */
        String truecolor,

        /*
         * Controls the viewport scaling and panning of the graph.
         */
        String viewport,

        /*
         * Minimum required "xdot" version for enhanced features.
         */
        String xdotversion
) {
    /**
     * Default values for Graphviz graph attributes.
     */
    public static final GraphStyle DEFAULT = new GraphStyle(
            null, // damping
            null, // K
            null, // URL
            null, // background
            null, // bb
            null, // bgcolor
            null, // center
            null, // charset
            null, // clusterrank
            null, // color
            null, // colorscheme
            null, // comment
            null, // compound
            null, // concentrate
            null, // dpi
            null, // epsilon
            null, // esep
            null, // fontcolor
            null, // fontname
            null, // fontpath
            null, // fontsize
            null, // id
            null, // label
            null, // labeljust
            null, // labelloc
            null, // landscape
            null, // layers
            null, // layersep
            null, // layout
            null, // margin
            null, // maxiter
            null, // mclimit
            null, // mindist
            null, // mode
            null, // model
            null, // rankdir
            null, // ranksep
            null, // ratio
            null, // remincross
            null, // rotate
            null, // scale
            null, // searchsize
            null, // sep
            null, // splines
            null, // stylesheet
            null, // target
            null, // tooltip
            null, // truecolor
            null, // viewport
            null  // xdotversion
    );

}
