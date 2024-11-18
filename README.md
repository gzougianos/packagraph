# packagraph

[![Build and Release JAR](https://github.com/gzougianos/packagraph/actions/workflows/packgraph.yml/badge.svg)](https://github.com/gzougianos/packagraph/actions/workflows/packgraph.yml)

Packagraph is a lightweight tool designed to generate customizable package dependency
diagrams for Java source code. It reads configurations from a [Human JSON](https://hjson.github.io/)
file and outputs diagrams in formats supported by [GraphViz](https://graphviz.org/).

Below is an example diagram of `packagraph` itself, created using the
[for_manual_testing.hjson](./src/test/resources/for_manual_testing.hjson) configuration:

![petclinic](./packagraph.png)

## Why Use Packagraph?

Packagraph provides a simple way to visualize Java package dependencies,
filling the void left by tools like ObjectAid (discontinued -
see [WebArchive](https://web.archive.org/web/20200418031122/http://www.objectaid.com/home)).
It focuses specifically on package-level diagrams, making it ideal for understanding high-level architecture.

## How Does It Work?

### 1. Source Code Analysis

Packagraph scans Java files in the specified directories, extracting package-level dependencies based on `import`
statements.

### 2. Graph Generation

Packagraph uses the GraphViz library to create and export the
dependency diagrams, styled according to your configuration.

# Quick Start:

Packagraph can be used as a command-line tool:

```bash
java -jar packagraph.jar -o myOptions.hjson
```

### Example Configuration

A typical HJSON configuration might look like:

```hjson
{
  directories: [
    "./module1/src/main/java",
    "./module2/src/main/java"
  ],
  // Source directories
  output: {
    path: "./output/diagram.png",
    // Output file path
    overwrite: true,
    // Overwrite existing file
    "graphStyle": MAIN_GRAPH
  },
  definitions: [
    {
      packages: "com.example.service.*",
      as: "Service Layer",
      nodeStyle: {
        fillcolor: "lightblue"
      }
    },
    {
      packages: "com.example.repository.*",
      as: "Repository Layer",
      nodeStyle: {
        fillcolor: "lightgray"
      }
    },
    {
      packages: "java.*",
      as: ""
      // Exclude standard Java packages
    }
  ],
  clusters: [
    {
      packages: "Service Layer,com.example.dao.*",
      graphStyle: {
        label: "Application Core"
      }
    }
  ],
  //Styles
  nodeStyles: {
    default: {
      //Default node style inherited by all other node styles. 
      shape: "rectangle"
    }
  },
  graphStyles: {
    default: {
      fontsize: 18,
      fontcolor: "gray"
    },
    MAIN_GRAPH: {
      label: "My Project",
      fontsize: 24,
      fontcolor: "blue"
    }
  }
}
```

### Configuration Properties

#### Core Properties

Below is all the properties that can be defined in the HJson file and supported by packagraph (<b>*</b> required):
<table>
  <thead>
    <tr>
      <th>Property</th>
      <th>Type</th>
      <th>Description</th>
      <th>Example Value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>directories<b>*</b> </td>
      <td>String[]</td>
      <td>Base directories that contain (source code) java packages. Could be as simple as "src/main/java" or directories of multiple modules.</td>
      <td>
            <pre>
"directories": [
    "./myModule1/src/main/java",
    "./myModule2/src/main/java"
]</pre>
      </td>
    </tr>
    <tr>
      <td>output.path</td>
      <td>String</td>
      <td>Defines the output file path and file type. Please see the <a href="https://graphviz.org/docs/outputs/">GraphViz output formats</a>.</td>
      <td rowspan="3">
        <pre>
"output": {
    //PNG output
    "path": "./packagraph.png", 
    "overwrite": true,
    "graphStyle": {
    #Label of the main graph
      "label": "MyLabel",
      "fontsize": 24,
      "fontcolor": "purple",
      "dpi": 96
    }
}
</pre>
      </td>
    </tr>
    <tr>
      <td>output.overwrite</td>
      <td>boolean</td>
      <td>Whether to overwrite the output file if it already exists.</td>
    </tr>
    <tr>
      <td>output.graphStyle</td>
      <td>Object</td>
      <td>The style of the main graph (read below).  See also <a href="https://graphviz.org/doc/info/attrs.html">graph attributes</a>.</td>
    </tr>
    <tr>
      <td>includeOnlyFromDirectories</td>
      <td>boolean</td>
      <td>If true, only packages sourced from the <code>directories</code> will be included in the graph.</td>
    <td><pre>"includeOnlyFromDirectories": true</pre></td>
    </tr>
    <tr>
      <td>definitions</td>
      <td>Object[]</td>
      <td>Definitions can be used to re-name (or group into one) some of the packages. For example, you could define that packages <code>javax.persistence.*</code> and <code>org.springframework.jpa.*</code> will be exported as a single <code>JPA</code> package.</td>
      <td rowspan="5">
<pre>
"definitions": [
    {
      "packages": "org.spring.data.*",
      "as": "Spring Data",
      "nodeStyle": {
        // Database has 'green' node
        "fillcolor": "green"
      },
      "edgeInStyle": {
        // Database node has 'green' edges
        "color": "green"
      }
    },
    {
      "packages": "java.*", //JDK standards
      "as": "" //exclude from the graph
    },
    {
      //Trim the 'com.smth.' prefix
      //com.smth.pack1 and com.smth.pack2 
      //will become just 'pack1' and 'pack2'
      "packages": "com.smth\\.(.*)",
      "as": "$1",
      "nodeStyle": {
          "shape": "rectangle"
      }
    }
]
</pre>
</td>
    </tr>
    <tr>
      <td>definition(s).packages<b>*</b></td>
      <td>String</td>
      <td>The packages that this definition refers to. You can use comma for multiple packages and regular expressions.</td>
    </tr>
    <tr>
      <td>definition(s).as<b>*</b></td>
      <td>String</td>
      <td>The name of the re-defined package. In other words, how the package will be shown in the output graph. If this value is empty, the package is completely excluded from the graph.</td>
    </tr>
    <tr>
      <td>definition(s).nodeStyle</td>
      <td>Object</td>
      <td>Each package is exported to the graph as an individual node. <code>nodeStyle</code> 
defines the style of this node. It can be either a named style (defined in <code>nodeStyles</code>) or an inner object. See also <a href="https://graphviz.org/doc/info/attrs.html">node attributes</a>.</td>
    </tr>
    <tr>
      <td>definition(s).edgeInStyle</td>
      <td>Object</td>
      <td>Customize the edges (that point to) of a single package.
It can be either a named style (defined in <code>edgeStyles</code>) or an inner object.
Please see the <a href="https://graphviz.org/doc/info/attrs.html">GraphViz attributes</a> that can be applied to an edge.</td>
    </tr>
    <tr>
      <td>clusters</td>
      <td>Object[]</td>
      <td>Clusters allow you to group several packages into a single type of sub-graph.
More information on <a href="https://graphviz.org/Gallery/directed/cluster.html">GraphViz clusters</a>.</td>
    <td rowspan="4">
<pre>
"definitions": [
{
  "packages": "org.hibernate.*",
  "as": "Hibernate"
},
{
  "packages": "jakarta.persistence.*",
  "as": "JPA"
}
],
"clusters": [
    {
      "packages": "JPA,Hibernate,
    org.springframework.data.*",
      "graphStyle": {
        "label": "Database Access Layer",
        "fontsize": 18,
        "fontcolor": "gray"
      }
    }
]
</pre>
</td>
    </tr>
    <tr>
      <td>cluster(s).packages<b>*</b></td>
      <td>String</td>
      <td>A comma-separated list of packages that will be grouped within the cluster. 
Please note that at this stage, you have to refer to the packages based in their re-defined
names specified in<code>definitions</code>.</td>
    </tr>
    <tr>
      <td>cluster(s).name</td>
      <td>String</td>
      <td>The name of the cluster, to be used as an identifier. Does not have any impact on the output.</td>
    </tr>
    <tr>
      <td>cluster(s).graphStyle</td>
      <td>Object</td>
      <td>The style of the cluster graph. By default, all cluster graphs have the same <code>graphStyle</code>
as the main graph (<code>output.graphStyle</code>). It can be either a named style (defined in <code>graphStyles</code>) or an inner object.
See also <a href="https://graphviz.org/doc/info/attrs.html">graph attributes</a>.</td>
    </tr>
    <tr>
      <td>nodeStyles</td>
      <td>Object[]</td>
      <td>A list of named styles to be used in <code>definitions</code>. All nodes have the 
 <code>default</code> style by default. All other styles inherit the properties of the <code>default</code> style.
If you want to prevent the inheritance, use <code>"inheritDefault": false</code></td>
        <td>
<pre>
"nodeStyles": [
    "default": {
        "shape": "rectangle"
    },
    "BLUE_RECTANGLE": {
        "color": "blue"
    },
    "BLUE_CIRCLE": {
        "inheritDefault": false
        "color": "blue"
    },
]
"definitions": [
    {
        "packages": "org.spring.data.*",
        "as": "Spring Data",
        "nodeStyle": "BLUE_RECTANGLE"
    }
]
</pre>
</td>
    </tr>
<tr>
  <td>edgeInStyles</td>
  <td>Object[]</td>
  <td>A list of named styles to be used in <code>definitions</code>. All edges have the 
<code>default</code> style by default. All other styles inherit the properties of the <code>default</code> style.
If you want to prevent the inheritance, use <code>"inheritDefault": false</code>.</td>
  <td>
<pre>
"edgeInStyles": {
    "default": {
        "style": "solid"
    },
    "DASHED_RED": {
        "color": "red",
        "style": "dashed"
    },
    "SOLID_BLUE": {
        "inheritDefault": false,
        "color": "blue",
        "style": "solid"
    }
}
"definitions": [
{
    "packages": "org.spring.data.*",
    "as": "Spring Data",
    "edgeInStyle": "SOLID_BLUE"
}
]
</pre>
</td>
</tr>
<tr>
  <td>graphStyles</td>
  <td>Object[]</td>
  <td>A list of named styles to be used by clusters or the main graph. All clusters have the 
<code>default</code> style by default. All other styles inherit the properties of the <code>default</code> style.
If you want to prevent the inheritance, use <code>"inheritDefault": false</code>.</td>
  <td>
<pre>
"graphStyles": {
    "default": {
        "dpi": "96"
    },
    "BASIC_CLUSTER": {
        "color": "red"
    }
}
"clusters": [
{
    "packages": "org.spring.data.*",
    "graphStyle": "BASIC_CLUSTER"
}
]
</pre>
</td>
</tr>
    <tr>
      <td>constants</td>
      <td>Object[]</td>
      <td>A list of "key-value" pairs defined as constants within the Hjson file.
Use the <code>${MY_CONSTANT}</code> syntax to refer to a constant.</td>
    <td rowspan="3">
<pre>
"constants": [
    {
      "name": "EDGE_CUSTOM_BLUE",
      "value": "#258fc4"
    }
]
"nodeStyles": {
    "default":{
        "color": "${EDGE_CUSTOM_BLUE}"
    }
},
</pre></td>
    </tr>
    <tr>
      <td>constant(s).name<b>*</b></td>
      <td>String</td>
      <td>The name of the constant</td>
    </tr>
    <tr>
      <td>constant(s).value<b>*</b></td>
      <td>String</td>
      <td>The value of the constant</td>
    </tr>
  </tbody>
</table>

More examples of packagraph's usage can be found within the [examples](examples/) folder.
You can use [skeleton.hjson](skeleton.hjson) as a starting point for your own Human JSON files.