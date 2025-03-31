package com.github.gzougianos.packagraph2.analysis;

import com.github.gzougianos.packagraph2.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassShould {

    @Test
    void know_package() throws Exception {
        TempDir tempDir = new TempDir();
        File sourceFile = tempDir.addJavaFile("ClassToBeAnalyzed.java", """
                package testing;
                
                import java.util.HashMap;
                import java.io.*;
                import static java.lang.System.setErr;
                import static javax.swing.SwingUtilities.*;
                
                public class ClassToBeAnalyzed {}
                """);
        JavaClass javaClass = JavaClass.of(sourceFile);

        assertEquals("testing", javaClass.packag().name());
    }

    @Test
    void know_imports() throws Exception {
        TempDir tempDir = new TempDir();
        File sourceFile = tempDir.addJavaFile("ClassToBeAnalyzed.java", """
                package testing;
                
                import java.util.HashMap;
                import java.io.*;
                import static java.lang.System.setErr;
                import static javax.swing.SwingUtilities.*;
                
                public class ClassToBeAnalyzed {}
                """);
        JavaClass javaClass = JavaClass.of(sourceFile);

        Collection<PackageName> imports = javaClass.imports();

        assertEquals(4, imports.size());

        assertImport("java.io", imports);
        assertImport("java.util", imports);
        assertImport("java.lang", imports);
        assertImport("javax.swing", imports);
    }

    @Test
    void throw_exception_if_class_is_not_compilable() throws Exception {
        TempDir tempDir = new TempDir();
        File sourceFile = tempDir.addJavaFile("ClassToBeAnalyzed.java", """
                package testing;
                
                blabla-not-compilable ClassToBeAnalyzed {}
                """);

        assertThrows(ClassAnalysisFailedException.class, () -> JavaClass.of(sourceFile));
    }

    private static void assertImport(String name, Collection<PackageName> imports) {
        imports.stream().filter(i -> i.name().equals(name)).findFirst().orElseThrow();
    }
}