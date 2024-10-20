package externaldependency;

import externaldependency.dependency.Dependency;

import java.util.*;

public class Dependent {

    public static void main(String[] args) {
        System.out.println("Hello world!" + new Dependency());
    }
}