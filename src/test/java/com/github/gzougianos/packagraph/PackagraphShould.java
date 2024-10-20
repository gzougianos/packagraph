package com.github.gzougianos.packagraph;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackagraphShould {

    public static final File FOR_GRAPH_CREATION = new File(ResourcesFolder.asFile(), "forGraphCreation");

    @Test
    void create_nodes_for_simple_dependencies() {
        var simpleProject = new File(FOR_GRAPH_CREATION, "simple");

        PackagraphOptions options = PackagraphOptions.builder()
                .directories(new String[]{simpleProject.getAbsolutePath()})
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(2, packageNodes.size());

        var dependant = findUniqueNode("simple", packageNodes);
        var dependency = findUniqueNode("simple.dependency", packageNodes);

        assertTrue(dependant.dependsOn(dependency));
    }

    @Test
    void create_nodes_for_external_dependencies() {
        var project = new File(FOR_GRAPH_CREATION, "externaldependency");

        PackagraphOptions options = PackagraphOptions.builder()
                .directories(new String[]{project.getAbsolutePath()})
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(3, packageNodes.size());

        var dependent = findUniqueNode("externaldependency", packageNodes);
        var dependency = findUniqueNode("externaldependency.dependency", packageNodes);
        var java = findUniqueNode("java.util", packageNodes);

        assertTrue(dependent.dependsOn(java));
        assertTrue(dependent.dependsOn(dependency));
    }

    private static PackageNode findUniqueNode(String packageName, Collection<PackageNode> nodes) {
        List<PackageNode> foundNodes = nodes.stream().filter(p -> p.packag().name().equals(packageName)).toList();
        if (foundNodes.size() != 1) {
            throw new RuntimeException("Found " + foundNodes.size() + " nodes for package " + packageName);
        }
        return foundNodes.getFirst();
    }
}