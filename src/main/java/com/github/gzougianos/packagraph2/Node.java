package com.github.gzougianos.packagraph2;

import com.github.gzougianos.packagraph.analysis.*;
import lombok.*;
import lombok.experimental.*;

public record Node(PackageName packag, boolean isInternal) {

}
