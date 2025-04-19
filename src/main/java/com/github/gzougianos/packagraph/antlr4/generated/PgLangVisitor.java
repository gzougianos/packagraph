// Generated from C:/Users/gzoug/Java/workspace/packagraph/src/main/antlr4/PgLang.g4 by ANTLR 4.13.2
package com.github.gzougianos.packagraph.antlr4.generated;

import org.antlr.v4.runtime.tree.*;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PgLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface PgLangVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link PgLangParser#script}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitScript(PgLangParser.ScriptContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement(PgLangParser.StatementContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#includeStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIncludeStmt(PgLangParser.IncludeStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#excludeStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExcludeStmt(PgLangParser.ExcludeStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#showMainGraphStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitShowMainGraphStmt(PgLangParser.ShowMainGraphStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#showLegendGraphStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitShowLegendGraphStmt(PgLangParser.ShowLegendGraphStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#showNodesStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitShowNodesStmt(PgLangParser.ShowNodesStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#nodesAs}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNodesAs(PgLangParser.NodesAsContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#styleDef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStyleDef(PgLangParser.StyleDefContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#showEdgesStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitShowEdgesStmt(PgLangParser.ShowEdgesStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#edgeFromDef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEdgeFromDef(PgLangParser.EdgeFromDefContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#edgeToDef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEdgeToDef(PgLangParser.EdgeToDefContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#fromNodeStyleDef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFromNodeStyleDef(PgLangParser.FromNodeStyleDefContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#toNodeStyleDef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitToNodeStyleDef(PgLangParser.ToNodeStyleDefContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#defineStyleStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDefineStyleStmt(PgLangParser.DefineStyleStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#withLegend}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithLegend(PgLangParser.WithLegendContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#nodeOrEdge}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNodeOrEdge(PgLangParser.NodeOrEdgeContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#defineConstantStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDefineConstantStmt(PgLangParser.DefineConstantStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#exportStmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExportStmt(PgLangParser.ExportStmtContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#exportInto}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExportInto(PgLangParser.ExportIntoContext ctx);

    /**
     * Visit a parse tree produced by {@link PgLangParser#byOverwiting}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitByOverwiting(PgLangParser.ByOverwitingContext ctx);
}