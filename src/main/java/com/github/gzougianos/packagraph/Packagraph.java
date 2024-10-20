package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph.analysis.ClassAnalysisFailedException;
import com.github.gzougianos.packagraph.analysis.JavaClass;
import com.github.gzougianos.packagraph.analysis.Package;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
public class Packagraph {

    private Packagraph(PackagraphOptions options) {
    }

    public static Collection<PackageNode> create(PackagraphOptions options) {
        final var allFiles = JavaFilesFinder.findWithin(options.directories());
        final var analyzed = allFiles.stream()
                .map(Packagraph::asClass)
                .filter(Objects::nonNull)
                .toList();

        final var internalPackages = analyzed.stream()
                .map(JavaClass::packag)
                .collect(Collectors.toSet());


        final var dependencies = findDependencies(analyzed);

        final Set<Package> allPackages = combinePackages(internalPackages, dependencies);
        final Map<Package, PackageNode> allNodes = allPackages.stream()
                .map(PackageNode::new)
                .collect(Collectors.toMap(PackageNode::packag, Function.identity()));


        dependencies.forEach(dependency -> allNodes.get(dependency.from()).dependOn(allNodes.get(dependency.to())));


        return allNodes.values();

    }

    private static Set<Package> combinePackages(Set<Package> internalPackages, HashSet<Dependency> dependencies) {
        HashSet<Package> packages = new HashSet<>(internalPackages);

        for (var dependency : dependencies) {
            packages.add(dependency.to());
            packages.add(dependency.from());
        }

        return packages;
    }

    private static HashSet<Dependency> findDependencies(List<JavaClass> analyzed) {
        final HashSet<Dependency> dependencies = new HashSet<>();
        analyzed.forEach(javaClass -> {
            var fromPackage = javaClass.packag();
            for (var declaredImport : javaClass.imports()) {
                dependencies.add(new Dependency(fromPackage, declaredImport));
            }
        });
        return dependencies;
    }

    public record Dependency(Package from, Package to) {
    }
    
    private static JavaClass asClass(File javaFile) {
        try {
            return JavaClass.of(javaFile);
        } catch (ClassAnalysisFailedException ex) {
            log.warn("Failed to analyze {}. Message: {}", javaFile.getAbsolutePath(), ex.getMessage());
        }
        return null;
    }
}