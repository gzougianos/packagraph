// Generated from C:/Users/gzoug/Java/workspace/packagraph/src/main/antlr4/PgLang.g4 by ANTLR 4.13.2
package com.github.gzougianos.packagraph.antlr4.generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class PgLangParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
            T__24 = 25, T__25 = 26, T__26 = 27, VALUE = 28, WS = 29, LINE_COMMENT = 30, BLOCK_COMMENT = 31;
    public static final int
            RULE_script = 0, RULE_statement = 1, RULE_includeStmt = 2, RULE_excludeStmt = 3,
            RULE_showMainGraphStmt = 4, RULE_showLegendGraphStmt = 5, RULE_showNodesStmt = 6,
            RULE_nodesAs = 7, RULE_styleDef = 8, RULE_showEdgesStmt = 9, RULE_edgeFromDef = 10,
            RULE_edgeToDef = 11, RULE_fromNodeStyleDef = 12, RULE_toNodeStyleDef = 13,
            RULE_defineStyleStmt = 14, RULE_withLegend = 15, RULE_nodeOrEdge = 16,
            RULE_defineConstantStmt = 17, RULE_exportStmt = 18, RULE_exportInto = 19,
            RULE_byOverwiting = 20;

    private static String[] makeRuleNames() {
        return new String[]{
                "script", "statement", "includeStmt", "excludeStmt", "showMainGraphStmt",
                "showLegendGraphStmt", "showNodesStmt", "nodesAs", "styleDef", "showEdgesStmt",
                "edgeFromDef", "edgeToDef", "fromNodeStyleDef", "toNodeStyleDef", "defineStyleStmt",
                "withLegend", "nodeOrEdge", "defineConstantStmt", "exportStmt", "exportInto",
                "byOverwiting"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'include'", "'source'", "'directory'", "';'", "'exclude'", "'externals'",
                "'show'", "'main'", "'graph'", "'legend'", "'nodes'", "'as'", "'with'",
                "'style'", "'edges'", "'from'", "'to'", "'from-node'", "'to-node'", "'define'",
                "'node'", "'edge'", "'constant'", "'export'", "'into'", "'by'", "'overwriting'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, "VALUE", "WS", "LINE_COMMENT", "BLOCK_COMMENT"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "PgLang.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public PgLangParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ScriptContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(PgLangParser.EOF, 0);
        }

        public List<StatementContext> statement() {
            return getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return getRuleContext(StatementContext.class, i);
        }

        public ScriptContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_script;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterScript(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitScript(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitScript(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ScriptContext script() throws RecognitionException {
        ScriptContext _localctx = new ScriptContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_script);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(45);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17825954L) != 0)) {
                    {
                        {
                            setState(42);
                            statement();
                        }
                    }
                    setState(47);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(48);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StatementContext extends ParserRuleContext {
        public IncludeStmtContext includeStmt() {
            return getRuleContext(IncludeStmtContext.class, 0);
        }

        public ExcludeStmtContext excludeStmt() {
            return getRuleContext(ExcludeStmtContext.class, 0);
        }

        public ShowMainGraphStmtContext showMainGraphStmt() {
            return getRuleContext(ShowMainGraphStmtContext.class, 0);
        }

        public ShowLegendGraphStmtContext showLegendGraphStmt() {
            return getRuleContext(ShowLegendGraphStmtContext.class, 0);
        }

        public ShowNodesStmtContext showNodesStmt() {
            return getRuleContext(ShowNodesStmtContext.class, 0);
        }

        public ShowEdgesStmtContext showEdgesStmt() {
            return getRuleContext(ShowEdgesStmtContext.class, 0);
        }

        public DefineStyleStmtContext defineStyleStmt() {
            return getRuleContext(DefineStyleStmtContext.class, 0);
        }

        public DefineConstantStmtContext defineConstantStmt() {
            return getRuleContext(DefineConstantStmtContext.class, 0);
        }

        public ExportStmtContext exportStmt() {
            return getRuleContext(ExportStmtContext.class, 0);
        }

        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitStatement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitStatement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StatementContext statement() throws RecognitionException {
        StatementContext _localctx = new StatementContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_statement);
        try {
            setState(59);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(50);
                    includeStmt();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(51);
                    excludeStmt();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(52);
                    showMainGraphStmt();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(53);
                    showLegendGraphStmt();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(54);
                    showNodesStmt();
                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(55);
                    showEdgesStmt();
                }
                break;
                case 7:
                    enterOuterAlt(_localctx, 7);
                {
                    setState(56);
                    defineStyleStmt();
                }
                break;
                case 8:
                    enterOuterAlt(_localctx, 8);
                {
                    setState(57);
                    defineConstantStmt();
                }
                break;
                case 9:
                    enterOuterAlt(_localctx, 9);
                {
                    setState(58);
                    exportStmt();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IncludeStmtContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public IncludeStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_includeStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterIncludeStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitIncludeStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitIncludeStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final IncludeStmtContext includeStmt() throws RecognitionException {
        IncludeStmtContext _localctx = new IncludeStmtContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_includeStmt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(61);
                match(T__0);
                setState(62);
                match(T__1);
                setState(63);
                match(T__2);
                setState(64);
                match(VALUE);
                setState(65);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExcludeStmtContext extends ParserRuleContext {
        public ExcludeStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_excludeStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterExcludeStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitExcludeStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitExcludeStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ExcludeStmtContext excludeStmt() throws RecognitionException {
        ExcludeStmtContext _localctx = new ExcludeStmtContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_excludeStmt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(67);
                match(T__4);
                setState(68);
                match(T__5);
                setState(69);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ShowMainGraphStmtContext extends ParserRuleContext {
        public StyleDefContext styleDef() {
            return getRuleContext(StyleDefContext.class, 0);
        }

        public ShowMainGraphStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_showMainGraphStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterShowMainGraphStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitShowMainGraphStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitShowMainGraphStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ShowMainGraphStmtContext showMainGraphStmt() throws RecognitionException {
        ShowMainGraphStmtContext _localctx = new ShowMainGraphStmtContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_showMainGraphStmt);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(71);
                match(T__6);
                setState(72);
                match(T__7);
                setState(73);
                match(T__8);
                setState(75);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(74);
                        styleDef();
                    }
                }

                setState(77);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ShowLegendGraphStmtContext extends ParserRuleContext {
        public StyleDefContext styleDef() {
            return getRuleContext(StyleDefContext.class, 0);
        }

        public ShowLegendGraphStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_showLegendGraphStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterShowLegendGraphStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitShowLegendGraphStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitShowLegendGraphStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ShowLegendGraphStmtContext showLegendGraphStmt() throws RecognitionException {
        ShowLegendGraphStmtContext _localctx = new ShowLegendGraphStmtContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_showLegendGraphStmt);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(79);
                match(T__6);
                setState(80);
                match(T__9);
                setState(81);
                match(T__8);
                setState(83);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(82);
                        styleDef();
                    }
                }

                setState(85);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ShowNodesStmtContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public NodesAsContext nodesAs() {
            return getRuleContext(NodesAsContext.class, 0);
        }

        public StyleDefContext styleDef() {
            return getRuleContext(StyleDefContext.class, 0);
        }

        public ShowNodesStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_showNodesStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterShowNodesStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitShowNodesStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitShowNodesStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ShowNodesStmtContext showNodesStmt() throws RecognitionException {
        ShowNodesStmtContext _localctx = new ShowNodesStmtContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_showNodesStmt);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(87);
                match(T__6);
                setState(88);
                match(T__10);
                setState(89);
                match(VALUE);
                setState(91);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__11) {
                    {
                        setState(90);
                        nodesAs();
                    }
                }

                setState(94);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(93);
                        styleDef();
                    }
                }

                setState(96);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NodesAsContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public NodesAsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodesAs;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterNodesAs(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitNodesAs(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitNodesAs(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NodesAsContext nodesAs() throws RecognitionException {
        NodesAsContext _localctx = new NodesAsContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_nodesAs);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(98);
                match(T__11);
                setState(99);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StyleDefContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public StyleDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_styleDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterStyleDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitStyleDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitStyleDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final StyleDefContext styleDef() throws RecognitionException {
        StyleDefContext _localctx = new StyleDefContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_styleDef);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(101);
                match(T__12);
                setState(102);
                match(T__13);
                setState(103);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ShowEdgesStmtContext extends ParserRuleContext {
        public EdgeFromDefContext edgeFromDef() {
            return getRuleContext(EdgeFromDefContext.class, 0);
        }

        public EdgeToDefContext edgeToDef() {
            return getRuleContext(EdgeToDefContext.class, 0);
        }

        public StyleDefContext styleDef() {
            return getRuleContext(StyleDefContext.class, 0);
        }

        public FromNodeStyleDefContext fromNodeStyleDef() {
            return getRuleContext(FromNodeStyleDefContext.class, 0);
        }

        public ToNodeStyleDefContext toNodeStyleDef() {
            return getRuleContext(ToNodeStyleDefContext.class, 0);
        }

        public ShowEdgesStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_showEdgesStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterShowEdgesStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitShowEdgesStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitShowEdgesStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ShowEdgesStmtContext showEdgesStmt() throws RecognitionException {
        ShowEdgesStmtContext _localctx = new ShowEdgesStmtContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_showEdgesStmt);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(105);
                match(T__6);
                setState(106);
                match(T__14);
                setState(108);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__15) {
                    {
                        setState(107);
                        edgeFromDef();
                    }
                }

                setState(111);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__16) {
                    {
                        setState(110);
                        edgeToDef();
                    }
                }

                setState(114);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                    case 1: {
                        setState(113);
                        styleDef();
                    }
                    break;
                }
                setState(117);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                    case 1: {
                        setState(116);
                        fromNodeStyleDef();
                    }
                    break;
                }
                setState(120);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(119);
                        toNodeStyleDef();
                    }
                }

                setState(122);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class EdgeFromDefContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public EdgeFromDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_edgeFromDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterEdgeFromDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitEdgeFromDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitEdgeFromDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final EdgeFromDefContext edgeFromDef() throws RecognitionException {
        EdgeFromDefContext _localctx = new EdgeFromDefContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_edgeFromDef);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(124);
                match(T__15);
                setState(125);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class EdgeToDefContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public EdgeToDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_edgeToDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterEdgeToDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitEdgeToDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitEdgeToDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final EdgeToDefContext edgeToDef() throws RecognitionException {
        EdgeToDefContext _localctx = new EdgeToDefContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_edgeToDef);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(127);
                match(T__16);
                setState(128);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class FromNodeStyleDefContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public FromNodeStyleDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_fromNodeStyleDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterFromNodeStyleDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitFromNodeStyleDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitFromNodeStyleDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FromNodeStyleDefContext fromNodeStyleDef() throws RecognitionException {
        FromNodeStyleDefContext _localctx = new FromNodeStyleDefContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_fromNodeStyleDef);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(130);
                match(T__12);
                setState(131);
                match(T__17);
                setState(132);
                match(T__13);
                setState(133);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ToNodeStyleDefContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public ToNodeStyleDefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_toNodeStyleDef;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterToNodeStyleDef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitToNodeStyleDef(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitToNodeStyleDef(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ToNodeStyleDefContext toNodeStyleDef() throws RecognitionException {
        ToNodeStyleDefContext _localctx = new ToNodeStyleDefContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_toNodeStyleDef);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(135);
                match(T__12);
                setState(136);
                match(T__18);
                setState(137);
                match(T__13);
                setState(138);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DefineStyleStmtContext extends ParserRuleContext {
        public List<TerminalNode> VALUE() {
            return getTokens(PgLangParser.VALUE);
        }

        public TerminalNode VALUE(int i) {
            return getToken(PgLangParser.VALUE, i);
        }

        public WithLegendContext withLegend() {
            return getRuleContext(WithLegendContext.class, 0);
        }

        public DefineStyleStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_defineStyleStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterDefineStyleStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitDefineStyleStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitDefineStyleStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final DefineStyleStmtContext defineStyleStmt() throws RecognitionException {
        DefineStyleStmtContext _localctx = new DefineStyleStmtContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_defineStyleStmt);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(140);
                match(T__19);
                setState(141);
                match(T__13);
                setState(142);
                match(VALUE);
                setState(143);
                match(T__11);
                setState(144);
                match(VALUE);
                setState(146);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(145);
                        withLegend();
                    }
                }

                setState(148);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class WithLegendContext extends ParserRuleContext {
        public NodeOrEdgeContext nodeOrEdge() {
            return getRuleContext(NodeOrEdgeContext.class, 0);
        }

        public WithLegendContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_withLegend;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterWithLegend(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitWithLegend(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitWithLegend(this);
            else return visitor.visitChildren(this);
        }
    }

    public final WithLegendContext withLegend() throws RecognitionException {
        WithLegendContext _localctx = new WithLegendContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_withLegend);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(150);
                match(T__12);
                setState(151);
                nodeOrEdge();
                setState(152);
                match(T__9);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NodeOrEdgeContext extends ParserRuleContext {
        public NodeOrEdgeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodeOrEdge;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterNodeOrEdge(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitNodeOrEdge(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitNodeOrEdge(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NodeOrEdgeContext nodeOrEdge() throws RecognitionException {
        NodeOrEdgeContext _localctx = new NodeOrEdgeContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_nodeOrEdge);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(154);
                _la = _input.LA(1);
                if (!(_la == T__20 || _la == T__21)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DefineConstantStmtContext extends ParserRuleContext {
        public List<TerminalNode> VALUE() {
            return getTokens(PgLangParser.VALUE);
        }

        public TerminalNode VALUE(int i) {
            return getToken(PgLangParser.VALUE, i);
        }

        public DefineConstantStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_defineConstantStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterDefineConstantStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitDefineConstantStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor)
                return ((PgLangVisitor<? extends T>) visitor).visitDefineConstantStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final DefineConstantStmtContext defineConstantStmt() throws RecognitionException {
        DefineConstantStmtContext _localctx = new DefineConstantStmtContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_defineConstantStmt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(156);
                match(T__19);
                setState(157);
                match(T__22);
                setState(158);
                match(VALUE);
                setState(159);
                match(T__11);
                setState(160);
                match(VALUE);
                setState(161);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExportStmtContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public ExportIntoContext exportInto() {
            return getRuleContext(ExportIntoContext.class, 0);
        }

        public ByOverwitingContext byOverwiting() {
            return getRuleContext(ByOverwitingContext.class, 0);
        }

        public ExportStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_exportStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterExportStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitExportStmt(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitExportStmt(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ExportStmtContext exportStmt() throws RecognitionException {
        ExportStmtContext _localctx = new ExportStmtContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_exportStmt);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(163);
                match(T__23);
                setState(164);
                match(T__11);
                setState(165);
                match(VALUE);
                setState(167);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__24) {
                    {
                        setState(166);
                        exportInto();
                    }
                }

                setState(170);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__25) {
                    {
                        setState(169);
                        byOverwiting();
                    }
                }

                setState(172);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExportIntoContext extends ParserRuleContext {
        public TerminalNode VALUE() {
            return getToken(PgLangParser.VALUE, 0);
        }

        public ExportIntoContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_exportInto;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterExportInto(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitExportInto(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitExportInto(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ExportIntoContext exportInto() throws RecognitionException {
        ExportIntoContext _localctx = new ExportIntoContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_exportInto);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(174);
                match(T__24);
                setState(175);
                match(VALUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ByOverwitingContext extends ParserRuleContext {
        public ByOverwitingContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_byOverwiting;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).enterByOverwiting(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof PgLangListener) ((PgLangListener) listener).exitByOverwiting(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PgLangVisitor) return ((PgLangVisitor<? extends T>) visitor).visitByOverwiting(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ByOverwitingContext byOverwiting() throws RecognitionException {
        ByOverwitingContext _localctx = new ByOverwitingContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_byOverwiting);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(177);
                match(T__25);
                setState(178);
                match(T__26);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\u0004\u0001\u001f\u00b5\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001" +
                    "\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004" +
                    "\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007" +
                    "\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b" +
                    "\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007" +
                    "\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007" +
                    "\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0005" +
                    "\u0000,\b\u0000\n\u0000\f\u0000/\t\u0000\u0001\u0000\u0001\u0000\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0003\u0001<\b\u0001\u0001\u0002\u0001" +
                    "\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001" +
                    "\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001" +
                    "\u0004\u0003\u0004L\b\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001" +
                    "\u0005\u0001\u0005\u0001\u0005\u0003\u0005T\b\u0005\u0001\u0005\u0001" +
                    "\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\\\b" +
                    "\u0006\u0001\u0006\u0003\u0006_\b\u0006\u0001\u0006\u0001\u0006\u0001" +
                    "\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t" +
                    "\u0001\t\u0001\t\u0003\tm\b\t\u0001\t\u0003\tp\b\t\u0001\t\u0003\ts\b" +
                    "\t\u0001\t\u0003\tv\b\t\u0001\t\u0003\ty\b\t\u0001\t\u0001\t\u0001\n\u0001" +
                    "\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f" +
                    "\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001" +
                    "\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0093" +
                    "\b\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001" +
                    "\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001" +
                    "\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001" +
                    "\u0012\u0001\u0012\u0003\u0012\u00a8\b\u0012\u0001\u0012\u0003\u0012\u00ab" +
                    "\b\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001" +
                    "\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0000\u0000\u0015\u0000\u0002" +
                    "\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e" +
                    " \"$&(\u0000\u0001\u0001\u0000\u0015\u0016\u00b4\u0000-\u0001\u0000\u0000" +
                    "\u0000\u0002;\u0001\u0000\u0000\u0000\u0004=\u0001\u0000\u0000\u0000\u0006" +
                    "C\u0001\u0000\u0000\u0000\bG\u0001\u0000\u0000\u0000\nO\u0001\u0000\u0000" +
                    "\u0000\fW\u0001\u0000\u0000\u0000\u000eb\u0001\u0000\u0000\u0000\u0010" +
                    "e\u0001\u0000\u0000\u0000\u0012i\u0001\u0000\u0000\u0000\u0014|\u0001" +
                    "\u0000\u0000\u0000\u0016\u007f\u0001\u0000\u0000\u0000\u0018\u0082\u0001" +
                    "\u0000\u0000\u0000\u001a\u0087\u0001\u0000\u0000\u0000\u001c\u008c\u0001" +
                    "\u0000\u0000\u0000\u001e\u0096\u0001\u0000\u0000\u0000 \u009a\u0001\u0000" +
                    "\u0000\u0000\"\u009c\u0001\u0000\u0000\u0000$\u00a3\u0001\u0000\u0000" +
                    "\u0000&\u00ae\u0001\u0000\u0000\u0000(\u00b1\u0001\u0000\u0000\u0000*" +
                    ",\u0003\u0002\u0001\u0000+*\u0001\u0000\u0000\u0000,/\u0001\u0000\u0000" +
                    "\u0000-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000.0\u0001\u0000" +
                    "\u0000\u0000/-\u0001\u0000\u0000\u000001\u0005\u0000\u0000\u00011\u0001" +
                    "\u0001\u0000\u0000\u00002<\u0003\u0004\u0002\u00003<\u0003\u0006\u0003" +
                    "\u00004<\u0003\b\u0004\u00005<\u0003\n\u0005\u00006<\u0003\f\u0006\u0000" +
                    "7<\u0003\u0012\t\u00008<\u0003\u001c\u000e\u00009<\u0003\"\u0011\u0000" +
                    ":<\u0003$\u0012\u0000;2\u0001\u0000\u0000\u0000;3\u0001\u0000\u0000\u0000" +
                    ";4\u0001\u0000\u0000\u0000;5\u0001\u0000\u0000\u0000;6\u0001\u0000\u0000" +
                    "\u0000;7\u0001\u0000\u0000\u0000;8\u0001\u0000\u0000\u0000;9\u0001\u0000" +
                    "\u0000\u0000;:\u0001\u0000\u0000\u0000<\u0003\u0001\u0000\u0000\u0000" +
                    "=>\u0005\u0001\u0000\u0000>?\u0005\u0002\u0000\u0000?@\u0005\u0003\u0000" +
                    "\u0000@A\u0005\u001c\u0000\u0000AB\u0005\u0004\u0000\u0000B\u0005\u0001" +
                    "\u0000\u0000\u0000CD\u0005\u0005\u0000\u0000DE\u0005\u0006\u0000\u0000" +
                    "EF\u0005\u0004\u0000\u0000F\u0007\u0001\u0000\u0000\u0000GH\u0005\u0007" +
                    "\u0000\u0000HI\u0005\b\u0000\u0000IK\u0005\t\u0000\u0000JL\u0003\u0010" +
                    "\b\u0000KJ\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LM\u0001\u0000" +
                    "\u0000\u0000MN\u0005\u0004\u0000\u0000N\t\u0001\u0000\u0000\u0000OP\u0005" +
                    "\u0007\u0000\u0000PQ\u0005\n\u0000\u0000QS\u0005\t\u0000\u0000RT\u0003" +
                    "\u0010\b\u0000SR\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000TU\u0001" +
                    "\u0000\u0000\u0000UV\u0005\u0004\u0000\u0000V\u000b\u0001\u0000\u0000" +
                    "\u0000WX\u0005\u0007\u0000\u0000XY\u0005\u000b\u0000\u0000Y[\u0005\u001c" +
                    "\u0000\u0000Z\\\u0003\u000e\u0007\u0000[Z\u0001\u0000\u0000\u0000[\\\u0001" +
                    "\u0000\u0000\u0000\\^\u0001\u0000\u0000\u0000]_\u0003\u0010\b\u0000^]" +
                    "\u0001\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000" +
                    "\u0000`a\u0005\u0004\u0000\u0000a\r\u0001\u0000\u0000\u0000bc\u0005\f" +
                    "\u0000\u0000cd\u0005\u001c\u0000\u0000d\u000f\u0001\u0000\u0000\u0000" +
                    "ef\u0005\r\u0000\u0000fg\u0005\u000e\u0000\u0000gh\u0005\u001c\u0000\u0000" +
                    "h\u0011\u0001\u0000\u0000\u0000ij\u0005\u0007\u0000\u0000jl\u0005\u000f" +
                    "\u0000\u0000km\u0003\u0014\n\u0000lk\u0001\u0000\u0000\u0000lm\u0001\u0000" +
                    "\u0000\u0000mo\u0001\u0000\u0000\u0000np\u0003\u0016\u000b\u0000on\u0001" +
                    "\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pr\u0001\u0000\u0000\u0000" +
                    "qs\u0003\u0010\b\u0000rq\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000" +
                    "su\u0001\u0000\u0000\u0000tv\u0003\u0018\f\u0000ut\u0001\u0000\u0000\u0000" +
                    "uv\u0001\u0000\u0000\u0000vx\u0001\u0000\u0000\u0000wy\u0003\u001a\r\u0000" +
                    "xw\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000" +
                    "\u0000z{\u0005\u0004\u0000\u0000{\u0013\u0001\u0000\u0000\u0000|}\u0005" +
                    "\u0010\u0000\u0000}~\u0005\u001c\u0000\u0000~\u0015\u0001\u0000\u0000" +
                    "\u0000\u007f\u0080\u0005\u0011\u0000\u0000\u0080\u0081\u0005\u001c\u0000" +
                    "\u0000\u0081\u0017\u0001\u0000\u0000\u0000\u0082\u0083\u0005\r\u0000\u0000" +
                    "\u0083\u0084\u0005\u0012\u0000\u0000\u0084\u0085\u0005\u000e\u0000\u0000" +
                    "\u0085\u0086\u0005\u001c\u0000\u0000\u0086\u0019\u0001\u0000\u0000\u0000" +
                    "\u0087\u0088\u0005\r\u0000\u0000\u0088\u0089\u0005\u0013\u0000\u0000\u0089" +
                    "\u008a\u0005\u000e\u0000\u0000\u008a\u008b\u0005\u001c\u0000\u0000\u008b" +
                    "\u001b\u0001\u0000\u0000\u0000\u008c\u008d\u0005\u0014\u0000\u0000\u008d" +
                    "\u008e\u0005\u000e\u0000\u0000\u008e\u008f\u0005\u001c\u0000\u0000\u008f" +
                    "\u0090\u0005\f\u0000\u0000\u0090\u0092\u0005\u001c\u0000\u0000\u0091\u0093" +
                    "\u0003\u001e\u000f\u0000\u0092\u0091\u0001\u0000\u0000\u0000\u0092\u0093" +
                    "\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0095" +
                    "\u0005\u0004\u0000\u0000\u0095\u001d\u0001\u0000\u0000\u0000\u0096\u0097" +
                    "\u0005\r\u0000\u0000\u0097\u0098\u0003 \u0010\u0000\u0098\u0099\u0005" +
                    "\n\u0000\u0000\u0099\u001f\u0001\u0000\u0000\u0000\u009a\u009b\u0007\u0000" +
                    "\u0000\u0000\u009b!\u0001\u0000\u0000\u0000\u009c\u009d\u0005\u0014\u0000" +
                    "\u0000\u009d\u009e\u0005\u0017\u0000\u0000\u009e\u009f\u0005\u001c\u0000" +
                    "\u0000\u009f\u00a0\u0005\f\u0000\u0000\u00a0\u00a1\u0005\u001c\u0000\u0000" +
                    "\u00a1\u00a2\u0005\u0004\u0000\u0000\u00a2#\u0001\u0000\u0000\u0000\u00a3" +
                    "\u00a4\u0005\u0018\u0000\u0000\u00a4\u00a5\u0005\f\u0000\u0000\u00a5\u00a7" +
                    "\u0005\u001c\u0000\u0000\u00a6\u00a8\u0003&\u0013\u0000\u00a7\u00a6\u0001" +
                    "\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00aa\u0001" +
                    "\u0000\u0000\u0000\u00a9\u00ab\u0003(\u0014\u0000\u00aa\u00a9\u0001\u0000" +
                    "\u0000\u0000\u00aa\u00ab\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000" +
                    "\u0000\u0000\u00ac\u00ad\u0005\u0004\u0000\u0000\u00ad%\u0001\u0000\u0000" +
                    "\u0000\u00ae\u00af\u0005\u0019\u0000\u0000\u00af\u00b0\u0005\u001c\u0000" +
                    "\u0000\u00b0\'\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005\u001a\u0000\u0000" +
                    "\u00b2\u00b3\u0005\u001b\u0000\u0000\u00b3)\u0001\u0000\u0000\u0000\u000e" +
                    "-;KS[^lorux\u0092\u00a7\u00aa";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}