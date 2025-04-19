// Generated from C:/Users/gzoug/Java/workspace/packagraph/src/main/antlr4/PgLang.g4 by ANTLR 4.13.2
package com.github.gzougianos.packagraph.antlr4.generated;

import org.antlr.v4.runtime.tree.*;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PgLangParser}.
 */
public interface PgLangListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link PgLangParser#script}.
     *
     * @param ctx the parse tree
     */
    void enterScript(PgLangParser.ScriptContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#script}.
     *
     * @param ctx the parse tree
     */
    void exitScript(PgLangParser.ScriptContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterStatement(PgLangParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitStatement(PgLangParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#includeStmt}.
     *
     * @param ctx the parse tree
     */
    void enterIncludeStmt(PgLangParser.IncludeStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#includeStmt}.
     *
     * @param ctx the parse tree
     */
    void exitIncludeStmt(PgLangParser.IncludeStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#excludeStmt}.
     *
     * @param ctx the parse tree
     */
    void enterExcludeStmt(PgLangParser.ExcludeStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#excludeStmt}.
     *
     * @param ctx the parse tree
     */
    void exitExcludeStmt(PgLangParser.ExcludeStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#showMainGraphStmt}.
     *
     * @param ctx the parse tree
     */
    void enterShowMainGraphStmt(PgLangParser.ShowMainGraphStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#showMainGraphStmt}.
     *
     * @param ctx the parse tree
     */
    void exitShowMainGraphStmt(PgLangParser.ShowMainGraphStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#showLegendGraphStmt}.
     *
     * @param ctx the parse tree
     */
    void enterShowLegendGraphStmt(PgLangParser.ShowLegendGraphStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#showLegendGraphStmt}.
     *
     * @param ctx the parse tree
     */
    void exitShowLegendGraphStmt(PgLangParser.ShowLegendGraphStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#showNodesStmt}.
     *
     * @param ctx the parse tree
     */
    void enterShowNodesStmt(PgLangParser.ShowNodesStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#showNodesStmt}.
     *
     * @param ctx the parse tree
     */
    void exitShowNodesStmt(PgLangParser.ShowNodesStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#nodesAs}.
     *
     * @param ctx the parse tree
     */
    void enterNodesAs(PgLangParser.NodesAsContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#nodesAs}.
     *
     * @param ctx the parse tree
     */
    void exitNodesAs(PgLangParser.NodesAsContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#styleDef}.
     *
     * @param ctx the parse tree
     */
    void enterStyleDef(PgLangParser.StyleDefContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#styleDef}.
     *
     * @param ctx the parse tree
     */
    void exitStyleDef(PgLangParser.StyleDefContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#showEdgesStmt}.
     *
     * @param ctx the parse tree
     */
    void enterShowEdgesStmt(PgLangParser.ShowEdgesStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#showEdgesStmt}.
     *
     * @param ctx the parse tree
     */
    void exitShowEdgesStmt(PgLangParser.ShowEdgesStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#edgeFromDef}.
     *
     * @param ctx the parse tree
     */
    void enterEdgeFromDef(PgLangParser.EdgeFromDefContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#edgeFromDef}.
     *
     * @param ctx the parse tree
     */
    void exitEdgeFromDef(PgLangParser.EdgeFromDefContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#edgeToDef}.
     *
     * @param ctx the parse tree
     */
    void enterEdgeToDef(PgLangParser.EdgeToDefContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#edgeToDef}.
     *
     * @param ctx the parse tree
     */
    void exitEdgeToDef(PgLangParser.EdgeToDefContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#fromNodeStyleDef}.
     *
     * @param ctx the parse tree
     */
    void enterFromNodeStyleDef(PgLangParser.FromNodeStyleDefContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#fromNodeStyleDef}.
     *
     * @param ctx the parse tree
     */
    void exitFromNodeStyleDef(PgLangParser.FromNodeStyleDefContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#toNodeStyleDef}.
     *
     * @param ctx the parse tree
     */
    void enterToNodeStyleDef(PgLangParser.ToNodeStyleDefContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#toNodeStyleDef}.
     *
     * @param ctx the parse tree
     */
    void exitToNodeStyleDef(PgLangParser.ToNodeStyleDefContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#defineStyleStmt}.
     *
     * @param ctx the parse tree
     */
    void enterDefineStyleStmt(PgLangParser.DefineStyleStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#defineStyleStmt}.
     *
     * @param ctx the parse tree
     */
    void exitDefineStyleStmt(PgLangParser.DefineStyleStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#withLegend}.
     *
     * @param ctx the parse tree
     */
    void enterWithLegend(PgLangParser.WithLegendContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#withLegend}.
     *
     * @param ctx the parse tree
     */
    void exitWithLegend(PgLangParser.WithLegendContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#nodeOrEdge}.
     *
     * @param ctx the parse tree
     */
    void enterNodeOrEdge(PgLangParser.NodeOrEdgeContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#nodeOrEdge}.
     *
     * @param ctx the parse tree
     */
    void exitNodeOrEdge(PgLangParser.NodeOrEdgeContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#defineConstantStmt}.
     *
     * @param ctx the parse tree
     */
    void enterDefineConstantStmt(PgLangParser.DefineConstantStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#defineConstantStmt}.
     *
     * @param ctx the parse tree
     */
    void exitDefineConstantStmt(PgLangParser.DefineConstantStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#exportStmt}.
     *
     * @param ctx the parse tree
     */
    void enterExportStmt(PgLangParser.ExportStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#exportStmt}.
     *
     * @param ctx the parse tree
     */
    void exitExportStmt(PgLangParser.ExportStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#exportInto}.
     *
     * @param ctx the parse tree
     */
    void enterExportInto(PgLangParser.ExportIntoContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#exportInto}.
     *
     * @param ctx the parse tree
     */
    void exitExportInto(PgLangParser.ExportIntoContext ctx);

    /**
     * Enter a parse tree produced by {@link PgLangParser#byOverwiting}.
     *
     * @param ctx the parse tree
     */
    void enterByOverwiting(PgLangParser.ByOverwitingContext ctx);

    /**
     * Exit a parse tree produced by {@link PgLangParser#byOverwiting}.
     *
     * @param ctx the parse tree
     */
    void exitByOverwiting(PgLangParser.ByOverwitingContext ctx);
}