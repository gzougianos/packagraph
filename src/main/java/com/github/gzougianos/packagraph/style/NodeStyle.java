package com.github.gzougianos.packagraph.style;

public record NodeStyle(
        String shape,
        String style,
        String fillcolor,
        String color,
        String fontcolor,
        Integer fontsize,
        String fontname,
        Double width,
        Double height,
        Boolean fixedsize,
        String tooltip,
        String url,
        String target,
        Integer layer,
        String group,
        String rank,
        Integer sides,
        Integer peripheries,
        Boolean inheritGlobal
) {
    public static NodeStyle DEFAULT = new NodeStyle(
            null,
            null,
            null,
            null,
            null,
            0,
            null,
            0d,
            0d,
            false,
            null,
            null,
            null,
            0,
            null,
            null,
            0,
            0,
            true);

    public NodeStyle inheritFrom(NodeStyle otherStyle) {
        if (inheritGlobalIsExplicitlyDisabled())
            return this;

        var shape = this.shape == null ? otherStyle.shape : this.shape;
        var style = this.style == null ? otherStyle.style : this.style;
        var fillcolor = this.fillcolor == null ? otherStyle.fillcolor : this.fillcolor;
        var color = this.color == null ? otherStyle.color : this.color;
        var fontcolor = this.fontcolor == null ? otherStyle.fontcolor : this.fontcolor;
        var fontsize = this.fontsize == null ? otherStyle.fontsize : this.fontsize;
        var fontname = this.fontname == null ? otherStyle.fontname : this.fontname;
        var width = this.width == null ? otherStyle.width : this.width;
        var height = this.height == null ? otherStyle.height : this.height;
        var fixedsize = this.fixedsize == null ? otherStyle.fixedsize : this.fixedsize;
        var tooltip = this.tooltip == null ? otherStyle.tooltip : this.tooltip;
        var url = this.url == null ? otherStyle.url : this.url;
        var target = this.target == null ? otherStyle.target : this.target;
        var layer = this.layer == null ? otherStyle.layer : this.layer;
        var group = this.group == null ? otherStyle.group : this.group;
        var rank = this.rank == null ? otherStyle.rank : this.rank;
        var sides = this.sides == null ? otherStyle.sides : this.sides;
        var peripheries = this.peripheries == null ? otherStyle.peripheries : this.peripheries;

        return new NodeStyle(
                shape,
                style,
                fillcolor,
                color,
                fontcolor,
                fontsize,
                fontname,
                width,
                height,
                fixedsize,
                tooltip,
                url,
                target,
                layer,
                group,
                rank,
                sides,
                peripheries,
                false);
    }

    private boolean inheritGlobalIsExplicitlyDisabled() {
        return Boolean.FALSE.equals(this.inheritGlobal);
    }
}
