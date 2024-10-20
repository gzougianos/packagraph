package com.github.gzougianos.packagraph.analysis;

import com.github.gzougianos.packagraph.ResourcesFolder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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

        Collection<Package> imports = javaClass.imports();

        assertEquals(3, imports.size());

        var javaIo = getImport("java.io", imports);
        assertEquals("java.io", javaIo.name());

        var staticMethod = getImport("java.lang.System", imports);
        assertEquals("java.lang.System", staticMethod.name());

        var hashMapImport = getImport("java.util", imports);
        assertEquals("java.util", hashMapImport.name());
    }

    @Test
    void throw_exception_if_class_is_not_compilable() {
        assertThrows(ClassAnalysisFailedException.class, () -> JavaClass.of(NON_COMPILABLE_CLASS));
    }

    private static Package getImport(String name, Collection<Package> imports) {
        return imports.stream().filter(i -> i.name().equals(name)).findFirst().orElseThrow();
    }
}