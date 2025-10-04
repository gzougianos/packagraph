# packagraph

[![Build and Release JAR](https://github.com/gzougianos/packagraph/actions/workflows/packgraph.yml/badge.svg)](https://github.com/gzougianos/packagraph/actions/workflows/packgraph.yml)

*(experimental)*

## Problem:

*How can I see a graph of the inter-dependencies among Java projects, modules,
and packages while maintaining full control over which dependencies
are included (or excluded)?

## Solution:

*<b>Packagraph</b> is a lightweight tool that generates customizable package dependency
diagrams from Java source code. Packagraph reads configurations from a <code>.pg</code> file
and outputs diagrams in formats supported by [GraphViz](https://graphviz.org/).*

In other words, packagraph tries to fill the void left by *ObjectAid* (discontinued -
see [WebArchive](https://web.archive.org/web/20200418031122/http://www.objectaid.com/home)).

## Example:

See the [Spring Petclinic](examples/spring-petclinic/readme.md) example.

## How Does It Work?

### 1. `.pg` file parsing

Packagraph uses a custom [ANTLR](https://www.antlr.org/) grammar in order to configure the graph.
At the very first stage, packagraph parses the `.pg` file and validates
its syntax. Then, it translates the contents of the `.pg` file
to the export options & configurations.

### 2. Source code analysis

Depending on the configurations of the `pg` file, packagraph scans
the defined Java source code directories. For every Java source file,
it analyzes the `import` statements and creates the dependency graph.

### 3. Rendering

In the final stage, packagraph uses Graphviz in order to render and export
the dependency graph to a file such as `.png`, `.svg`, etc.

## How to use:

Packagraph needs Java 21 and can be used as a command-line tool where you simply provide
the path of the `.pg` as an argument:

```bash
java -jar packagraph.jar my_project.pg
```

## All `.pg` Available Options

### 1. Include Source Directories

```
include source directory '/path/to/source/dir';
```

Specifies the Java source directories to analyze for package dependencies.

### 2. Exclude External Dependencies

```
exclude externals;
```

Excludes external dependencies (packages outside the analyzed source directories) from the graph.

### 3. Show Nodes

```
show nodes 'package_pattern' [as 'display_name'] [with style 'style_name'];
```

Controls how packages matching the pattern are displayed in the graph.

- `package_pattern`: A regular expression to match package names
- `as 'display_name'`: Optional display name (supports `$1`, `$2`, etc. for regex groups).
  If empty, the node be excluded. Useful to exclude Java standard packages such as `java.*` or `javax.*`.
- `with style`: Optional style to apply to matching nodes

### 4. Define Constants

```
define constant 'CONSTANT_NAME' as 'value';
```

Defines a constant that can be reused throughout the configuration file using `${CONSTANT_NAME}` syntax.

### 5. Define Styles

```
define style 'style_name' as 'style_definition' [with node legend];
```

Defines a named style that can be applied to nodes or edges. The `style_definition`
uses [GraphViz attributes](https://graphviz.org/doc/info/attrs.html).

`with node legend` is optional and when specified, it will add a legend to the graph
that illustrates this style. See [an example](./docs/legend.png).

### 6. Show Edges

```
show edges [from 'source_pattern'] [to 'target_pattern'] 
    [with style 'style_name']
    [with from-node style 'from_style']
    [with to-node style 'to_style'];
```

Controls how dependencies between packages are displayed.

- `from 'source_pattern'`: Optional pattern to match source packages
- `to 'target_pattern'`: Optional pattern to match target packages
- `with style`: Optional style to apply to matching edges
- `with from-node style`: Optional style to apply to source nodes
- `with to-node style`: Optional style to apply to target nodes

### 7. Show Main Graph

```
show main graph [with style 'style_definition'];
```

Applies styles to the main graph.

### 8. Show Legend Graph

```
show legend graph [with style 'style_definition'];
```

Controls the appearance of the legend.

### 9. Export

```
export as 'format' [into 'output_file'] [by overwriting];
```

Specifies the output format and file for the graph.

- `format`: Output format (e.g., 'png', 'svg'). See [GraphViz formats](https://graphviz.org/docs/outputs/).
- `into`: Output file path (optional)
- `by overwriting`: Overwrite output file if it exists

### Example

```
// Define styles
define style 'default' as 'shape=box;fontsize=20';
define style 'infra' as 'shape=box;style=filled;fillcolor=lightgreen';

// Define constants
define constant 'JPA_PACKAGES' as '(org\.springframework\.data.*)|(javax\.persistence.*)';

// Include source code from multiple modules
include source directory './moduleA/src/main/java';
include source directory './moduleB/src/main/java';

// Configure nodes and edges
show nodes 'com\.example\.(.*)' as '$1' with style 'default';
show edges to '${JPA_PACKAGES}' with style 'color=blue';

// Export the graph
export as 'png' into 'dependencies.png' by overwriting;
```

### Notes

- All patterns use Java regular expression syntax
- Strings must be enclosed in single quotes
- Multiple statements must be separated by semicolons
- The order of statements can affect the final appearance (later styles override earlier ones)

## Future Improvements

- Support node clustering
- Try to find a way to ignore unused imports (harder bytecode analysis?)
- IntelliJ plugin to help with the `.pg` file editing (syntax check, keyword coloring, etc)

## Contributing

If you find a bug or have a feature request,
please open an issue on the [GitHub repository](https://github.com/gzougianos/packagraph).
If you would like to experiment, start from
the [ManualPlayground.java](./src/test/java/com/github/gzougianos/packagraph/ManualPlayground.java)
that generates a graph from the [/src/test/resources/for_maual_testing.pg](./src/test/resources/for_manual_testing.pg)
and exports it into the `/target` directory.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
