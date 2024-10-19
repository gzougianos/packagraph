package com.github.gzougianos.packagraph.analysis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

@Accessors(fluent = true)
@ToString
@Getter
public final class JavaClass {
    public static final JavaParser JAVA_PARSER = new JavaParser();
    private final File sourceFile;
    private final Collection<Import> imports;
    private final Package packag;

    private JavaClass(File sourceFile) throws IOException {
        this.sourceFile = sourceFile;

        final var compilationUnit = parse(sourceFile);
        this.imports = findImports(compilationUnit);
        this.packag = findPackage(compilationUnit);
    }

    private Package findPackage(CompilationUnit compilationUnit) {
        return compilationUnit.getPackageDeclaration()
                .map(PackageDeclaration::getNameAsString)
                .map(Package::new)
                .orElse(Package.ROOT);
    }

    private static CompilationUnit parse(File sourceFile) throws FileNotFoundException {
        var parseResult = JAVA_PARSER.parse(sourceFile);
        if (!parseResult.getProblems().isEmpty()) {
            throw new ClassAnalysisFailedException("Failed to parse file: " + sourceFile.getAbsolutePath() + "." +
                    "It seems like the class is not compilable.");
        }

        return parseResult
                .getResult()
                .orElseThrow(() -> new ClassAnalysisFailedException("Failed to parse file: " + sourceFile.getAbsolutePath()));
    }

    private Collection<Import> findImports(CompilationUnit unit) {
        return unit.getImports().stream()
                .map(x -> new Import(x.getNameAsString(), x.isStatic(), x.isAsterisk()))
                .toList();
    }

    public static JavaClass of(File sourceFile) throws ClassAnalysisFailedException {
        try {
            return new JavaClass(sourceFile);
        } catch (IOException e) {
            throw new ClassAnalysisFailedException("Failed to analyze class: " + sourceFile.getAbsolutePath(), e);
        }
    }
}
