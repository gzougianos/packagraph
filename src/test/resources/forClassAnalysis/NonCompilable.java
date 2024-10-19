package testing;

import java.util.HashMap;

import static java.lang.System.setErr;

public class NonCompilable {

    I CANT BE COMPILED.
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var map = new HashMap<>();
        setErr(null);
    }
}