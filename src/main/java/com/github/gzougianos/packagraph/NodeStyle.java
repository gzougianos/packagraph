package com.github.gzougianos.packagraph;

import java.util.Objects;

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

    public NodeStyle inheritGlobal(NodeStyle global) {
        if (Objects.equals(Boolean.FALSE, inheritGlobal))
            return this;

        var shape = this.shape == null ? global.shape : this.shape;
        var style = this.style == null ? global.style : this.style;
        var fillcolor = this.fillcolor == null ? global.fillcolor : this.fillcolor;
        var color = this.color == null ? global.color : this.color;
        var fontcolor = this.fontcolor == null ? global.fontcolor : this.fontcolor;
        var fontsize = this.fontsize == null ? global.fontsize : this.fontsize;
        var fontname = this.fontname == null ? global.fontname : this.fontname;
        var width = this.width == null ? global.width : this.width;
        var height = this.height == null ? global.height : this.height;
        var fixedsize = this.fixedsize == null ? global.fixedsize : this.fixedsize;
        var tooltip = this.tooltip == null ? global.tooltip : this.tooltip;
        var url = this.url == null ? global.url : this.url;
        var target = this.target == null ? global.target : this.target;
        var layer = this.layer == null ? global.layer : this.layer;
        var group = this.group == null ? global.group : this.group;
        var rank = this.rank == null ? global.rank : this.rank;
        var sides = this.sides == null ? global.sides : this.sides;
        var peripheries = this.peripheries == null ? global.peripheries : this.peripheries;

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
}
