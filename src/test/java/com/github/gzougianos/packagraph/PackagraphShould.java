package com.github.gzougianos.packagraph;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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
        var options = PackagraphOptions.fromJson("""
                {
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("simple")));

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
        var options = PackagraphOptions.fromJson("""
                {
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("circular")));


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
        var options = PackagraphOptions.fromJson("""
                {
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("externaldependency")));

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
        var options = PackagraphOptions.fromJson("""
                {
                    "includeOnlyFromDirectories": true,
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("externaldependency")));

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
        var options = PackagraphOptions.fromJson("""
                {
                    "includeOnlyFromDirectories": false,
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "definitions": [
                      {
                        "packages": "java.*",
                        "as": "Java"
                      },
                      {
                        "packages": "renamings.*",
                        "as": "Renamings"
                      }
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("renamings")));

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(3, packageNodes.size());

        var java = findUniqueNode("Java", packageNodes);
        var renamings = findUniqueNode("Renamings", packageNodes);
        var toBeExcludedInAnotherTestCase = findUniqueNode("assume.something.to.be.excluded", packageNodes);
        assertTrue(renamings.dependsOn(java));
        assertTrue(renamings.dependsOn(toBeExcludedInAnotherTestCase));
    }

    // +------------+
    // | Java  |
    // +------------+
    @Test
    void merge_packages_based_on_names() {
        var options = PackagraphOptions.fromJson("""
                {
                    "includeOnlyFromDirectories": false,
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "definitions": [
                      {
                        "packages": "java.*",
                        "as": "Java"
                      },
                      {
                        "packages": "assume.something.to.be.excluded",
                        "as": "Java"
                      },
                      {
                        "packages": "renamings.*",
                        "as": "Java"
                      }
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("renamings")));

        Collection<PackageNode> packageNodes = Packagraph.create(options);
        assertEquals(1, packageNodes.size());

        findUniqueNode("Java", packageNodes);
    }

    //+------------+                 +------------+
    //| Renamings  |  ------------->  |   Java     |
    //+------------+                 +------------+
    @Test
    void exclude_packages_with_empty_string_renaming() {
        var options = PackagraphOptions.fromJson("""
                {
                    "includeOnlyFromDirectories": false,
                    "directories": [
                      "%PROJECT_DIR%"
                    ],
                    "definitions": [
                      {
                        "packages": "java.*",
                        "as": "Java"
                      },
                      {
                        "packages": "assume.something.to.be.excluded",
                        "as": ""
                      },
                      {
                        "packages": "renamings.*",
                        "as": "Renamings"
                      }
                    ],
                    "outputImage": {
                        "path": "target/packagraph.png",
                        "overwrite": true
                    }
                }
                """.replace("%PROJECT_DIR%", projectFolder("renamings")));

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

    private static String projectFolder(String name) {
        return new File(FOR_GRAPH_CREATION, name).getPath().replace('\\', '/');
    }
}