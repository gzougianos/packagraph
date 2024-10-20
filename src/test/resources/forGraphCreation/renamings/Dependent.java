package renamings;

import renamings.dependency.Dependency;

//To be renamed as "java"
import java.util.*;
import java.io.File;

import assume.something.to.be.excluded.*;

import renamings.excluded.ExcludedDependency;

public class Dependent {

    public static void main(String[] args) {
        System.out.println("Hello world!" + new Dependency());
    }
}