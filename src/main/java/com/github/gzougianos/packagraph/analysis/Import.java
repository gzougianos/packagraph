package com.github.gzougianos.packagraph.analysis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(fluent = true)
@Getter
@ToString
public final class Import {
    private final String value;
    private final boolean isStatic;
    private final boolean isAsterisk;

    public Package packag() {
        String packagName;
        if (isStatic) {
            // Handle static imports, removing the class and method/field part
            var withoutClassOrMember = value.substring(0, value.lastIndexOf('.')); // Remove method or wildcard
            packagName = withoutClassOrMember.substring(0, withoutClassOrMember.lastIndexOf('.')); // Remove class name
        } else {
            // Regular import: return everything before the class name
            packagName = value.substring(0, value.lastIndexOf('.'));
        }
        return new Package(packagName);
    }
}
