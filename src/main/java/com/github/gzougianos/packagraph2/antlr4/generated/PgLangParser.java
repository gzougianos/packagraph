// Generated from C:/Users/gzoug/Java/workspace/packagraph/src/main/antlr4/PgLang.g4 by ANTLR 4.13.2
package com.github.gzougianos.packagraph2.antlr4.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class PgLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, VALUE=27, WS=28, LINE_COMMENT=29, BLOCK_COMMENT=30;
	public static final int
		RULE_script = 0, RULE_statement = 1, RULE_includeStmt = 2, RULE_excludeStmt = 3, 
		RULE_showMainGraphStmt = 4, RULE_showNodesStmt = 5, RULE_nodesAs = 6, 
		RULE_styleDef = 7, RULE_showEdgesStmt = 8, RULE_edgeFromDef = 9, RULE_edgeToDef = 10, 
		RULE_fromNodeStyleDef = 11, RULE_toNodeStyleDef = 12, RULE_defineStyleStmt = 13, 
		RULE_withLegend = 14, RULE_nodeOrEdge = 15, RULE_defineConstantStmt = 16, 
		RULE_exportStmt = 17, RULE_exportInto = 18, RULE_byOverwiting = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"script", "statement", "includeStmt", "excludeStmt", "showMainGraphStmt", 
			"showNodesStmt", "nodesAs", "styleDef", "showEdgesStmt", "edgeFromDef", 
			"edgeToDef", "fromNodeStyleDef", "toNodeStyleDef", "defineStyleStmt", 
			"withLegend", "nodeOrEdge", "defineConstantStmt", "exportStmt", "exportInto", 
			"byOverwiting"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'include'", "'source'", "'directory'", "';'", "'exclude'", "'externals'", 
			"'show'", "'maingraph'", "'nodes'", "'as'", "'with'", "'style'", "'edges'", 
			"'from'", "'to'", "'from-node'", "'to-node'", "'define'", "'legend'", 
			"'node'", "'edge'", "'constant'", "'export'", "'into'", "'by'", "'overwriting'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "VALUE", "WS", "LINE_COMMENT", "BLOCK_COMMENT"
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
	public String getGrammarFileName() { return "PgLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PgLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ScriptContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(PgLangParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterScript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitScript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitScript(this);
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
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8650914L) != 0)) {
				{
				{
				setState(40);
				statement();
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(46);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public IncludeStmtContext includeStmt() {
			return getRuleContext(IncludeStmtContext.class,0);
		}
		public ExcludeStmtContext excludeStmt() {
			return getRuleContext(ExcludeStmtContext.class,0);
		}
		public ShowMainGraphStmtContext showMainGraphStmt() {
			return getRuleContext(ShowMainGraphStmtContext.class,0);
		}
		public ShowNodesStmtContext showNodesStmt() {
			return getRuleContext(ShowNodesStmtContext.class,0);
		}
		public ShowEdgesStmtContext showEdgesStmt() {
			return getRuleContext(ShowEdgesStmtContext.class,0);
		}
		public DefineStyleStmtContext defineStyleStmt() {
			return getRuleContext(DefineStyleStmtContext.class,0);
		}
		public DefineConstantStmtContext defineConstantStmt() {
			return getRuleContext(DefineConstantStmtContext.class,0);
		}
		public ExportStmtContext exportStmt() {
			return getRuleContext(ExportStmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(56);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				includeStmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(49);
				excludeStmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(50);
				showMainGraphStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(51);
				showNodesStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(52);
				showEdgesStmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(53);
				defineStyleStmt();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(54);
				defineConstantStmt();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(55);
				exportStmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IncludeStmtContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public IncludeStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_includeStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterIncludeStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitIncludeStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitIncludeStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncludeStmtContext includeStmt() throws RecognitionException {
		IncludeStmtContext _localctx = new IncludeStmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_includeStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__0);
			setState(59);
			match(T__1);
			setState(60);
			match(T__2);
			setState(61);
			match(VALUE);
			setState(62);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExcludeStmtContext extends ParserRuleContext {
		public ExcludeStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_excludeStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterExcludeStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitExcludeStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitExcludeStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExcludeStmtContext excludeStmt() throws RecognitionException {
		ExcludeStmtContext _localctx = new ExcludeStmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_excludeStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(T__4);
			setState(65);
			match(T__5);
			setState(66);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ShowMainGraphStmtContext extends ParserRuleContext {
		public StyleDefContext styleDef() {
			return getRuleContext(StyleDefContext.class,0);
		}
		public ShowMainGraphStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showMainGraphStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterShowMainGraphStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitShowMainGraphStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitShowMainGraphStmt(this);
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
			setState(68);
			match(T__6);
			setState(69);
			match(T__7);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(70);
				styleDef();
				}
			}

			setState(73);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ShowNodesStmtContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public NodesAsContext nodesAs() {
			return getRuleContext(NodesAsContext.class,0);
		}
		public StyleDefContext styleDef() {
			return getRuleContext(StyleDefContext.class,0);
		}
		public ShowNodesStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showNodesStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterShowNodesStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitShowNodesStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitShowNodesStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShowNodesStmtContext showNodesStmt() throws RecognitionException {
		ShowNodesStmtContext _localctx = new ShowNodesStmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_showNodesStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(T__6);
			setState(76);
			match(T__8);
			setState(77);
			match(VALUE);
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(78);
				nodesAs();
				}
			}

			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(81);
				styleDef();
				}
			}

			setState(84);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NodesAsContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public NodesAsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodesAs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterNodesAs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitNodesAs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitNodesAs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodesAsContext nodesAs() throws RecognitionException {
		NodesAsContext _localctx = new NodesAsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_nodesAs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(T__9);
			setState(87);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StyleDefContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public StyleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_styleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterStyleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitStyleDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitStyleDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StyleDefContext styleDef() throws RecognitionException {
		StyleDefContext _localctx = new StyleDefContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_styleDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(T__10);
			setState(90);
			match(T__11);
			setState(91);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ShowEdgesStmtContext extends ParserRuleContext {
		public EdgeFromDefContext edgeFromDef() {
			return getRuleContext(EdgeFromDefContext.class,0);
		}
		public EdgeToDefContext edgeToDef() {
			return getRuleContext(EdgeToDefContext.class,0);
		}
		public StyleDefContext styleDef() {
			return getRuleContext(StyleDefContext.class,0);
		}
		public FromNodeStyleDefContext fromNodeStyleDef() {
			return getRuleContext(FromNodeStyleDefContext.class,0);
		}
		public ToNodeStyleDefContext toNodeStyleDef() {
			return getRuleContext(ToNodeStyleDefContext.class,0);
		}
		public ShowEdgesStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showEdgesStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterShowEdgesStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitShowEdgesStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitShowEdgesStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShowEdgesStmtContext showEdgesStmt() throws RecognitionException {
		ShowEdgesStmtContext _localctx = new ShowEdgesStmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_showEdgesStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(T__6);
			setState(94);
			match(T__12);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(95);
				edgeFromDef();
				}
			}

			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(98);
				edgeToDef();
				}
			}

			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(101);
				styleDef();
				}
				break;
			}
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(104);
				fromNodeStyleDef();
				}
				break;
			}
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(107);
				toNodeStyleDef();
				}
			}

			setState(110);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EdgeFromDefContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public EdgeFromDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeFromDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterEdgeFromDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitEdgeFromDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitEdgeFromDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeFromDefContext edgeFromDef() throws RecognitionException {
		EdgeFromDefContext _localctx = new EdgeFromDefContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_edgeFromDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__13);
			setState(113);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EdgeToDefContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public EdgeToDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeToDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterEdgeToDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitEdgeToDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitEdgeToDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeToDefContext edgeToDef() throws RecognitionException {
		EdgeToDefContext _localctx = new EdgeToDefContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_edgeToDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(T__14);
			setState(116);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FromNodeStyleDefContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public FromNodeStyleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fromNodeStyleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterFromNodeStyleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitFromNodeStyleDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitFromNodeStyleDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FromNodeStyleDefContext fromNodeStyleDef() throws RecognitionException {
		FromNodeStyleDefContext _localctx = new FromNodeStyleDefContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_fromNodeStyleDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(T__10);
			setState(119);
			match(T__15);
			setState(120);
			match(T__11);
			setState(121);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ToNodeStyleDefContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public ToNodeStyleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_toNodeStyleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterToNodeStyleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitToNodeStyleDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitToNodeStyleDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ToNodeStyleDefContext toNodeStyleDef() throws RecognitionException {
		ToNodeStyleDefContext _localctx = new ToNodeStyleDefContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_toNodeStyleDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(T__10);
			setState(124);
			match(T__16);
			setState(125);
			match(T__11);
			setState(126);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefineStyleStmtContext extends ParserRuleContext {
		public List<TerminalNode> VALUE() { return getTokens(PgLangParser.VALUE); }
		public TerminalNode VALUE(int i) {
			return getToken(PgLangParser.VALUE, i);
		}
		public WithLegendContext withLegend() {
			return getRuleContext(WithLegendContext.class,0);
		}
		public DefineStyleStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineStyleStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterDefineStyleStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitDefineStyleStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitDefineStyleStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefineStyleStmtContext defineStyleStmt() throws RecognitionException {
		DefineStyleStmtContext _localctx = new DefineStyleStmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_defineStyleStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(T__17);
			setState(129);
			match(T__11);
			setState(130);
			match(VALUE);
			setState(131);
			match(T__9);
			setState(132);
			match(VALUE);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(133);
				withLegend();
				}
			}

			setState(136);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WithLegendContext extends ParserRuleContext {
		public NodeOrEdgeContext nodeOrEdge() {
			return getRuleContext(NodeOrEdgeContext.class,0);
		}
		public WithLegendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withLegend; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterWithLegend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitWithLegend(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitWithLegend(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithLegendContext withLegend() throws RecognitionException {
		WithLegendContext _localctx = new WithLegendContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_withLegend);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(T__10);
			setState(139);
			nodeOrEdge();
			setState(140);
			match(T__18);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NodeOrEdgeContext extends ParserRuleContext {
		public NodeOrEdgeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeOrEdge; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterNodeOrEdge(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitNodeOrEdge(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitNodeOrEdge(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeOrEdgeContext nodeOrEdge() throws RecognitionException {
		NodeOrEdgeContext _localctx = new NodeOrEdgeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_nodeOrEdge);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			_la = _input.LA(1);
			if ( !(_la==T__19 || _la==T__20) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefineConstantStmtContext extends ParserRuleContext {
		public List<TerminalNode> VALUE() { return getTokens(PgLangParser.VALUE); }
		public TerminalNode VALUE(int i) {
			return getToken(PgLangParser.VALUE, i);
		}
		public DefineConstantStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineConstantStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterDefineConstantStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitDefineConstantStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitDefineConstantStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefineConstantStmtContext defineConstantStmt() throws RecognitionException {
		DefineConstantStmtContext _localctx = new DefineConstantStmtContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_defineConstantStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(T__17);
			setState(145);
			match(T__21);
			setState(146);
			match(VALUE);
			setState(147);
			match(T__9);
			setState(148);
			match(VALUE);
			setState(149);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExportStmtContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public ExportIntoContext exportInto() {
			return getRuleContext(ExportIntoContext.class,0);
		}
		public ByOverwitingContext byOverwiting() {
			return getRuleContext(ByOverwitingContext.class,0);
		}
		public ExportStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterExportStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitExportStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitExportStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExportStmtContext exportStmt() throws RecognitionException {
		ExportStmtContext _localctx = new ExportStmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_exportStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			match(T__22);
			setState(152);
			match(T__9);
			setState(153);
			match(VALUE);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__23) {
				{
				setState(154);
				exportInto();
				}
			}

			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(157);
				byOverwiting();
				}
			}

			setState(160);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExportIntoContext extends ParserRuleContext {
		public TerminalNode VALUE() { return getToken(PgLangParser.VALUE, 0); }
		public ExportIntoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportInto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterExportInto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitExportInto(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitExportInto(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExportIntoContext exportInto() throws RecognitionException {
		ExportIntoContext _localctx = new ExportIntoContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_exportInto);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(T__23);
			setState(163);
			match(VALUE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ByOverwitingContext extends ParserRuleContext {
		public ByOverwitingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_byOverwiting; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).enterByOverwiting(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PgLangListener ) ((PgLangListener)listener).exitByOverwiting(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PgLangVisitor ) return ((PgLangVisitor<? extends T>)visitor).visitByOverwiting(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ByOverwitingContext byOverwiting() throws RecognitionException {
		ByOverwitingContext _localctx = new ByOverwitingContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_byOverwiting);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(T__24);
			setState(166);
			match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001e\u00a9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0005\u0000*\b\u0000\n\u0000"+
		"\f\u0000-\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u00019\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u0004H\b\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005P\b"+
		"\u0005\u0001\u0005\u0003\u0005S\b\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0003\ba\b\b\u0001\b\u0003\bd\b\b\u0001"+
		"\b\u0003\bg\b\b\u0001\b\u0003\bj\b\b\u0001\b\u0003\bm\b\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0087\b\r\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0003\u0011\u009c\b\u0011\u0001\u0011\u0003\u0011\u009f\b\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0000\u0000\u0014\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000"+
		"\u0001\u0001\u0000\u0014\u0015\u00a7\u0000+\u0001\u0000\u0000\u0000\u0002"+
		"8\u0001\u0000\u0000\u0000\u0004:\u0001\u0000\u0000\u0000\u0006@\u0001"+
		"\u0000\u0000\u0000\bD\u0001\u0000\u0000\u0000\nK\u0001\u0000\u0000\u0000"+
		"\fV\u0001\u0000\u0000\u0000\u000eY\u0001\u0000\u0000\u0000\u0010]\u0001"+
		"\u0000\u0000\u0000\u0012p\u0001\u0000\u0000\u0000\u0014s\u0001\u0000\u0000"+
		"\u0000\u0016v\u0001\u0000\u0000\u0000\u0018{\u0001\u0000\u0000\u0000\u001a"+
		"\u0080\u0001\u0000\u0000\u0000\u001c\u008a\u0001\u0000\u0000\u0000\u001e"+
		"\u008e\u0001\u0000\u0000\u0000 \u0090\u0001\u0000\u0000\u0000\"\u0097"+
		"\u0001\u0000\u0000\u0000$\u00a2\u0001\u0000\u0000\u0000&\u00a5\u0001\u0000"+
		"\u0000\u0000(*\u0003\u0002\u0001\u0000)(\u0001\u0000\u0000\u0000*-\u0001"+
		"\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000"+
		",.\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000./\u0005\u0000\u0000"+
		"\u0001/\u0001\u0001\u0000\u0000\u000009\u0003\u0004\u0002\u000019\u0003"+
		"\u0006\u0003\u000029\u0003\b\u0004\u000039\u0003\n\u0005\u000049\u0003"+
		"\u0010\b\u000059\u0003\u001a\r\u000069\u0003 \u0010\u000079\u0003\"\u0011"+
		"\u000080\u0001\u0000\u0000\u000081\u0001\u0000\u0000\u000082\u0001\u0000"+
		"\u0000\u000083\u0001\u0000\u0000\u000084\u0001\u0000\u0000\u000085\u0001"+
		"\u0000\u0000\u000086\u0001\u0000\u0000\u000087\u0001\u0000\u0000\u0000"+
		"9\u0003\u0001\u0000\u0000\u0000:;\u0005\u0001\u0000\u0000;<\u0005\u0002"+
		"\u0000\u0000<=\u0005\u0003\u0000\u0000=>\u0005\u001b\u0000\u0000>?\u0005"+
		"\u0004\u0000\u0000?\u0005\u0001\u0000\u0000\u0000@A\u0005\u0005\u0000"+
		"\u0000AB\u0005\u0006\u0000\u0000BC\u0005\u0004\u0000\u0000C\u0007\u0001"+
		"\u0000\u0000\u0000DE\u0005\u0007\u0000\u0000EG\u0005\b\u0000\u0000FH\u0003"+
		"\u000e\u0007\u0000GF\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"HI\u0001\u0000\u0000\u0000IJ\u0005\u0004\u0000\u0000J\t\u0001\u0000\u0000"+
		"\u0000KL\u0005\u0007\u0000\u0000LM\u0005\t\u0000\u0000MO\u0005\u001b\u0000"+
		"\u0000NP\u0003\f\u0006\u0000ON\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000"+
		"\u0000PR\u0001\u0000\u0000\u0000QS\u0003\u000e\u0007\u0000RQ\u0001\u0000"+
		"\u0000\u0000RS\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000TU\u0005"+
		"\u0004\u0000\u0000U\u000b\u0001\u0000\u0000\u0000VW\u0005\n\u0000\u0000"+
		"WX\u0005\u001b\u0000\u0000X\r\u0001\u0000\u0000\u0000YZ\u0005\u000b\u0000"+
		"\u0000Z[\u0005\f\u0000\u0000[\\\u0005\u001b\u0000\u0000\\\u000f\u0001"+
		"\u0000\u0000\u0000]^\u0005\u0007\u0000\u0000^`\u0005\r\u0000\u0000_a\u0003"+
		"\u0012\t\u0000`_\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000ac\u0001"+
		"\u0000\u0000\u0000bd\u0003\u0014\n\u0000cb\u0001\u0000\u0000\u0000cd\u0001"+
		"\u0000\u0000\u0000df\u0001\u0000\u0000\u0000eg\u0003\u000e\u0007\u0000"+
		"fe\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000\u0000gi\u0001\u0000\u0000"+
		"\u0000hj\u0003\u0016\u000b\u0000ih\u0001\u0000\u0000\u0000ij\u0001\u0000"+
		"\u0000\u0000jl\u0001\u0000\u0000\u0000km\u0003\u0018\f\u0000lk\u0001\u0000"+
		"\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000no\u0005"+
		"\u0004\u0000\u0000o\u0011\u0001\u0000\u0000\u0000pq\u0005\u000e\u0000"+
		"\u0000qr\u0005\u001b\u0000\u0000r\u0013\u0001\u0000\u0000\u0000st\u0005"+
		"\u000f\u0000\u0000tu\u0005\u001b\u0000\u0000u\u0015\u0001\u0000\u0000"+
		"\u0000vw\u0005\u000b\u0000\u0000wx\u0005\u0010\u0000\u0000xy\u0005\f\u0000"+
		"\u0000yz\u0005\u001b\u0000\u0000z\u0017\u0001\u0000\u0000\u0000{|\u0005"+
		"\u000b\u0000\u0000|}\u0005\u0011\u0000\u0000}~\u0005\f\u0000\u0000~\u007f"+
		"\u0005\u001b\u0000\u0000\u007f\u0019\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0005\u0012\u0000\u0000\u0081\u0082\u0005\f\u0000\u0000\u0082\u0083\u0005"+
		"\u001b\u0000\u0000\u0083\u0084\u0005\n\u0000\u0000\u0084\u0086\u0005\u001b"+
		"\u0000\u0000\u0085\u0087\u0003\u001c\u000e\u0000\u0086\u0085\u0001\u0000"+
		"\u0000\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087\u0088\u0001\u0000"+
		"\u0000\u0000\u0088\u0089\u0005\u0004\u0000\u0000\u0089\u001b\u0001\u0000"+
		"\u0000\u0000\u008a\u008b\u0005\u000b\u0000\u0000\u008b\u008c\u0003\u001e"+
		"\u000f\u0000\u008c\u008d\u0005\u0013\u0000\u0000\u008d\u001d\u0001\u0000"+
		"\u0000\u0000\u008e\u008f\u0007\u0000\u0000\u0000\u008f\u001f\u0001\u0000"+
		"\u0000\u0000\u0090\u0091\u0005\u0012\u0000\u0000\u0091\u0092\u0005\u0016"+
		"\u0000\u0000\u0092\u0093\u0005\u001b\u0000\u0000\u0093\u0094\u0005\n\u0000"+
		"\u0000\u0094\u0095\u0005\u001b\u0000\u0000\u0095\u0096\u0005\u0004\u0000"+
		"\u0000\u0096!\u0001\u0000\u0000\u0000\u0097\u0098\u0005\u0017\u0000\u0000"+
		"\u0098\u0099\u0005\n\u0000\u0000\u0099\u009b\u0005\u001b\u0000\u0000\u009a"+
		"\u009c\u0003$\u0012\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009b\u009c"+
		"\u0001\u0000\u0000\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u009f"+
		"\u0003&\u0013\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009e\u009f\u0001"+
		"\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005"+
		"\u0004\u0000\u0000\u00a1#\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005\u0018"+
		"\u0000\u0000\u00a3\u00a4\u0005\u001b\u0000\u0000\u00a4%\u0001\u0000\u0000"+
		"\u0000\u00a5\u00a6\u0005\u0019\u0000\u0000\u00a6\u00a7\u0005\u001a\u0000"+
		"\u0000\u00a7\'\u0001\u0000\u0000\u0000\r+8GOR`cfil\u0086\u009b\u009e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}