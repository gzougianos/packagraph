package com.github.gzougianos.packagraph2.analysis;

public class ClassAnalysisFailedException extends RuntimeException {
    ClassAnalysisFailedException(String message) {
        super(message);
    }

    ClassAnalysisFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
