package com.github.gzougianos.packagraph2.antlr4;

import com.github.gzougianos.packagraph2.*;
import com.github.gzougianos.packagraph2.antlr4.generated.*;
import lombok.extern.slf4j.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.*;

import static java.util.Collections.unmodifiableList;

@Slf4j
public class PgLangInterpreter extends PgLangBaseListener {
    private MutableOptions options = new MutableOptions();

    @Override
    public void enterIncludeStmt(PgLangParser.IncludeStmtContext ctx) {
        var sourceDir = ctx.VALUE().getText();
        options.sourceDirs.add(removeQuotes(sourceDir));
    }

    @Override
    public void enterExcludeStmt(PgLangParser.ExcludeStmtContext ctx) {
        options.excludeExternals = true;
    }

    @Override
    public void enterShowMainGraphStmt(PgLangParser.ShowMainGraphStmtContext ctx) {
        if (ctx.styleDef() != null)
            options.mainGraphStyle = removeQuotes(ctx.styleDef().VALUE().getText());
    }

    @Override
    public void enterShowNodesStmt(PgLangParser.ShowNodesStmtContext ctx) {
        var packag = ctx.VALUE().getText();
        String as = null;
        if (ctx.nodesAs() != null)
            as = ctx.nodesAs().VALUE().getText();

        String style = null;
        if (ctx.styleDef() != null)
            style = ctx.styleDef().VALUE().getText();

        options.showNodes.add(
                new Options.ShowNodes(removeQuotes(packag), removeQuotes(as), removeQuotes(style)));
    }

    @Override
    public void enterDefineStyleStmt(PgLangParser.DefineStyleStmtContext ctx) {
        var name = ctx.VALUE(0).getText();
        var value = ctx.VALUE(1).getText();
        options.defineStyles.add(new Options.DefineStyle(removeQuotes(name), removeQuotes(value)));
    }

    @Override
    public void enterDefineConstantStmt(PgLangParser.DefineConstantStmtContext ctx) {
        var name = ctx.VALUE(0).getText();
        var value = ctx.VALUE(1).getText();
        options.defineConstants.add(new Options.DefineConstant(removeQuotes(name), removeQuotes(value)));
    }

    @Override
    public void enterShowEdgesStmt(PgLangParser.ShowEdgesStmtContext ctx) {
        String fromPackage = null;
        if (ctx.edgeFromDef() != null)
            fromPackage = ctx.edgeFromDef().VALUE().getText();

        String toPackage = null;
        if (ctx.edgeToDef() != null)
            toPackage = ctx.edgeToDef().VALUE().getText();

        String style = null;
        if (ctx.styleDef() != null)
            style = ctx.styleDef().VALUE().getText();

        String fromNodeStyle = null;
        if (ctx.fromNodeStyleDef() != null)
            fromNodeStyle = ctx.fromNodeStyleDef().VALUE().getText();

        String toNodeStyle = null;
        if (ctx.toNodeStyleDef() != null)
            toNodeStyle = ctx.toNodeStyleDef().VALUE().getText();

        options.showEdges.add(
                new Options.ShowEdges(removeQuotes(fromPackage), removeQuotes(toPackage), removeQuotes(style), removeQuotes(fromNodeStyle), removeQuotes(toNodeStyle)));
    }

    private static String removeQuotes(String str) {
        if (str == null)
            return null;

        if (str.startsWith("'") && str.endsWith("'"))
            return str.substring(1, str.length() - 1);
        return str;
    }

    public static Options interprete(String input) throws Exception {
        PgLangLexer lexer = new PgLangLexer(CharStreams.fromString(input));

        ThreadLocal<Boolean> syntaxError = ThreadLocal.withInitial(() -> false);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                syntaxError.set(true);
                log.error("line {}:{} {}", line, charPositionInLine, msg);
            }
        });
        PgLangParser parser = new PgLangParser(new CommonTokenStream(lexer));

        ParseTreeWalker walker = new ParseTreeWalker();
        PgLangInterpreter listener = new PgLangInterpreter();
        walker.walk(listener, parser.script());
        if (syntaxError.get())
            throw new SyntaxError("Syntax error in PG script.");

        return listener.options.build();
    }

    private static class SyntaxError extends Exception {
        public SyntaxError(String msg) {
            super(msg);
        }
    }

    private static class MutableOptions {
        private final List<String> sourceDirs = new LinkedList<>();
        private final List<Options.ShowNodes> showNodes = new LinkedList<>();
        private final List<Options.ShowEdges> showEdges = new LinkedList<>();
        private final List<Options.DefineStyle> defineStyles = new LinkedList<>();
        private final List<Options.DefineConstant> defineConstants = new LinkedList<>();
        private boolean excludeExternals = false;
        private String mainGraphStyle = null;

        Options build() {
            return Options.builder()
                    .sourceDirectories(unmodifiableList(sourceDirs))
                    .excludeExternals(excludeExternals)
                    .showNodes(unmodifiableList(showNodes))
                    .showEdges(unmodifiableList(showEdges))
                    .defineStyles(unmodifiableList(defineStyles))
                    .defineConstant(unmodifiableList(defineConstants))
                    .mainGraphStyle(mainGraphStyle)
                    .build();
        }
    }
}
