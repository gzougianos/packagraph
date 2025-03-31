package com.github.gzougianos.packagraph;

import com.github.gzougianos.packagraph2.analysis.ClassAnalysisFailedException;
import com.github.gzougianos.packagraph2.analysis.JavaClass;
import com.github.gzougianos.packagraph2.analysis.PackageName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Packagraph {
    private final PackagraphOptions options;

    public static Collection<PackageNode> create(PackagraphOptions options) {
        return new Packagraph(options).createNodes();
    }

    private Collection<PackageNode> createNodes() {
        final var allFiles = JavaFilesFinder.findWithin(options.directories());
        final var analyzed = allFiles.stream()
                .map(Packagraph::asClass)
                .filter(Objects::nonNull)
                .toList();

        final var internalPackages = analyzed.stream()
                .map(JavaClass::packag)
                .map(this::rename)
                .filter(Packagraph::isIncluded)
                .collect(Collectors.toSet());


        final var dependencies = findDependencies(analyzed, internalPackages);

        final Set<PackageName> allPackages = combinePackages(internalPackages, dependencies);

        final Map<PackageName, PackageNode> allNodes = allPackages.stream()
                .map(this::createPackageNodeFor)
                .collect(Collectors.toMap(PackageNode::packag, Function.identity()));


        dependencies.forEach(dependency -> allNodes.get(dependency.from()).dependOn(allNodes.get(dependency.to())));

        return allNodes.values();
    }

    private PackageNode createPackageNodeFor(PackageName packag) {
        return new PackageNode(packag, options.clusterOf(packag).orElse(null));
    }

    private static Set<PackageName> combinePackages(Set<PackageName> internalPackages, HashSet<Dependency> dependencies) {
        HashSet<PackageName> packages = new HashSet<>(internalPackages);

        for (var dependency : dependencies) {
            packages.add(dependency.to());
            packages.add(dependency.from());
        }

        return packages;
    }

    private PackageName rename(PackageName packag) {
        return options.rename(packag);
    }

    private HashSet<Dependency> findDependencies(List<JavaClass> analyzedClasses, Set<PackageName> internalPackages) {
        final HashSet<Dependency> dependencies = new HashSet<>();
        analyzedClasses.forEach(javaClass -> {
            var renamedFromPackage = rename(javaClass.packag());

            for (var declaredImport : javaClass.imports()) {
                var renamedImport = rename(declaredImport);

                if (options.includeOnlyFromDirectories() && !internalPackages.contains(renamedImport)) {
                    continue;
                }
                if (!isIncluded(renamedImport) || !isIncluded(renamedFromPackage)) {
                    continue;
                }

                dependencies.add(new Dependency(renamedFromPackage, renamedImport));
            }
        });
        return dependencies;
    }

    private static boolean isIncluded(PackageName packag) {
        return !packag.name().trim().isEmpty();
    }

    private record Dependency(PackageName from, PackageName to) {
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