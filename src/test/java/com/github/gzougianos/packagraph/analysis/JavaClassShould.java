package com.github.gzougianos.packagraph.analysis;

import com.github.gzougianos.packagraph.ResourcesFolder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassShould {
    private static final File RESOURCES_FOLDER = new File(ResourcesFolder.asFile(), "forClassAnalysis");
    private static final File CLASS_FILE_TO_ANALYZE = new File(RESOURCES_FOLDER, "ClassToBeAnalyzed.java");
    private static final File NON_COMPILABLE_CLASS = new File(RESOURCES_FOLDER, "NonCompilable.java");

    @Test
    void know_package() throws IOException {
        JavaClass javaClass = JavaClass.of(CLASS_FILE_TO_ANALYZE);

        assertEquals("testing", javaClass.packag().name());
    }

    @Test
    void know_imports() {
        JavaClass javaClass = JavaClass.of(CLASS_FILE_TO_ANALYZE);

        Collection<Import> imports = javaClass.imports();

        assertEquals(3, imports.size());

        var javaIo = getImport("java.io", imports);
        assertTrue(javaIo.isAsterisk());
        assertFalse(javaIo.isStatic());

        var staticMethod = getImport("java.lang.System.setErr", imports);
        assertFalse(staticMethod.isAsterisk());
        assertTrue(staticMethod.isStatic());

        var hashMapImport = getImport("java.util.HashMap", imports);
        assertFalse(hashMapImport.isAsterisk());
        assertFalse(hashMapImport.isStatic());
    }

    @Test
    void throw_exception_if_class_is_not_compilable() {
        assertThrows(ClassAnalysisFailedException.class, () -> JavaClass.of(NON_COMPILABLE_CLASS));
    }

    private static Import getImport(String name, Collection<Import> imports) {
        return imports.stream().filter(i -> i.value().equals(name)).findFirst().orElseThrow();
    }
}