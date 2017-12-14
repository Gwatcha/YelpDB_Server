package ca.ece.ubc.cpen221.mp5;

// Generated from StructuredQueries.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class StructuredQueriesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, LParen=13, RParen=14, NUM=15, STRING=16, 
		WS=17;
	public static final int
		RULE_root = 0, RULE_expr = 1, RULE_orExpr = 2, RULE_andExpr = 3, RULE_atom = 4, 
		RULE_or = 5, RULE_and = 6, RULE_ineq = 7, RULE_gt = 8, RULE_gte = 9, RULE_lt = 10, 
		RULE_lte = 11, RULE_eq = 12, RULE_in = 13, RULE_category = 14, RULE_name = 15, 
		RULE_rating = 16, RULE_price = 17, RULE_orWithinAtom = 18;
	public static final String[] ruleNames = {
		"root", "expr", "orExpr", "andExpr", "atom", "or", "and", "ineq", "gt", 
		"gte", "lt", "lte", "eq", "in", "category", "name", "rating", "price", 
		"orWithinAtom"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'||'", "'&&'", "'>'", "'>='", "'<'", "'<='", "'='", "'in'", "'category'", 
		"'name'", "'rating'", "'price'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "LParen", "RParen", "NUM", "STRING", "WS"
	};
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
	public String getGrammarFileName() { return "StructuredQueries.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public StructuredQueriesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RootContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitRoot(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			expr();
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

	public static class ExprContext extends ParserRuleContext {
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expr);
		try {
			setState(43);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				orExpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				andExpr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(42);
				atom();
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

	public static class OrExprContext extends ParserRuleContext {
		public List<AndExprContext> andExpr() {
			return getRuleContexts(AndExprContext.class);
		}
		public AndExprContext andExpr(int i) {
			return getRuleContext(AndExprContext.class,i);
		}
		public List<OrContext> or() {
			return getRuleContexts(OrContext.class);
		}
		public OrContext or(int i) {
			return getRuleContext(OrContext.class,i);
		}
		public OrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitOrExpr(this);
		}
	}

	public final OrExprContext orExpr() throws RecognitionException {
		OrExprContext _localctx = new OrExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_orExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			andExpr();
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(46);
				or();
				setState(47);
				andExpr();
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class AndExprContext extends ParserRuleContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<AndContext> and() {
			return getRuleContexts(AndContext.class);
		}
		public AndContext and(int i) {
			return getRuleContext(AndContext.class,i);
		}
		public AndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitAndExpr(this);
		}
	}

	public final AndExprContext andExpr() throws RecognitionException {
		AndExprContext _localctx = new AndExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_andExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			atom();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(55);
				and();
				setState(56);
				atom();
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class AtomContext extends ParserRuleContext {
		public InContext in() {
			return getRuleContext(InContext.class,0);
		}
		public CategoryContext category() {
			return getRuleContext(CategoryContext.class,0);
		}
		public RatingContext rating() {
			return getRuleContext(RatingContext.class,0);
		}
		public PriceContext price() {
			return getRuleContext(PriceContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public OrWithinAtomContext orWithinAtom() {
			return getRuleContext(OrWithinAtomContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atom);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(63);
				in();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				category();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(65);
				rating();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 4);
				{
				setState(66);
				price();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 5);
				{
				setState(67);
				name();
				}
				break;
			case LParen:
				enterOuterAlt(_localctx, 6);
				{
				setState(68);
				orWithinAtom();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class OrContext extends ParserRuleContext {
		public OrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitOr(this);
		}
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__0);
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

	public static class AndContext extends ParserRuleContext {
		public AndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitAnd(this);
		}
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T__1);
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

	public static class IneqContext extends ParserRuleContext {
		public GtContext gt() {
			return getRuleContext(GtContext.class,0);
		}
		public GteContext gte() {
			return getRuleContext(GteContext.class,0);
		}
		public LtContext lt() {
			return getRuleContext(LtContext.class,0);
		}
		public LteContext lte() {
			return getRuleContext(LteContext.class,0);
		}
		public EqContext eq() {
			return getRuleContext(EqContext.class,0);
		}
		public IneqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ineq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterIneq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitIneq(this);
		}
	}

	public final IneqContext ineq() throws RecognitionException {
		IneqContext _localctx = new IneqContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ineq);
		try {
			setState(80);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				gt();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				gte();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(77);
				lt();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 4);
				{
				setState(78);
				lte();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 5);
				{
				setState(79);
				eq();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class GtContext extends ParserRuleContext {
		public GtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterGt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitGt(this);
		}
	}

	public final GtContext gt() throws RecognitionException {
		GtContext _localctx = new GtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_gt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(T__2);
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

	public static class GteContext extends ParserRuleContext {
		public GteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gte; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterGte(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitGte(this);
		}
	}

	public final GteContext gte() throws RecognitionException {
		GteContext _localctx = new GteContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_gte);
		try {
			enterOuterAlt(_localctx, 1);
			{
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

	public static class LtContext extends ParserRuleContext {
		public LtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterLt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitLt(this);
		}
	}

	public final LtContext lt() throws RecognitionException {
		LtContext _localctx = new LtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_lt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(T__4);
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

	public static class LteContext extends ParserRuleContext {
		public LteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lte; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterLte(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitLte(this);
		}
	}

	public final LteContext lte() throws RecognitionException {
		LteContext _localctx = new LteContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_lte);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(T__5);
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

	public static class EqContext extends ParserRuleContext {
		public EqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitEq(this);
		}
	}

	public final EqContext eq() throws RecognitionException {
		EqContext _localctx = new EqContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_eq);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__6);
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

	public static class InContext extends ParserRuleContext {
		public TerminalNode LParen() { return getToken(StructuredQueriesParser.LParen, 0); }
		public TerminalNode STRING() { return getToken(StructuredQueriesParser.STRING, 0); }
		public TerminalNode RParen() { return getToken(StructuredQueriesParser.RParen, 0); }
		public InContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_in; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitIn(this);
		}
	}

	public final InContext in() throws RecognitionException {
		InContext _localctx = new InContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_in);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(T__7);
			setState(93);
			match(LParen);
			setState(94);
			match(STRING);
			setState(95);
			match(RParen);
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

	public static class CategoryContext extends ParserRuleContext {
		public TerminalNode LParen() { return getToken(StructuredQueriesParser.LParen, 0); }
		public TerminalNode STRING() { return getToken(StructuredQueriesParser.STRING, 0); }
		public TerminalNode RParen() { return getToken(StructuredQueriesParser.RParen, 0); }
		public CategoryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterCategory(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitCategory(this);
		}
	}

	public final CategoryContext category() throws RecognitionException {
		CategoryContext _localctx = new CategoryContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_category);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__8);
			setState(98);
			match(LParen);
			setState(99);
			match(STRING);
			setState(100);
			match(RParen);
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

	public static class NameContext extends ParserRuleContext {
		public TerminalNode LParen() { return getToken(StructuredQueriesParser.LParen, 0); }
		public TerminalNode STRING() { return getToken(StructuredQueriesParser.STRING, 0); }
		public TerminalNode RParen() { return getToken(StructuredQueriesParser.RParen, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__9);
			setState(103);
			match(LParen);
			setState(104);
			match(STRING);
			setState(105);
			match(RParen);
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

	public static class RatingContext extends ParserRuleContext {
		public IneqContext ineq() {
			return getRuleContext(IneqContext.class,0);
		}
		public TerminalNode NUM() { return getToken(StructuredQueriesParser.NUM, 0); }
		public RatingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rating; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterRating(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitRating(this);
		}
	}

	public final RatingContext rating() throws RecognitionException {
		RatingContext _localctx = new RatingContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_rating);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(T__10);
			setState(108);
			ineq();
			setState(109);
			match(NUM);
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

	public static class PriceContext extends ParserRuleContext {
		public IneqContext ineq() {
			return getRuleContext(IneqContext.class,0);
		}
		public TerminalNode NUM() { return getToken(StructuredQueriesParser.NUM, 0); }
		public PriceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_price; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterPrice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitPrice(this);
		}
	}

	public final PriceContext price() throws RecognitionException {
		PriceContext _localctx = new PriceContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_price);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(T__11);
			setState(112);
			ineq();
			setState(113);
			match(NUM);
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

	public static class OrWithinAtomContext extends ParserRuleContext {
		public TerminalNode LParen() { return getToken(StructuredQueriesParser.LParen, 0); }
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public TerminalNode RParen() { return getToken(StructuredQueriesParser.RParen, 0); }
		public OrWithinAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orWithinAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).enterOrWithinAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuredQueriesListener ) ((StructuredQueriesListener)listener).exitOrWithinAtom(this);
		}
	}

	public final OrWithinAtomContext orWithinAtom() throws RecognitionException {
		OrWithinAtomContext _localctx = new OrWithinAtomContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_orWithinAtom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(LParen);
			setState(116);
			orExpr();
			setState(117);
			match(RParen);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\4\23"+
		"\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\3\5\3.\n\3\3\4\3\4\3\4\3\4\7\4\64\n"+
		"\4\f\4\16\4\67\13\4\3\5\3\5\3\5\3\5\7\5=\n\5\f\5\16\5@\13\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\5\6H\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\tS\n\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\2\2\25\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&\2\2\2s\2(\3\2\2\2\4-\3\2\2\2\6/\3\2\2\2\b8\3\2"+
		"\2\2\nG\3\2\2\2\fI\3\2\2\2\16K\3\2\2\2\20R\3\2\2\2\22T\3\2\2\2\24V\3\2"+
		"\2\2\26X\3\2\2\2\30Z\3\2\2\2\32\\\3\2\2\2\34^\3\2\2\2\36c\3\2\2\2 h\3"+
		"\2\2\2\"m\3\2\2\2$q\3\2\2\2&u\3\2\2\2()\5\4\3\2)\3\3\2\2\2*.\5\6\4\2+"+
		".\5\b\5\2,.\5\n\6\2-*\3\2\2\2-+\3\2\2\2-,\3\2\2\2.\5\3\2\2\2/\65\5\b\5"+
		"\2\60\61\5\f\7\2\61\62\5\b\5\2\62\64\3\2\2\2\63\60\3\2\2\2\64\67\3\2\2"+
		"\2\65\63\3\2\2\2\65\66\3\2\2\2\66\7\3\2\2\2\67\65\3\2\2\28>\5\n\6\29:"+
		"\5\16\b\2:;\5\n\6\2;=\3\2\2\2<9\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2"+
		"?\t\3\2\2\2@>\3\2\2\2AH\5\34\17\2BH\5\36\20\2CH\5\"\22\2DH\5$\23\2EH\5"+
		" \21\2FH\5&\24\2GA\3\2\2\2GB\3\2\2\2GC\3\2\2\2GD\3\2\2\2GE\3\2\2\2GF\3"+
		"\2\2\2H\13\3\2\2\2IJ\7\3\2\2J\r\3\2\2\2KL\7\4\2\2L\17\3\2\2\2MS\5\22\n"+
		"\2NS\5\24\13\2OS\5\26\f\2PS\5\30\r\2QS\5\32\16\2RM\3\2\2\2RN\3\2\2\2R"+
		"O\3\2\2\2RP\3\2\2\2RQ\3\2\2\2S\21\3\2\2\2TU\7\5\2\2U\23\3\2\2\2VW\7\6"+
		"\2\2W\25\3\2\2\2XY\7\7\2\2Y\27\3\2\2\2Z[\7\b\2\2[\31\3\2\2\2\\]\7\t\2"+
		"\2]\33\3\2\2\2^_\7\n\2\2_`\7\17\2\2`a\7\22\2\2ab\7\20\2\2b\35\3\2\2\2"+
		"cd\7\13\2\2de\7\17\2\2ef\7\22\2\2fg\7\20\2\2g\37\3\2\2\2hi\7\f\2\2ij\7"+
		"\17\2\2jk\7\22\2\2kl\7\20\2\2l!\3\2\2\2mn\7\r\2\2no\5\20\t\2op\7\21\2"+
		"\2p#\3\2\2\2qr\7\16\2\2rs\5\20\t\2st\7\21\2\2t%\3\2\2\2uv\7\17\2\2vw\5"+
		"\6\4\2wx\7\20\2\2x\'\3\2\2\2\7-\65>GR";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}