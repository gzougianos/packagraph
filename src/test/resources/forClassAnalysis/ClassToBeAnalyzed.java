package testing;

import java.util.HashMap;
import java.io.*;

import static java.lang.System.setErr;

import static javax.swing.SwingUtilities.*;

public class ClassToBeAnalyzed {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        var map = new HashMap<>();
        setErr(null);
    }
}