package com.github.gzougianos.packagraph.analysis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

@Accessors(fluent = true)
@ToString
@Getter
@Slf4j
public final class JavaClass {
    public static final JavaParser JAVA_PARSER;

    static {
        final ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21);

        JAVA_PARSER = new JavaParser(parserConfiguration);
    }

    private final File sourceFile;
    private final Collection<PackageName> imports;
    private final PackageName packag;

    private JavaClass(File sourceFile) throws IOException {
        this.sourceFile = sourceFile;

        final var compilationUnit = parse(sourceFile);
        this.imports = findImports(compilationUnit);
        this.packag = findPackage(compilationUnit);
    }

    private PackageName findPackage(CompilationUnit compilationUnit) {
        return compilationUnit.getPackageDeclaration()
                .map(PackageDeclaration::getNameAsString)
                .map(PackageName::new)
                .orElse(PackageName.ROOT);
    }

    private static CompilationUnit parse(File sourceFile) throws FileNotFoundException {
        var parseResult = JAVA_PARSER.parse(sourceFile);
        if (!parseResult.getProblems().isEmpty()) {
            for (var problem : parseResult.getProblems()) {
                log.warn("{}: {}", sourceFile.getName(), problem.getMessage());
            }
            throw new ClassAnalysisFailedException("Failed to parse file: " + sourceFile.getAbsolutePath() + ".");
        }

        return parseResult
                .getResult()
                .orElseThrow(() -> new ClassAnalysisFailedException("Failed to parse file: " + sourceFile.getAbsolutePath()));
    }

    private Collection<PackageName> findImports(CompilationUnit unit) {
        return unit.getImports().stream()
                .map(JavaClass::adaptImport)
                .toList();
    }

    private static PackageName adaptImport(ImportDeclaration importt) {
        final var importName = importt.getNameAsString();

        //import java.io.*, library gives: java.io
        if (!importt.isStatic() && importt.isAsterisk()) {
            return new PackageName(importName);
        }

        //import java.util.HashMap, library gives: java.util.HashMap
        //So need to trim className
        if (!importt.isStatic() && !importt.isAsterisk()) {
            return new PackageName(trimUpToLastDot(importName));
        }

        //import static java.lang.System.setErr, library gives: java.lang.System.setErr
        //So need to trim method name + class name
        if (importt.isStatic() && !importt.isAsterisk()) {
            return new PackageName(trimUpToLastDot(trimUpToLastDot(importName)));
        }

        //import static javax.swing.SwingUtilities.*, library gives: javax.swing.SwingUtilities
        //So need to trim class name
        return new PackageName(trimUpToLastDot(importName));
    }

    private static String trimUpToLastDot(String str) {
        if (!str.contains("."))
            return str;

        return str.substring(0, str.lastIndexOf('.'));
    }

    public static JavaClass of(File sourceFile) throws ClassAnalysisFailedException {
        try {
            return new JavaClass(sourceFile);
        } catch (IOException e) {
            throw new ClassAnalysisFailedException("Failed to analyze class: " + sourceFile.getAbsolutePath(), e);
        }
    }
}
