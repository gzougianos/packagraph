package com.github.gzougianos.packagraph.analysis;

import com.github.gzougianos.packagraph.ResourcesFolder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JavaClassShould {
    private static final File RESOURCES_FOLDER = new File(ResourcesFolder.asFile(), "forClassAnalysis");
    private static final File CLASS_FILE_TO_ANALYZE = new File(RESOURCES_FOLDER, "ClassToBeAnalyzed.java");
    private static final File NON_COMPILABLE_CLASS = new File(RESOURCES_FOLDER, "NonCompilable.java");

    @Test
    void know_package() {
        JavaClass javaClass = JavaClass.of(CLASS_FILE_TO_ANALYZE);

        assertEquals("testing", javaClass.packag().name());
    }

    @Test
    void know_imports() {
        JavaClass javaClass = JavaClass.of(CLASS_FILE_TO_ANALYZE);

        Collection<PackageName> imports = javaClass.imports();

        assertEquals(4, imports.size());

        assertImport("java.io", imports);
        assertImport("java.util", imports);
        assertImport("java.lang", imports);
        assertImport("javax.swing", imports);
    }

    @Test
    void throw_exception_if_class_is_not_compilable() {
        assertThrows(ClassAnalysisFailedException.class, () -> JavaClass.of(NON_COMPILABLE_CLASS));
    }

    private static void assertImport(String name, Collection<PackageName> imports) {
        imports.stream().filter(i -> i.name().equals(name)).findFirst().orElseThrow();
    }
}