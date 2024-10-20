package com.github.gzougianos.packagraph;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackagraphShould {

    public static final File FOR_GRAPH_CREATION = new File(ResourcesFolder.asFile(), "forGraphCreation");

    // +-------------+                 +-------------+
    // |  Dependant  |  ------------>  |  Dependency |
    // +-------------+                 +-------------+
    @Test
    void create_nodes_for_simple_dependencies() {
        PackagraphOptions options = PackagraphOptions.builder()
                .directories(projectFolder("simple"))
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(2, packageNodes.size());

        var dependant = findUniqueNode("simple", packageNodes);
        var dependency = findUniqueNode("simple.dependency", packageNodes);

        assertTrue(dependant.dependsOn(dependency));
    }

    //+-------------+                 +-------------+
    //|  Dependant  |  ------------->  |  Dependency |
    //+-------------+                 +-------------+
    //       ^                              |
    //       |------------------------------|
    @Test
    void understand_circular_dependencies() {
        PackagraphOptions options = PackagraphOptions.builder()
                .directories(projectFolder("circular"))
                .build();


        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(2, packageNodes.size());

        var dependant = findUniqueNode("circular", packageNodes);
        var dependency = findUniqueNode("circular.dependency", packageNodes);

        assertTrue(dependant.dependsOn(dependency));
        assertTrue(dependency.dependsOn(dependant));
    }

    // +-------------+                 +-------------+
    // |  Dependant  |  ------------>  |  Dependency |
    // +-------------+                 +-------------+
    //        |
    //        |
    //        v
    //  +-------------+
    //  |    Java     |
    //  +-------------+
    @Test
    void create_nodes_for_external_dependencies() {
        PackagraphOptions options = PackagraphOptions.builder()
                .directories(projectFolder("externaldependency"))
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(3, packageNodes.size());

        var dependent = findUniqueNode("externaldependency", packageNodes);
        var dependency = findUniqueNode("externaldependency.dependency", packageNodes);
        var java = findUniqueNode("java.util", packageNodes);

        assertTrue(dependent.dependsOn(java));
        assertTrue(dependent.dependsOn(dependency));
    }

    //+-------------+                 +-------------+
    //|  Dependant  |  ------------->  |  Dependency |
    //+-------------+                 +-------------+
    @Test
    void exclude_dependencies_from_packages_outside_the_directories() {
        PackagraphOptions options = PackagraphOptions.builder()
                .directories(projectFolder("externaldependency"))
                .includeOnlyFromDirectories(true)
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(2, packageNodes.size());

        var dependent = findUniqueNode("externaldependency", packageNodes);
        var dependency = findUniqueNode("externaldependency.dependency", packageNodes);

        assertTrue(dependent.dependsOn(dependency));
    }

    // +------------+                 +------------+
    // | Renamings  |  ------------>  |   Java     |
    // +------------+                 +------------+
    //        |
    //        |
    //        v
    //  +--------------+
    //  | ToBeExcluded | //in another test case
    //  +--------------+
    @Test
    void rename_packages() {
        PackagraphOptions options = PackagraphOptions.builder()
                .directories(projectFolder("renamings"))
                .renamings(List.of(
                        new PackagraphOptions.Rename("java.*", "Java"),
                        new PackagraphOptions.Rename("renamings.*", "Renamings")))
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(3, packageNodes.size());

        var java = findUniqueNode("Java", packageNodes);
        var renamings = findUniqueNode("Renamings", packageNodes);
        var toBeExcludedInAnotherTestCase = findUniqueNode("assume.something.to.be.excluded", packageNodes);
        assertTrue(renamings.dependsOn(java));
        assertTrue(renamings.dependsOn(toBeExcludedInAnotherTestCase));
    }

    //+------------+                 +------------+
    //| Renamings  |  ------------->  |   Java     |
    //+------------+                 +------------+
    @Test
    void exclude_packages_with_empty_string_renaming() {
        PackagraphOptions options = PackagraphOptions.builder()
                .directories(projectFolder("renamings"))
                .renamings(List.of(
                        new PackagraphOptions.Rename("java.*", "Java"),
                        new PackagraphOptions.Rename("assume.something.to.be.excluded.*", ""),
                        new PackagraphOptions.Rename("renamings.*", "Renamings")))
                .build();

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(2, packageNodes.size());

        var java = findUniqueNode("Java", packageNodes);
        var renamings = findUniqueNode("Renamings", packageNodes);
        assertTrue(renamings.dependsOn(java));
    }

    private static PackageNode findUniqueNode(String packageName, Collection<PackageNode> nodes) {
        List<PackageNode> foundNodes = nodes.stream().filter(p -> p.packag().name().equals(packageName)).toList();
        if (foundNodes.size() != 1) {
            throw new RuntimeException("Found " + foundNodes.size() + " nodes for package " + packageName);
        }
        return foundNodes.getFirst();
    }

    private static String[] projectFolder(String name) {
        return new String[]{new File(FOR_GRAPH_CREATION, name).getAbsolutePath()};
    }
}