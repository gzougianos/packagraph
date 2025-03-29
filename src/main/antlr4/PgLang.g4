grammar PgLang;

script: statement* EOF;

statement
    : includeStmt
    | excludeStmt
    | showMainGraphStmt
    | showNodesStmt
    | showEdgesStmt
    | defineStyleStmt
    | defineConstantStmt
    | exportStmt
    ;

includeStmt: 'include' 'source' 'directory' VALUE ';';
excludeStmt: 'exclude' 'externals' ';';

showMainGraphStmt: 'show' 'maingraph' styleDef? ';';
showNodesStmt: 'show' 'nodes' VALUE nodesAs? styleDef? ';';
nodesAs:  'as' VALUE;
styleDef: 'with' 'style' VALUE;

showEdgesStmt: 'show' 'edges' edgeFromDef? edgeToDef? styleDef? fromNodeStyleDef? toNodeStyleDef?';';
edgeFromDef: 'from' VALUE;
edgeToDef: 'to' VALUE;
fromNodeStyleDef: 'with' 'from-node' 'style' VALUE;
toNodeStyleDef: 'with' 'to-node''style' VALUE;

defineStyleStmt: 'define' 'style' VALUE 'as' VALUE ';';
defineConstantStmt: 'define' 'constant' VALUE 'as' VALUE ';';

exportStmt: 'export' 'as' VALUE exportInto? byOverwiting? ';';

exportInto: 'into' VALUE;
byOverwiting: 'by' 'overwriting';

VALUE: '\'' ( ~[\r\n'] | '\\' . )* '\'';
WS: [ \t\r\n]+ -> skip;
