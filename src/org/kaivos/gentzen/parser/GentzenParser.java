// Generated from Gentzen.g4 by ANTLR 4.13.2

package org.kaivos.gentzen.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.kaivos.gentzen.DeclDef;
import org.kaivos.gentzen.DefinedType;
import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.Kind;
import org.kaivos.gentzen.Rule;
import org.kaivos.gentzen.RuleSignature;
import org.kaivos.gentzen.TypeAlias;
import org.kaivos.gentzen.Typeclass;
import org.kaivos.gentzen.Utils;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GentzenParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, IDENT=34, NUM=35, CHAR=36, STRING=37, WS=38, EQ=39, 
		LINE_COMMENT=40;
	public static final int
		RULE_gentzenFile = 0, RULE_importDecl = 1, RULE_exportDecl = 2, RULE_ruleDecl = 3, 
		RULE_ruleDef = 4, RULE_ruleSignature = 5, RULE_sequent = 6, RULE_type = 7, 
		RULE_expression = 8, RULE_expressionTermLeft = 9, RULE_expressionTermRight = 10, 
		RULE_expressionArg = 11, RULE_arule = 12, RULE_identlist = 13, RULE_symbol = 14, 
		RULE_application = 15, RULE_typealias = 16, RULE_typeclass = 17, RULE_kind = 18, 
		RULE_cutEliminationRuleDef = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"gentzenFile", "importDecl", "exportDecl", "ruleDecl", "ruleDef", "ruleSignature", 
			"sequent", "type", "expression", "expressionTermLeft", "expressionTermRight", 
			"expressionArg", "arule", "identlist", "symbol", "application", "typealias", 
			"typeclass", "kind", "cutEliminationRuleDef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<-'", "';'", "'->'", "':'", "'='", "'('", "')'", "'/'", "'*'", 
			"'$'", "','", "'|-'", "'?'", "'~'", "'/\\'", "'\\/'", "'@'", "'|\\|'", 
			"'++'", "'_|_'", "'..'", "'[]'", "'<>'", "'!'", "'+'", "'['", "']'", 
			"':='", "'???'", "'{'", "'}'", "'&'", "'%'", null, null, null, null, 
			null, "'=='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "IDENT", 
			"NUM", "CHAR", "STRING", "WS", "EQ", "LINE_COMMENT"
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
	public String getGrammarFileName() { return "Gentzen.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GentzenParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GentzenFileContext extends ParserRuleContext {
		public ArrayList<DeclDef> decldefs;
		public ImportDeclContext importDecl;
		public RuleDeclContext ruleDecl;
		public RuleDefContext ruleDef;
		public TypealiasContext typealias;
		public TypeclassContext typeclass;
		public ExportDeclContext exportDecl;
		public List<ImportDeclContext> importDecl() {
			return getRuleContexts(ImportDeclContext.class);
		}
		public ImportDeclContext importDecl(int i) {
			return getRuleContext(ImportDeclContext.class,i);
		}
		public List<RuleDeclContext> ruleDecl() {
			return getRuleContexts(RuleDeclContext.class);
		}
		public RuleDeclContext ruleDecl(int i) {
			return getRuleContext(RuleDeclContext.class,i);
		}
		public List<RuleDefContext> ruleDef() {
			return getRuleContexts(RuleDefContext.class);
		}
		public RuleDefContext ruleDef(int i) {
			return getRuleContext(RuleDefContext.class,i);
		}
		public List<TypealiasContext> typealias() {
			return getRuleContexts(TypealiasContext.class);
		}
		public TypealiasContext typealias(int i) {
			return getRuleContext(TypealiasContext.class,i);
		}
		public List<TypeclassContext> typeclass() {
			return getRuleContexts(TypeclassContext.class);
		}
		public TypeclassContext typeclass(int i) {
			return getRuleContext(TypeclassContext.class,i);
		}
		public List<ExportDeclContext> exportDecl() {
			return getRuleContexts(ExportDeclContext.class);
		}
		public ExportDeclContext exportDecl(int i) {
			return getRuleContext(ExportDeclContext.class,i);
		}
		public GentzenFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gentzenFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterGentzenFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitGentzenFile(this);
		}
	}

	public final GentzenFileContext gentzenFile() throws RecognitionException {
		GentzenFileContext _localctx = new GentzenFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_gentzenFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			((GentzenFileContext)_localctx).decldefs =  new ArrayList<>();
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(41);
				((GentzenFileContext)_localctx).importDecl = importDecl();
				_localctx.decldefs.add(((GentzenFileContext)_localctx).importDecl.d);
				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__29 || _la==IDENT) {
				{
				setState(61);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(49);
					((GentzenFileContext)_localctx).ruleDecl = ruleDecl();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).ruleDecl.r);
					}
					break;
				case 2:
					{
					setState(52);
					((GentzenFileContext)_localctx).ruleDef = ruleDef();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).ruleDef.rd);
					}
					break;
				case 3:
					{
					setState(55);
					((GentzenFileContext)_localctx).typealias = typealias();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).typealias.ta);
					}
					break;
				case 4:
					{
					setState(58);
					((GentzenFileContext)_localctx).typeclass = typeclass();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).typeclass.tc);
					}
					break;
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(66);
				((GentzenFileContext)_localctx).exportDecl = exportDecl();
				_localctx.decldefs.add(((GentzenFileContext)_localctx).exportDecl.d);
				}
				}
				setState(73);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ImportDeclContext extends ParserRuleContext {
		public DeclDef.ImportDecl d;
		public Token STRING;
		public TerminalNode STRING() { return getToken(GentzenParser.STRING, 0); }
		public ImportDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterImportDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitImportDecl(this);
		}
	}

	public final ImportDeclContext importDecl() throws RecognitionException {
		ImportDeclContext _localctx = new ImportDeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(T__0);
			setState(75);
			((ImportDeclContext)_localctx).STRING = match(STRING);
			setState(76);
			match(T__1);
			((ImportDeclContext)_localctx).d =  new DeclDef.ImportDecl(Utils.parseString((((ImportDeclContext)_localctx).STRING!=null?((ImportDeclContext)_localctx).STRING.getText():null)), _localctx.start, _localctx.stop);
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
	public static class ExportDeclContext extends ParserRuleContext {
		public DeclDef.ExportDecl d;
		public ArrayList<String> names = new ArrayList<>();
		public Token IDENT;
		public List<TerminalNode> IDENT() { return getTokens(GentzenParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(GentzenParser.IDENT, i);
		}
		public ExportDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterExportDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitExportDecl(this);
		}
	}

	public final ExportDeclContext exportDecl() throws RecognitionException {
		ExportDeclContext _localctx = new ExportDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_exportDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(T__2);
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENT) {
				{
				{
				setState(80);
				((ExportDeclContext)_localctx).IDENT = match(IDENT);
				_localctx.names.add((((ExportDeclContext)_localctx).IDENT!=null?((ExportDeclContext)_localctx).IDENT.getText():null));
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			match(T__1);
			}
			_ctx.stop = _input.LT(-1);
			((ExportDeclContext)_localctx).d =  new DeclDef.ExportDecl(_localctx.names, _localctx.start, _localctx.stop);
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
	public static class RuleDeclContext extends ParserRuleContext {
		public DeclDef.RuleDeclaration r;
		public String name;
		public RuleSignature rs;
		public Token nametoken;
		public RuleSignatureContext rulesig;
		public TerminalNode IDENT() { return getToken(GentzenParser.IDENT, 0); }
		public RuleSignatureContext ruleSignature() {
			return getRuleContext(RuleSignatureContext.class,0);
		}
		public RuleDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterRuleDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitRuleDecl(this);
		}
	}

	public final RuleDeclContext ruleDecl() throws RecognitionException {
		RuleDeclContext _localctx = new RuleDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ruleDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			((RuleDeclContext)_localctx).nametoken = match(IDENT);
			setState(90);
			match(T__3);
			setState(91);
			((RuleDeclContext)_localctx).rulesig = ruleSignature();
			setState(92);
			match(T__1);
			}
			_ctx.stop = _input.LT(-1);
			((RuleDeclContext)_localctx).r =  new DeclDef.RuleDeclaration((((RuleDeclContext)_localctx).nametoken!=null?((RuleDeclContext)_localctx).nametoken.getText():null), ((RuleDeclContext)_localctx).rulesig.rs, _localctx.start, _localctx.stop);
			    ((RuleDeclContext)_localctx).name = (((RuleDeclContext)_localctx).nametoken!=null?((RuleDeclContext)_localctx).nametoken.getText():null); ((RuleDeclContext)_localctx).rs = ((RuleDeclContext)_localctx).rulesig.rs;
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
	public static class RuleDefContext extends ParserRuleContext {
		public DeclDef.RuleDefinition rd;
		public Token name;
		public ExpressionContext expr;
		public TerminalNode IDENT() { return getToken(GentzenParser.IDENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RuleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterRuleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitRuleDef(this);
		}
	}

	public final RuleDefContext ruleDef() throws RecognitionException {
		RuleDefContext _localctx = new RuleDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ruleDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			((RuleDefContext)_localctx).name = match(IDENT);
			setState(95);
			match(T__4);
			setState(96);
			((RuleDefContext)_localctx).expr = expression();
			setState(97);
			match(T__1);
			}
			_ctx.stop = _input.LT(-1);
			((RuleDefContext)_localctx).rd =  new DeclDef.RuleDefinition((((RuleDefContext)_localctx).name!=null?((RuleDefContext)_localctx).name.getText():null), ((RuleDefContext)_localctx).expr.expr, _localctx.start, _localctx.stop);
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
	public static class RuleSignatureContext extends ParserRuleContext {
		public RuleSignature rs;
		public SequentContext sequent;
		public List<SequentContext> sequent() {
			return getRuleContexts(SequentContext.class);
		}
		public SequentContext sequent(int i) {
			return getRuleContext(SequentContext.class,i);
		}
		public RuleSignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleSignature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterRuleSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitRuleSignature(this);
		}
	}

	public final RuleSignatureContext ruleSignature() throws RecognitionException {
		RuleSignatureContext _localctx = new RuleSignatureContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_ruleSignature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			ArrayList<RuleSignature.Sequent> aboves = new ArrayList<>();
			setState(100);
			match(T__5);
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 5632L) != 0)) {
				{
				setState(101);
				((RuleSignatureContext)_localctx).sequent = sequent();
				aboves.add(((RuleSignatureContext)_localctx).sequent.seq);
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(103);
					match(T__1);
					setState(104);
					((RuleSignatureContext)_localctx).sequent = sequent();
					aboves.add(((RuleSignatureContext)_localctx).sequent.seq);
					}
					}
					setState(111);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(114);
			match(T__6);
			setState(115);
			match(T__7);
			setState(116);
			match(T__5);
			setState(117);
			((RuleSignatureContext)_localctx).sequent = sequent();
			RuleSignature.Sequent below = ((RuleSignatureContext)_localctx).sequent.seq;
			setState(119);
			match(T__6);
			((RuleSignatureContext)_localctx).rs =  new RuleSignature(aboves, below);
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
	public static class SequentContext extends ParserRuleContext {
		public RuleSignature.Sequent seq;
		public Token IDENT;
		public Token NUM;
		public TypeContext type;
		public List<TerminalNode> IDENT() { return getTokens(GentzenParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(GentzenParser.IDENT, i);
		}
		public List<TerminalNode> NUM() { return getTokens(GentzenParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(GentzenParser.NUM, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public SequentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterSequent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitSequent(this);
		}
	}

	public final SequentContext sequent() throws RecognitionException {
		SequentContext _localctx = new SequentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_sequent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

			    ArrayList<RuleSignature.ContextVarParameter> contextVars = new ArrayList<>();
			    HashMap<Integer, RuleSignature.SequentParameter> parameters = new HashMap<>();
			    
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(133);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(123);
					match(T__8);
					setState(124);
					((SequentContext)_localctx).IDENT = match(IDENT);
					contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar((((SequentContext)_localctx).IDENT!=null?((SequentContext)_localctx).IDENT.getText():null), 0), true));
					}
					break;
				case T__9:
					{
					setState(126);
					match(T__9);
					setState(127);
					((SequentContext)_localctx).NUM = match(NUM);
					setState(128);
					match(T__5);
					setState(129);
					((SequentContext)_localctx).type = type(0);
					setState(130);
					match(T__6);
					parameters.put(Integer.parseInt((((SequentContext)_localctx).NUM!=null?((SequentContext)_localctx).NUM.getText():null)), new RuleSignature.SequentParameter(((SequentContext)_localctx).type.typ, true));
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(135);
					match(T__10);
					setState(146);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__8:
						{
						setState(136);
						match(T__8);
						setState(137);
						((SequentContext)_localctx).IDENT = match(IDENT);
						contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar((((SequentContext)_localctx).IDENT!=null?((SequentContext)_localctx).IDENT.getText():null), 0), true));
						}
						break;
					case T__9:
						{
						setState(139);
						match(T__9);
						setState(140);
						((SequentContext)_localctx).NUM = match(NUM);
						setState(141);
						match(T__5);
						setState(142);
						((SequentContext)_localctx).type = type(0);
						setState(143);
						match(T__6);
						parameters.put(Integer.parseInt((((SequentContext)_localctx).NUM!=null?((SequentContext)_localctx).NUM.getText():null)), new RuleSignature.SequentParameter(((SequentContext)_localctx).type.typ, true));
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					}
					setState(152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(155);
			match(T__11);
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(166);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(156);
					match(T__8);
					setState(157);
					((SequentContext)_localctx).IDENT = match(IDENT);
					contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar((((SequentContext)_localctx).IDENT!=null?((SequentContext)_localctx).IDENT.getText():null), 0), false));
					}
					break;
				case T__9:
					{
					setState(159);
					match(T__9);
					setState(160);
					((SequentContext)_localctx).NUM = match(NUM);
					setState(161);
					match(T__5);
					setState(162);
					((SequentContext)_localctx).type = type(0);
					setState(163);
					match(T__6);
					parameters.put(Integer.parseInt((((SequentContext)_localctx).NUM!=null?((SequentContext)_localctx).NUM.getText():null)), new RuleSignature.SequentParameter(((SequentContext)_localctx).type.typ, false));
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(168);
					match(T__10);
					setState(179);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__8:
						{
						setState(169);
						match(T__8);
						setState(170);
						((SequentContext)_localctx).IDENT = match(IDENT);
						contextVars.add(new RuleSignature.ContextVarParameter(new RuleSignature.ContextVar((((SequentContext)_localctx).IDENT!=null?((SequentContext)_localctx).IDENT.getText():null), 0), false));
						}
						break;
					case T__9:
						{
						setState(172);
						match(T__9);
						setState(173);
						((SequentContext)_localctx).NUM = match(NUM);
						setState(174);
						match(T__5);
						setState(175);
						((SequentContext)_localctx).type = type(0);
						setState(176);
						match(T__6);
						parameters.put(Integer.parseInt((((SequentContext)_localctx).NUM!=null?((SequentContext)_localctx).NUM.getText():null)), new RuleSignature.SequentParameter(((SequentContext)_localctx).type.typ, false));
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					}
					setState(185);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			((SequentContext)_localctx).seq =  new RuleSignature.Sequent(parameters, Map.of(), contextVars);
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
	public static class TypeContext extends ParserRuleContext {
		public ExpressionType typ;
		public TypeContext left;
		public TypeContext type;
		public Token IDENT;
		public Token name;
		public TypeContext right;
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TerminalNode IDENT() { return getToken(GentzenParser.IDENT, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				{
				setState(191);
				match(T__5);
				setState(192);
				((TypeContext)_localctx).type = type(0);
				setState(193);
				match(T__6);
				((TypeContext)_localctx).typ =  ((TypeContext)_localctx).type.typ;
				}
				break;
			case T__12:
				{
				setState(196);
				match(T__12);
				setState(197);
				((TypeContext)_localctx).IDENT = match(IDENT);
				((TypeContext)_localctx).typ =  new ExpressionType.TypeVar((((TypeContext)_localctx).IDENT!=null?((TypeContext)_localctx).IDENT.getText():null), 0);
				}
				break;
			case T__13:
				{
				setState(199);
				match(T__13);
				setState(200);
				((TypeContext)_localctx).type = type(12);
				((TypeContext)_localctx).typ =  new ExpressionType.Not(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__16:
				{
				setState(203);
				match(T__16);
				setState(204);
				((TypeContext)_localctx).type = type(9);
				((TypeContext)_localctx).typ =  new ExpressionType.Maybe(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__17:
				{
				setState(207);
				match(T__17);
				((TypeContext)_localctx).typ =  new ExpressionType.Nat();
				}
				break;
			case T__18:
				{
				setState(209);
				match(T__18);
				((TypeContext)_localctx).typ =  new ExpressionType.Unit();
				}
				break;
			case T__19:
				{
				setState(211);
				match(T__19);
				((TypeContext)_localctx).typ =  new ExpressionType.Bottom();
				}
				break;
			case T__20:
				{
				setState(213);
				match(T__20);
				setState(214);
				((TypeContext)_localctx).type = type(5);
				((TypeContext)_localctx).typ =  new ExpressionType.List(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__21:
				{
				setState(217);
				match(T__21);
				setState(218);
				((TypeContext)_localctx).type = type(4);
				((TypeContext)_localctx).typ =  new ExpressionType.Box(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__22:
				{
				setState(221);
				match(T__22);
				setState(222);
				((TypeContext)_localctx).type = type(3);
				((TypeContext)_localctx).typ =  new ExpressionType.Not(new ExpressionType.Box(new ExpressionType.Not(((TypeContext)_localctx).type.typ)));
				}
				break;
			case T__23:
				{
				setState(225);
				match(T__23);
				setState(226);
				((TypeContext)_localctx).IDENT = match(IDENT);
				((TypeContext)_localctx).typ =  new ExpressionType.Atomic((((TypeContext)_localctx).IDENT!=null?((TypeContext)_localctx).IDENT.getText():null));
				}
				break;
			case IDENT:
				{
				setState(228);
				((TypeContext)_localctx).name = match(IDENT);
				ArrayList<ExpressionType> typeArgs = new ArrayList<>();
				setState(235);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(230);
						((TypeContext)_localctx).type = type(0);
						typeArgs.add(((TypeContext)_localctx).type.typ);
						}
						} 
					}
					setState(237);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				}
				((TypeContext)_localctx).typ =  new ExpressionType.CustomType((((TypeContext)_localctx).name!=null?((TypeContext)_localctx).name.getText():null), typeArgs);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(253);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(251);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new TypeContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(241);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(242);
						match(T__14);
						setState(243);
						((TypeContext)_localctx).right = ((TypeContext)_localctx).type = type(12);
						((TypeContext)_localctx).typ =  new ExpressionType.And(((TypeContext)_localctx).left.typ, ((TypeContext)_localctx).right.typ);
						}
						break;
					case 2:
						{
						_localctx = new TypeContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(246);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(247);
						match(T__15);
						setState(248);
						((TypeContext)_localctx).right = ((TypeContext)_localctx).type = type(11);
						((TypeContext)_localctx).typ =  new ExpressionType.Or(((TypeContext)_localctx).left.typ, ((TypeContext)_localctx).right.typ);
						}
						break;
					}
					} 
				}
				setState(255);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public Expression expr = null;
		public ExpressionTermLeftContext expressionTermLeft;
		public ExpressionTermRightContext expressionTermRight;
		public ExpressionTermRightContext expressionTermRight() {
			return getRuleContext(ExpressionTermRightContext.class,0);
		}
		public ExpressionTermLeftContext expressionTermLeft() {
			return getRuleContext(ExpressionTermLeftContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(256);
				((ExpressionContext)_localctx).expressionTermLeft = expressionTermLeft();
				setState(257);
				match(T__24);
				((ExpressionContext)_localctx).expr =  ((ExpressionContext)_localctx).expressionTermLeft.expr;
				}
				break;
			}
			setState(262);
			((ExpressionContext)_localctx).expressionTermRight = expressionTermRight();
			((ExpressionContext)_localctx).expr =  _localctx.expr == null ? ((ExpressionContext)_localctx).expressionTermRight.expr : new Expression.Application("cut", List.of(_localctx.expr, ((ExpressionContext)_localctx).expressionTermRight.expr), (((ExpressionContext)_localctx).expressionTermLeft!=null?(((ExpressionContext)_localctx).expressionTermLeft.start):null), (((ExpressionContext)_localctx).expressionTermRight!=null?(((ExpressionContext)_localctx).expressionTermRight.stop):null));
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
	public static class ExpressionTermLeftContext extends ParserRuleContext {
		public Expression expr;
		public ExpressionArgContext expressionArg;
		public ApplicationContext application;
		public ExpressionArgContext expressionArg() {
			return getRuleContext(ExpressionArgContext.class,0);
		}
		public ApplicationContext application() {
			return getRuleContext(ApplicationContext.class,0);
		}
		public ExpressionTermLeftContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionTermLeft; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterExpressionTermLeft(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitExpressionTermLeft(this);
		}
	}

	public final ExpressionTermLeftContext expressionTermLeft() throws RecognitionException {
		ExpressionTermLeftContext _localctx = new ExpressionTermLeftContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expressionTermLeft);
		try {
			setState(271);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(265);
				((ExpressionTermLeftContext)_localctx).expressionArg = expressionArg();
				((ExpressionTermLeftContext)_localctx).expr =  ((ExpressionTermLeftContext)_localctx).expressionArg.expr;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(268);
				((ExpressionTermLeftContext)_localctx).application = application();
				((ExpressionTermLeftContext)_localctx).expr =  ((ExpressionTermLeftContext)_localctx).application.app;
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
	public static class ExpressionTermRightContext extends ParserRuleContext {
		public Expression expr;
		public ExpressionArgContext expressionArg;
		public ApplicationContext application;
		public AruleContext arule;
		public ExpressionArgContext expressionArg() {
			return getRuleContext(ExpressionArgContext.class,0);
		}
		public ApplicationContext application() {
			return getRuleContext(ApplicationContext.class,0);
		}
		public AruleContext arule() {
			return getRuleContext(AruleContext.class,0);
		}
		public ExpressionTermRightContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionTermRight; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterExpressionTermRight(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitExpressionTermRight(this);
		}
	}

	public final ExpressionTermRightContext expressionTermRight() throws RecognitionException {
		ExpressionTermRightContext _localctx = new ExpressionTermRightContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expressionTermRight);
		try {
			setState(282);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(273);
				((ExpressionTermRightContext)_localctx).expressionArg = expressionArg();
				((ExpressionTermRightContext)_localctx).expr =  ((ExpressionTermRightContext)_localctx).expressionArg.expr;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(276);
				((ExpressionTermRightContext)_localctx).application = application();
				((ExpressionTermRightContext)_localctx).expr =  ((ExpressionTermRightContext)_localctx).application.app;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(279);
				((ExpressionTermRightContext)_localctx).arule = arule();
				((ExpressionTermRightContext)_localctx).expr =  ((ExpressionTermRightContext)_localctx).arule.ar;
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
	public static class ExpressionArgContext extends ParserRuleContext {
		public Expression expr;
		public SymbolContext symbol;
		public Token STRING;
		public Token NUM;
		public Token CHAR;
		public ExpressionContext expression;
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public TerminalNode STRING() { return getToken(GentzenParser.STRING, 0); }
		public TerminalNode NUM() { return getToken(GentzenParser.NUM, 0); }
		public TerminalNode CHAR() { return getToken(GentzenParser.CHAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterExpressionArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitExpressionArg(this);
		}
	}

	public final ExpressionArgContext expressionArg() throws RecognitionException {
		ExpressionArgContext _localctx = new ExpressionArgContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expressionArg);
		try {
			setState(298);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(284);
				((ExpressionArgContext)_localctx).symbol = symbol();
				((ExpressionArgContext)_localctx).expr =  ((ExpressionArgContext)_localctx).symbol.sym;
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(287);
				((ExpressionArgContext)_localctx).STRING = match(STRING);
				((ExpressionArgContext)_localctx).expr =  new Expression.StringLiteral((((ExpressionArgContext)_localctx).STRING!=null?((ExpressionArgContext)_localctx).STRING.getText():null), ((ExpressionArgContext)_localctx).STRING, ((ExpressionArgContext)_localctx).STRING);
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(289);
				((ExpressionArgContext)_localctx).NUM = match(NUM);
				((ExpressionArgContext)_localctx).expr =  new Expression.NaturalLiteral(Integer.parseInt((((ExpressionArgContext)_localctx).NUM!=null?((ExpressionArgContext)_localctx).NUM.getText():null)), ((ExpressionArgContext)_localctx).NUM, ((ExpressionArgContext)_localctx).NUM);
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 4);
				{
				setState(291);
				((ExpressionArgContext)_localctx).CHAR = match(CHAR);
				((ExpressionArgContext)_localctx).expr =  new Expression.NaturalLiteral((((ExpressionArgContext)_localctx).CHAR!=null?((ExpressionArgContext)_localctx).CHAR.getText():null).charAt(1), ((ExpressionArgContext)_localctx).CHAR, ((ExpressionArgContext)_localctx).CHAR);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 5);
				{
				setState(293);
				match(T__5);
				setState(294);
				((ExpressionArgContext)_localctx).expression = expression();
				setState(295);
				match(T__6);
				((ExpressionArgContext)_localctx).expr =  ((ExpressionArgContext)_localctx).expression.expr;
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

	@SuppressWarnings("CheckReturnValue")
	public static class AruleContext extends ParserRuleContext {
		public Expression ar;
		public Token open;
		public IdentlistContext below;
		public ExpressionContext body;
		public IdentlistContext above;
		public List<IdentlistContext> identlist() {
			return getRuleContexts(IdentlistContext.class);
		}
		public IdentlistContext identlist(int i) {
			return getRuleContext(IdentlistContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterArule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitArule(this);
		}
	}

	public final AruleContext arule() throws RecognitionException {
		AruleContext _localctx = new AruleContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_arule);
		try {
			setState(314);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				((AruleContext)_localctx).open = match(T__25);
				setState(301);
				((AruleContext)_localctx).below = identlist();
				setState(302);
				match(T__26);
				setState(303);
				((AruleContext)_localctx).body = expression();
				((AruleContext)_localctx).ar =  new Expression.AnonymousRule(List.of(), ((AruleContext)_localctx).below.list, ((AruleContext)_localctx).body.expr, ((AruleContext)_localctx).open, (((AruleContext)_localctx).body!=null?(((AruleContext)_localctx).body.stop):null));
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(306);
				((AruleContext)_localctx).open = match(T__25);
				setState(307);
				((AruleContext)_localctx).above = identlist();
				setState(308);
				match(T__7);
				setState(309);
				((AruleContext)_localctx).below = identlist();
				setState(310);
				match(T__26);
				setState(311);
				((AruleContext)_localctx).body = expression();
				((AruleContext)_localctx).ar =  new Expression.AnonymousRule(((AruleContext)_localctx).above.list, ((AruleContext)_localctx).below.list, ((AruleContext)_localctx).body.expr, ((AruleContext)_localctx).open, (((AruleContext)_localctx).body!=null?(((AruleContext)_localctx).body.stop):null));
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
	public static class IdentlistContext extends ParserRuleContext {
		public ArrayList<String> list;
		public Token IDENT;
		public List<TerminalNode> IDENT() { return getTokens(GentzenParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(GentzenParser.IDENT, i);
		}
		public IdentlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterIdentlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitIdentlist(this);
		}
	}

	public final IdentlistContext identlist() throws RecognitionException {
		IdentlistContext _localctx = new IdentlistContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_identlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			((IdentlistContext)_localctx).list =  new ArrayList<>();
			setState(317);
			((IdentlistContext)_localctx).IDENT = match(IDENT);
			_localctx.list.add((((IdentlistContext)_localctx).IDENT!=null?((IdentlistContext)_localctx).IDENT.getText():null));
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(319);
				match(T__10);
				setState(320);
				((IdentlistContext)_localctx).IDENT = match(IDENT);
				_localctx.list.add((((IdentlistContext)_localctx).IDENT!=null?((IdentlistContext)_localctx).IDENT.getText():null));
				}
				}
				setState(326);
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

	@SuppressWarnings("CheckReturnValue")
	public static class SymbolContext extends ParserRuleContext {
		public Expression sym;
		public Token IDENT;
		public TerminalNode IDENT() { return getToken(GentzenParser.IDENT, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitSymbol(this);
		}
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			((SymbolContext)_localctx).IDENT = match(IDENT);
			((SymbolContext)_localctx).sym =  new Expression.Symbol((((SymbolContext)_localctx).IDENT!=null?((SymbolContext)_localctx).IDENT.getText():null), ((SymbolContext)_localctx).IDENT, ((SymbolContext)_localctx).IDENT);
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
	public static class ApplicationContext extends ParserRuleContext {
		public Expression app;
		public ArrayList<Expression> args = new ArrayList<>();
		public Token name;
		public ExpressionArgContext expressionArg;
		public AruleContext arule;
		public TerminalNode IDENT() { return getToken(GentzenParser.IDENT, 0); }
		public List<ExpressionArgContext> expressionArg() {
			return getRuleContexts(ExpressionArgContext.class);
		}
		public ExpressionArgContext expressionArg(int i) {
			return getRuleContext(ExpressionArgContext.class,i);
		}
		public AruleContext arule() {
			return getRuleContext(AruleContext.class,0);
		}
		public ApplicationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_application; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterApplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitApplication(this);
		}
	}

	public final ApplicationContext application() throws RecognitionException {
		ApplicationContext _localctx = new ApplicationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_application);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			((ApplicationContext)_localctx).name = match(IDENT);

			setState(337);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 257698037824L) != 0)) {
				{
				{
				setState(332);
				((ApplicationContext)_localctx).expressionArg = expressionArg();
				_localctx.args.add(((ApplicationContext)_localctx).expressionArg.expr);
				}
				}
				setState(339);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(343);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__25) {
				{
				setState(340);
				((ApplicationContext)_localctx).arule = arule();
				_localctx.args.add(((ApplicationContext)_localctx).arule.ar);
				}
			}

			}
			_ctx.stop = _input.LT(-1);
			((ApplicationContext)_localctx).app =  new Expression.Application((((ApplicationContext)_localctx).name!=null?((ApplicationContext)_localctx).name.getText():null), _localctx.args, _localctx.start, _localctx.stop);
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
	public static class TypealiasContext extends ParserRuleContext {
		public DeclDef.TypeAliasDecl ta;
		public TypeAlias alias;
		public Token name;
		public Token IDENT;
		public TypeContext type;
		public List<TerminalNode> IDENT() { return getTokens(GentzenParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(GentzenParser.IDENT, i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypealiasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typealias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterTypealias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitTypealias(this);
		}
	}

	public final TypealiasContext typealias() throws RecognitionException {
		TypealiasContext _localctx = new TypealiasContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_typealias);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			ArrayList<String> params = new ArrayList<>();
			setState(346);
			((TypealiasContext)_localctx).name = match(IDENT);
			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENT) {
				{
				{
				setState(347);
				((TypealiasContext)_localctx).IDENT = match(IDENT);
				params.add((((TypealiasContext)_localctx).IDENT!=null?((TypealiasContext)_localctx).IDENT.getText():null));
				}
				}
				setState(353);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(354);
			match(T__27);
			setState(360);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case T__12:
			case T__13:
			case T__16:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case IDENT:
				{
				setState(355);
				((TypealiasContext)_localctx).type = type(0);
				((TypealiasContext)_localctx).alias =  new TypeAlias((((TypealiasContext)_localctx).name!=null?((TypealiasContext)_localctx).name.getText():null), params, Optional.of(((TypealiasContext)_localctx).type.typ));
				}
				break;
			case T__28:
				{
				setState(358);
				match(T__28);
				((TypealiasContext)_localctx).alias =  new TypeAlias((((TypealiasContext)_localctx).name!=null?((TypealiasContext)_localctx).name.getText():null), params, Optional.empty());
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(362);
			match(T__1);
			}
			_ctx.stop = _input.LT(-1);
			((TypealiasContext)_localctx).ta =  new DeclDef.TypeAliasDecl(_localctx.alias, _localctx.start, _localctx.stop);
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
	public static class TypeclassContext extends ParserRuleContext {
		public DeclDef.TypeclassDecl tc;
		public ArrayList<DefinedType.TypeParam> params = new ArrayList<>();
		public HashMap<String, RuleSignature> rs = new HashMap<>();
		public ArrayList<Typeclass.CutEliminationRule> cers = new ArrayList<>();
		public Token name;
		public Token IDENT;
		public RuleSignatureContext ruleSignature;
		public KindContext kind;
		public RuleDeclContext ruleDecl;
		public CutEliminationRuleDefContext cutEliminationRuleDef;
		public List<TerminalNode> IDENT() { return getTokens(GentzenParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(GentzenParser.IDENT, i);
		}
		public List<RuleSignatureContext> ruleSignature() {
			return getRuleContexts(RuleSignatureContext.class);
		}
		public RuleSignatureContext ruleSignature(int i) {
			return getRuleContext(RuleSignatureContext.class,i);
		}
		public List<KindContext> kind() {
			return getRuleContexts(KindContext.class);
		}
		public KindContext kind(int i) {
			return getRuleContext(KindContext.class,i);
		}
		public List<RuleDeclContext> ruleDecl() {
			return getRuleContexts(RuleDeclContext.class);
		}
		public RuleDeclContext ruleDecl(int i) {
			return getRuleContext(RuleDeclContext.class,i);
		}
		public List<CutEliminationRuleDefContext> cutEliminationRuleDef() {
			return getRuleContexts(CutEliminationRuleDefContext.class);
		}
		public CutEliminationRuleDefContext cutEliminationRuleDef(int i) {
			return getRuleContext(CutEliminationRuleDefContext.class,i);
		}
		public TypeclassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeclass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterTypeclass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitTypeclass(this);
		}
	}

	public final TypeclassContext typeclass() throws RecognitionException {
		TypeclassContext _localctx = new TypeclassContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_typeclass);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(364);
			match(T__29);
			setState(365);
			((TypeclassContext)_localctx).name = match(IDENT);
			setState(381);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__25 || _la==T__29) {
				{
				setState(379);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__29:
					{
					setState(366);
					match(T__29);
					setState(367);
					((TypeclassContext)_localctx).IDENT = match(IDENT);
					setState(368);
					match(T__3);
					setState(369);
					((TypeclassContext)_localctx).ruleSignature = ruleSignature();
					setState(370);
					match(T__30);
					_localctx.params.add(new DefinedType.TypeParam.ValueParam((((TypeclassContext)_localctx).IDENT!=null?((TypeclassContext)_localctx).IDENT.getText():null), ((TypeclassContext)_localctx).ruleSignature.rs));
					}
					break;
				case T__25:
					{
					setState(373);
					match(T__25);
					setState(374);
					((TypeclassContext)_localctx).IDENT = match(IDENT);
					setState(375);
					((TypeclassContext)_localctx).kind = kind();
					setState(376);
					match(T__26);
					_localctx.params.add(new DefinedType.TypeParam.ParamWithKind((((TypeclassContext)_localctx).IDENT!=null?((TypeclassContext)_localctx).IDENT.getText():null), ((TypeclassContext)_localctx).kind.knd));
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(383);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(384);
			match(T__1);
			setState(393);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENT) {
				{
				setState(391);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(385);
					((TypeclassContext)_localctx).ruleDecl = ruleDecl();
					_localctx.rs.put(((TypeclassContext)_localctx).ruleDecl.name, ((TypeclassContext)_localctx).ruleDecl.rs);
					}
					break;
				case 2:
					{
					setState(388);
					((TypeclassContext)_localctx).cutEliminationRuleDef = cutEliminationRuleDef();
					_localctx.cers.add(((TypeclassContext)_localctx).cutEliminationRuleDef.cer);
					}
					break;
				}
				}
				setState(395);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(396);
			match(T__2);
			setState(406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 30064779264L) != 0)) {
				{
				setState(404);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENT:
					{
					setState(397);
					((TypeclassContext)_localctx).IDENT = match(IDENT);
					}
					break;
				case T__31:
					{
					setState(398);
					match(T__31);
					setState(399);
					((TypeclassContext)_localctx).IDENT = match(IDENT);
					}
					break;
				case T__32:
					{
					setState(400);
					match(T__32);
					setState(401);
					((TypeclassContext)_localctx).IDENT = match(IDENT);
					}
					break;
				case T__12:
					{
					setState(402);
					match(T__12);
					setState(403);
					((TypeclassContext)_localctx).IDENT = match(IDENT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(408);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(409);
			match(T__1);
			setState(410);
			match(T__30);
			}
			_ctx.stop = _input.LT(-1);
			((TypeclassContext)_localctx).tc =  new DeclDef.TypeclassDecl(new Typeclass((((TypeclassContext)_localctx).name!=null?((TypeclassContext)_localctx).name.getText():null), _localctx.params, _localctx.rs, _localctx.cers), _localctx.start, _localctx.stop);
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
	public static class KindContext extends ParserRuleContext {
		public Kind knd;
		public KindContext kind;
		public RuleSignatureContext ruleSignature;
		public List<KindContext> kind() {
			return getRuleContexts(KindContext.class);
		}
		public KindContext kind(int i) {
			return getRuleContext(KindContext.class,i);
		}
		public RuleSignatureContext ruleSignature() {
			return getRuleContext(RuleSignatureContext.class,0);
		}
		public KindContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kind; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterKind(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitKind(this);
		}
	}

	public final KindContext kind() throws RecognitionException {
		KindContext _localctx = new KindContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_kind);
		int _la;
		try {
			setState(429);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case T__6:
			case T__26:
				enterOuterAlt(_localctx, 1);
				{
				ArrayList<Kind> subkinds = new ArrayList<>();
				setState(420);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(413);
					match(T__5);
					setState(414);
					((KindContext)_localctx).kind = kind();
					setState(415);
					match(T__6);
					subkinds.add(((KindContext)_localctx).kind.knd);
					}
					}
					setState(422);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				((KindContext)_localctx).knd =  new Kind.TypeKind(subkinds);
				}
				break;
			case T__29:
				enterOuterAlt(_localctx, 2);
				{
				setState(424);
				match(T__29);
				setState(425);
				((KindContext)_localctx).ruleSignature = ruleSignature();
				setState(426);
				match(T__30);
				((KindContext)_localctx).knd =  new Kind.RuleKind(((KindContext)_localctx).ruleSignature.rs);
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

	@SuppressWarnings("CheckReturnValue")
	public static class CutEliminationRuleDefContext extends ParserRuleContext {
		public Typeclass.CutEliminationRule cer;
		public Token prim;
		public Token primArg;
		public Token sec;
		public Token secArg;
		public ExpressionContext body;
		public List<TerminalNode> IDENT() { return getTokens(GentzenParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(GentzenParser.IDENT, i);
		}
		public List<TerminalNode> NUM() { return getTokens(GentzenParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(GentzenParser.NUM, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CutEliminationRuleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cutEliminationRuleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterCutEliminationRuleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitCutEliminationRuleDef(this);
		}
	}

	public final CutEliminationRuleDefContext cutEliminationRuleDef() throws RecognitionException {
		CutEliminationRuleDefContext _localctx = new CutEliminationRuleDefContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_cutEliminationRuleDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(431);
			((CutEliminationRuleDefContext)_localctx).prim = match(IDENT);
			setState(432);
			match(T__9);
			setState(433);
			((CutEliminationRuleDefContext)_localctx).primArg = match(NUM);
			setState(434);
			((CutEliminationRuleDefContext)_localctx).sec = match(IDENT);
			setState(435);
			match(T__9);
			setState(436);
			((CutEliminationRuleDefContext)_localctx).secArg = match(NUM);
			setState(437);
			match(T__4);
			setState(438);
			((CutEliminationRuleDefContext)_localctx).body = expression();
			setState(439);
			match(T__1);
			((CutEliminationRuleDefContext)_localctx).cer =  new Typeclass.CutEliminationRule((((CutEliminationRuleDefContext)_localctx).prim!=null?((CutEliminationRuleDefContext)_localctx).prim.getText():null), Integer.parseInt((((CutEliminationRuleDefContext)_localctx).primArg!=null?((CutEliminationRuleDefContext)_localctx).primArg.getText():null)), (((CutEliminationRuleDefContext)_localctx).sec!=null?((CutEliminationRuleDefContext)_localctx).sec.getText():null), Integer.parseInt((((CutEliminationRuleDefContext)_localctx).secArg!=null?((CutEliminationRuleDefContext)_localctx).secArg.getText():null)), ((CutEliminationRuleDefContext)_localctx).body.expr);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return type_sempred((TypeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001(\u01bb\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u0000-\b\u0000\n\u0000\f\u00000\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000>\b\u0000"+
		"\n\u0000\f\u0000A\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000"+
		"F\b\u0000\n\u0000\f\u0000I\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"S\b\u0002\n\u0002\f\u0002V\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"l\b\u0005\n\u0005\f\u0005o\t\u0005\u0003\u0005q\b\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006\u0086\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006\u0093\b\u0006\u0005\u0006\u0095\b\u0006\n\u0006\f\u0006"+
		"\u0098\t\u0006\u0003\u0006\u009a\b\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00a7\b\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00b4\b\u0006\u0005"+
		"\u0006\u00b6\b\u0006\n\u0006\f\u0006\u00b9\t\u0006\u0003\u0006\u00bb\b"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u00ea\b\u0007\n"+
		"\u0007\f\u0007\u00ed\t\u0007\u0001\u0007\u0003\u0007\u00f0\b\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u00fc\b\u0007\n"+
		"\u0007\f\u0007\u00ff\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0105"+
		"\b\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u0110\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u011b\b\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b"+
		"\u012b\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u013b"+
		"\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u0143\b\r"+
		"\n\r\f\r\u0146\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u0150\b\u000f\n"+
		"\u000f\f\u000f\u0153\t\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003"+
		"\u000f\u0158\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005"+
		"\u0010\u015e\b\u0010\n\u0010\f\u0010\u0161\t\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0169\b\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011"+
		"\u017c\b\u0011\n\u0011\f\u0011\u017f\t\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u0188"+
		"\b\u0011\n\u0011\f\u0011\u018b\t\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011"+
		"\u0195\b\u0011\n\u0011\f\u0011\u0198\t\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0005\u0012\u01a3\b\u0012\n\u0012\f\u0012\u01a6\t\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012"+
		"\u01ae\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0000\u0001\u000e\u0014\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000\u0000\u01dd"+
		"\u0000(\u0001\u0000\u0000\u0000\u0002J\u0001\u0000\u0000\u0000\u0004O"+
		"\u0001\u0000\u0000\u0000\u0006Y\u0001\u0000\u0000\u0000\b^\u0001\u0000"+
		"\u0000\u0000\nc\u0001\u0000\u0000\u0000\fz\u0001\u0000\u0000\u0000\u000e"+
		"\u00ef\u0001\u0000\u0000\u0000\u0010\u0104\u0001\u0000\u0000\u0000\u0012"+
		"\u010f\u0001\u0000\u0000\u0000\u0014\u011a\u0001\u0000\u0000\u0000\u0016"+
		"\u012a\u0001\u0000\u0000\u0000\u0018\u013a\u0001\u0000\u0000\u0000\u001a"+
		"\u013c\u0001\u0000\u0000\u0000\u001c\u0147\u0001\u0000\u0000\u0000\u001e"+
		"\u014a\u0001\u0000\u0000\u0000 \u0159\u0001\u0000\u0000\u0000\"\u016c"+
		"\u0001\u0000\u0000\u0000$\u01ad\u0001\u0000\u0000\u0000&\u01af\u0001\u0000"+
		"\u0000\u0000(.\u0006\u0000\uffff\uffff\u0000)*\u0003\u0002\u0001\u0000"+
		"*+\u0006\u0000\uffff\uffff\u0000+-\u0001\u0000\u0000\u0000,)\u0001\u0000"+
		"\u0000\u0000-0\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001"+
		"\u0000\u0000\u0000/?\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u0000"+
		"12\u0003\u0006\u0003\u000023\u0006\u0000\uffff\uffff\u00003>\u0001\u0000"+
		"\u0000\u000045\u0003\b\u0004\u000056\u0006\u0000\uffff\uffff\u00006>\u0001"+
		"\u0000\u0000\u000078\u0003 \u0010\u000089\u0006\u0000\uffff\uffff\u0000"+
		"9>\u0001\u0000\u0000\u0000:;\u0003\"\u0011\u0000;<\u0006\u0000\uffff\uffff"+
		"\u0000<>\u0001\u0000\u0000\u0000=1\u0001\u0000\u0000\u0000=4\u0001\u0000"+
		"\u0000\u0000=7\u0001\u0000\u0000\u0000=:\u0001\u0000\u0000\u0000>A\u0001"+
		"\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000"+
		"@G\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000BC\u0003\u0004\u0002"+
		"\u0000CD\u0006\u0000\uffff\uffff\u0000DF\u0001\u0000\u0000\u0000EB\u0001"+
		"\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000"+
		"GH\u0001\u0000\u0000\u0000H\u0001\u0001\u0000\u0000\u0000IG\u0001\u0000"+
		"\u0000\u0000JK\u0005\u0001\u0000\u0000KL\u0005%\u0000\u0000LM\u0005\u0002"+
		"\u0000\u0000MN\u0006\u0001\uffff\uffff\u0000N\u0003\u0001\u0000\u0000"+
		"\u0000OT\u0005\u0003\u0000\u0000PQ\u0005\"\u0000\u0000QS\u0006\u0002\uffff"+
		"\uffff\u0000RP\u0001\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001"+
		"\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UW\u0001\u0000\u0000\u0000"+
		"VT\u0001\u0000\u0000\u0000WX\u0005\u0002\u0000\u0000X\u0005\u0001\u0000"+
		"\u0000\u0000YZ\u0005\"\u0000\u0000Z[\u0005\u0004\u0000\u0000[\\\u0003"+
		"\n\u0005\u0000\\]\u0005\u0002\u0000\u0000]\u0007\u0001\u0000\u0000\u0000"+
		"^_\u0005\"\u0000\u0000_`\u0005\u0005\u0000\u0000`a\u0003\u0010\b\u0000"+
		"ab\u0005\u0002\u0000\u0000b\t\u0001\u0000\u0000\u0000cd\u0006\u0005\uffff"+
		"\uffff\u0000dp\u0005\u0006\u0000\u0000ef\u0003\f\u0006\u0000fm\u0006\u0005"+
		"\uffff\uffff\u0000gh\u0005\u0002\u0000\u0000hi\u0003\f\u0006\u0000ij\u0006"+
		"\u0005\uffff\uffff\u0000jl\u0001\u0000\u0000\u0000kg\u0001\u0000\u0000"+
		"\u0000lo\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000\u0000mn\u0001\u0000"+
		"\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000\u0000\u0000pe\u0001"+
		"\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000"+
		"rs\u0005\u0007\u0000\u0000st\u0005\b\u0000\u0000tu\u0005\u0006\u0000\u0000"+
		"uv\u0003\f\u0006\u0000vw\u0006\u0005\uffff\uffff\u0000wx\u0005\u0007\u0000"+
		"\u0000xy\u0006\u0005\uffff\uffff\u0000y\u000b\u0001\u0000\u0000\u0000"+
		"z\u0099\u0006\u0006\uffff\uffff\u0000{|\u0005\t\u0000\u0000|}\u0005\""+
		"\u0000\u0000}\u0086\u0006\u0006\uffff\uffff\u0000~\u007f\u0005\n\u0000"+
		"\u0000\u007f\u0080\u0005#\u0000\u0000\u0080\u0081\u0005\u0006\u0000\u0000"+
		"\u0081\u0082\u0003\u000e\u0007\u0000\u0082\u0083\u0005\u0007\u0000\u0000"+
		"\u0083\u0084\u0006\u0006\uffff\uffff\u0000\u0084\u0086\u0001\u0000\u0000"+
		"\u0000\u0085{\u0001\u0000\u0000\u0000\u0085~\u0001\u0000\u0000\u0000\u0086"+
		"\u0096\u0001\u0000\u0000\u0000\u0087\u0092\u0005\u000b\u0000\u0000\u0088"+
		"\u0089\u0005\t\u0000\u0000\u0089\u008a\u0005\"\u0000\u0000\u008a\u0093"+
		"\u0006\u0006\uffff\uffff\u0000\u008b\u008c\u0005\n\u0000\u0000\u008c\u008d"+
		"\u0005#\u0000\u0000\u008d\u008e\u0005\u0006\u0000\u0000\u008e\u008f\u0003"+
		"\u000e\u0007\u0000\u008f\u0090\u0005\u0007\u0000\u0000\u0090\u0091\u0006"+
		"\u0006\uffff\uffff\u0000\u0091\u0093\u0001\u0000\u0000\u0000\u0092\u0088"+
		"\u0001\u0000\u0000\u0000\u0092\u008b\u0001\u0000\u0000\u0000\u0093\u0095"+
		"\u0001\u0000\u0000\u0000\u0094\u0087\u0001\u0000\u0000\u0000\u0095\u0098"+
		"\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000\u0000\u0000\u0096\u0097"+
		"\u0001\u0000\u0000\u0000\u0097\u009a\u0001\u0000\u0000\u0000\u0098\u0096"+
		"\u0001\u0000\u0000\u0000\u0099\u0085\u0001\u0000\u0000\u0000\u0099\u009a"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u00ba"+
		"\u0005\f\u0000\u0000\u009c\u009d\u0005\t\u0000\u0000\u009d\u009e\u0005"+
		"\"\u0000\u0000\u009e\u00a7\u0006\u0006\uffff\uffff\u0000\u009f\u00a0\u0005"+
		"\n\u0000\u0000\u00a0\u00a1\u0005#\u0000\u0000\u00a1\u00a2\u0005\u0006"+
		"\u0000\u0000\u00a2\u00a3\u0003\u000e\u0007\u0000\u00a3\u00a4\u0005\u0007"+
		"\u0000\u0000\u00a4\u00a5\u0006\u0006\uffff\uffff\u0000\u00a5\u00a7\u0001"+
		"\u0000\u0000\u0000\u00a6\u009c\u0001\u0000\u0000\u0000\u00a6\u009f\u0001"+
		"\u0000\u0000\u0000\u00a7\u00b7\u0001\u0000\u0000\u0000\u00a8\u00b3\u0005"+
		"\u000b\u0000\u0000\u00a9\u00aa\u0005\t\u0000\u0000\u00aa\u00ab\u0005\""+
		"\u0000\u0000\u00ab\u00b4\u0006\u0006\uffff\uffff\u0000\u00ac\u00ad\u0005"+
		"\n\u0000\u0000\u00ad\u00ae\u0005#\u0000\u0000\u00ae\u00af\u0005\u0006"+
		"\u0000\u0000\u00af\u00b0\u0003\u000e\u0007\u0000\u00b0\u00b1\u0005\u0007"+
		"\u0000\u0000\u00b1\u00b2\u0006\u0006\uffff\uffff\u0000\u00b2\u00b4\u0001"+
		"\u0000\u0000\u0000\u00b3\u00a9\u0001\u0000\u0000\u0000\u00b3\u00ac\u0001"+
		"\u0000\u0000\u0000\u00b4\u00b6\u0001\u0000\u0000\u0000\u00b5\u00a8\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b5\u0001"+
		"\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00bb\u0001"+
		"\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba\u00a6\u0001"+
		"\u0000\u0000\u0000\u00ba\u00bb\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bc\u00bd\u0006\u0006\uffff\uffff\u0000\u00bd\r\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0006\u0007\uffff\uffff\u0000\u00bf\u00c0"+
		"\u0005\u0006\u0000\u0000\u00c0\u00c1\u0003\u000e\u0007\u0000\u00c1\u00c2"+
		"\u0005\u0007\u0000\u0000\u00c2\u00c3\u0006\u0007\uffff\uffff\u0000\u00c3"+
		"\u00f0\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005\r\u0000\u0000\u00c5\u00c6"+
		"\u0005\"\u0000\u0000\u00c6\u00f0\u0006\u0007\uffff\uffff\u0000\u00c7\u00c8"+
		"\u0005\u000e\u0000\u0000\u00c8\u00c9\u0003\u000e\u0007\f\u00c9\u00ca\u0006"+
		"\u0007\uffff\uffff\u0000\u00ca\u00f0\u0001\u0000\u0000\u0000\u00cb\u00cc"+
		"\u0005\u0011\u0000\u0000\u00cc\u00cd\u0003\u000e\u0007\t\u00cd\u00ce\u0006"+
		"\u0007\uffff\uffff\u0000\u00ce\u00f0\u0001\u0000\u0000\u0000\u00cf\u00d0"+
		"\u0005\u0012\u0000\u0000\u00d0\u00f0\u0006\u0007\uffff\uffff\u0000\u00d1"+
		"\u00d2\u0005\u0013\u0000\u0000\u00d2\u00f0\u0006\u0007\uffff\uffff\u0000"+
		"\u00d3\u00d4\u0005\u0014\u0000\u0000\u00d4\u00f0\u0006\u0007\uffff\uffff"+
		"\u0000\u00d5\u00d6\u0005\u0015\u0000\u0000\u00d6\u00d7\u0003\u000e\u0007"+
		"\u0005\u00d7\u00d8\u0006\u0007\uffff\uffff\u0000\u00d8\u00f0\u0001\u0000"+
		"\u0000\u0000\u00d9\u00da\u0005\u0016\u0000\u0000\u00da\u00db\u0003\u000e"+
		"\u0007\u0004\u00db\u00dc\u0006\u0007\uffff\uffff\u0000\u00dc\u00f0\u0001"+
		"\u0000\u0000\u0000\u00dd\u00de\u0005\u0017\u0000\u0000\u00de\u00df\u0003"+
		"\u000e\u0007\u0003\u00df\u00e0\u0006\u0007\uffff\uffff\u0000\u00e0\u00f0"+
		"\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\u0018\u0000\u0000\u00e2\u00e3"+
		"\u0005\"\u0000\u0000\u00e3\u00f0\u0006\u0007\uffff\uffff\u0000\u00e4\u00e5"+
		"\u0005\"\u0000\u0000\u00e5\u00eb\u0006\u0007\uffff\uffff\u0000\u00e6\u00e7"+
		"\u0003\u000e\u0007\u0000\u00e7\u00e8\u0006\u0007\uffff\uffff\u0000\u00e8"+
		"\u00ea\u0001\u0000\u0000\u0000\u00e9\u00e6\u0001\u0000\u0000\u0000\u00ea"+
		"\u00ed\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001\u0000\u0000\u0000\u00eb"+
		"\u00ec\u0001\u0000\u0000\u0000\u00ec\u00ee\u0001\u0000\u0000\u0000\u00ed"+
		"\u00eb\u0001\u0000\u0000\u0000\u00ee\u00f0\u0006\u0007\uffff\uffff\u0000"+
		"\u00ef\u00be\u0001\u0000\u0000\u0000\u00ef\u00c4\u0001\u0000\u0000\u0000"+
		"\u00ef\u00c7\u0001\u0000\u0000\u0000\u00ef\u00cb\u0001\u0000\u0000\u0000"+
		"\u00ef\u00cf\u0001\u0000\u0000\u0000\u00ef\u00d1\u0001\u0000\u0000\u0000"+
		"\u00ef\u00d3\u0001\u0000\u0000\u0000\u00ef\u00d5\u0001\u0000\u0000\u0000"+
		"\u00ef\u00d9\u0001\u0000\u0000\u0000\u00ef\u00dd\u0001\u0000\u0000\u0000"+
		"\u00ef\u00e1\u0001\u0000\u0000\u0000\u00ef\u00e4\u0001\u0000\u0000\u0000"+
		"\u00f0\u00fd\u0001\u0000\u0000\u0000\u00f1\u00f2\n\u000b\u0000\u0000\u00f2"+
		"\u00f3\u0005\u000f\u0000\u0000\u00f3\u00f4\u0003\u000e\u0007\f\u00f4\u00f5"+
		"\u0006\u0007\uffff\uffff\u0000\u00f5\u00fc\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f7\n\n\u0000\u0000\u00f7\u00f8\u0005\u0010\u0000\u0000\u00f8\u00f9"+
		"\u0003\u000e\u0007\u000b\u00f9\u00fa\u0006\u0007\uffff\uffff\u0000\u00fa"+
		"\u00fc\u0001\u0000\u0000\u0000\u00fb\u00f1\u0001\u0000\u0000\u0000\u00fb"+
		"\u00f6\u0001\u0000\u0000\u0000\u00fc\u00ff\u0001\u0000\u0000\u0000\u00fd"+
		"\u00fb\u0001\u0000\u0000\u0000\u00fd\u00fe\u0001\u0000\u0000\u0000\u00fe"+
		"\u000f\u0001\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000\u0000\u0000\u0100"+
		"\u0101\u0003\u0012\t\u0000\u0101\u0102\u0005\u0019\u0000\u0000\u0102\u0103"+
		"\u0006\b\uffff\uffff\u0000\u0103\u0105\u0001\u0000\u0000\u0000\u0104\u0100"+
		"\u0001\u0000\u0000\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105\u0106"+
		"\u0001\u0000\u0000\u0000\u0106\u0107\u0003\u0014\n\u0000\u0107\u0108\u0006"+
		"\b\uffff\uffff\u0000\u0108\u0011\u0001\u0000\u0000\u0000\u0109\u010a\u0003"+
		"\u0016\u000b\u0000\u010a\u010b\u0006\t\uffff\uffff\u0000\u010b\u0110\u0001"+
		"\u0000\u0000\u0000\u010c\u010d\u0003\u001e\u000f\u0000\u010d\u010e\u0006"+
		"\t\uffff\uffff\u0000\u010e\u0110\u0001\u0000\u0000\u0000\u010f\u0109\u0001"+
		"\u0000\u0000\u0000\u010f\u010c\u0001\u0000\u0000\u0000\u0110\u0013\u0001"+
		"\u0000\u0000\u0000\u0111\u0112\u0003\u0016\u000b\u0000\u0112\u0113\u0006"+
		"\n\uffff\uffff\u0000\u0113\u011b\u0001\u0000\u0000\u0000\u0114\u0115\u0003"+
		"\u001e\u000f\u0000\u0115\u0116\u0006\n\uffff\uffff\u0000\u0116\u011b\u0001"+
		"\u0000\u0000\u0000\u0117\u0118\u0003\u0018\f\u0000\u0118\u0119\u0006\n"+
		"\uffff\uffff\u0000\u0119\u011b\u0001\u0000\u0000\u0000\u011a\u0111\u0001"+
		"\u0000\u0000\u0000\u011a\u0114\u0001\u0000\u0000\u0000\u011a\u0117\u0001"+
		"\u0000\u0000\u0000\u011b\u0015\u0001\u0000\u0000\u0000\u011c\u011d\u0003"+
		"\u001c\u000e\u0000\u011d\u011e\u0006\u000b\uffff\uffff\u0000\u011e\u012b"+
		"\u0001\u0000\u0000\u0000\u011f\u0120\u0005%\u0000\u0000\u0120\u012b\u0006"+
		"\u000b\uffff\uffff\u0000\u0121\u0122\u0005#\u0000\u0000\u0122\u012b\u0006"+
		"\u000b\uffff\uffff\u0000\u0123\u0124\u0005$\u0000\u0000\u0124\u012b\u0006"+
		"\u000b\uffff\uffff\u0000\u0125\u0126\u0005\u0006\u0000\u0000\u0126\u0127"+
		"\u0003\u0010\b\u0000\u0127\u0128\u0005\u0007\u0000\u0000\u0128\u0129\u0006"+
		"\u000b\uffff\uffff\u0000\u0129\u012b\u0001\u0000\u0000\u0000\u012a\u011c"+
		"\u0001\u0000\u0000\u0000\u012a\u011f\u0001\u0000\u0000\u0000\u012a\u0121"+
		"\u0001\u0000\u0000\u0000\u012a\u0123\u0001\u0000\u0000\u0000\u012a\u0125"+
		"\u0001\u0000\u0000\u0000\u012b\u0017\u0001\u0000\u0000\u0000\u012c\u012d"+
		"\u0005\u001a\u0000\u0000\u012d\u012e\u0003\u001a\r\u0000\u012e\u012f\u0005"+
		"\u001b\u0000\u0000\u012f\u0130\u0003\u0010\b\u0000\u0130\u0131\u0006\f"+
		"\uffff\uffff\u0000\u0131\u013b\u0001\u0000\u0000\u0000\u0132\u0133\u0005"+
		"\u001a\u0000\u0000\u0133\u0134\u0003\u001a\r\u0000\u0134\u0135\u0005\b"+
		"\u0000\u0000\u0135\u0136\u0003\u001a\r\u0000\u0136\u0137\u0005\u001b\u0000"+
		"\u0000\u0137\u0138\u0003\u0010\b\u0000\u0138\u0139\u0006\f\uffff\uffff"+
		"\u0000\u0139\u013b\u0001\u0000\u0000\u0000\u013a\u012c\u0001\u0000\u0000"+
		"\u0000\u013a\u0132\u0001\u0000\u0000\u0000\u013b\u0019\u0001\u0000\u0000"+
		"\u0000\u013c\u013d\u0006\r\uffff\uffff\u0000\u013d\u013e\u0005\"\u0000"+
		"\u0000\u013e\u0144\u0006\r\uffff\uffff\u0000\u013f\u0140\u0005\u000b\u0000"+
		"\u0000\u0140\u0141\u0005\"\u0000\u0000\u0141\u0143\u0006\r\uffff\uffff"+
		"\u0000\u0142\u013f\u0001\u0000\u0000\u0000\u0143\u0146\u0001\u0000\u0000"+
		"\u0000\u0144\u0142\u0001\u0000\u0000\u0000\u0144\u0145\u0001\u0000\u0000"+
		"\u0000\u0145\u001b\u0001\u0000\u0000\u0000\u0146\u0144\u0001\u0000\u0000"+
		"\u0000\u0147\u0148\u0005\"\u0000\u0000\u0148\u0149\u0006\u000e\uffff\uffff"+
		"\u0000\u0149\u001d\u0001\u0000\u0000\u0000\u014a\u014b\u0005\"\u0000\u0000"+
		"\u014b\u0151\u0006\u000f\uffff\uffff\u0000\u014c\u014d\u0003\u0016\u000b"+
		"\u0000\u014d\u014e\u0006\u000f\uffff\uffff\u0000\u014e\u0150\u0001\u0000"+
		"\u0000\u0000\u014f\u014c\u0001\u0000\u0000\u0000\u0150\u0153\u0001\u0000"+
		"\u0000\u0000\u0151\u014f\u0001\u0000\u0000\u0000\u0151\u0152\u0001\u0000"+
		"\u0000\u0000\u0152\u0157\u0001\u0000\u0000\u0000\u0153\u0151\u0001\u0000"+
		"\u0000\u0000\u0154\u0155\u0003\u0018\f\u0000\u0155\u0156\u0006\u000f\uffff"+
		"\uffff\u0000\u0156\u0158\u0001\u0000\u0000\u0000\u0157\u0154\u0001\u0000"+
		"\u0000\u0000\u0157\u0158\u0001\u0000\u0000\u0000\u0158\u001f\u0001\u0000"+
		"\u0000\u0000\u0159\u015a\u0006\u0010\uffff\uffff\u0000\u015a\u015f\u0005"+
		"\"\u0000\u0000\u015b\u015c\u0005\"\u0000\u0000\u015c\u015e\u0006\u0010"+
		"\uffff\uffff\u0000\u015d\u015b\u0001\u0000\u0000\u0000\u015e\u0161\u0001"+
		"\u0000\u0000\u0000\u015f\u015d\u0001\u0000\u0000\u0000\u015f\u0160\u0001"+
		"\u0000\u0000\u0000\u0160\u0162\u0001\u0000\u0000\u0000\u0161\u015f\u0001"+
		"\u0000\u0000\u0000\u0162\u0168\u0005\u001c\u0000\u0000\u0163\u0164\u0003"+
		"\u000e\u0007\u0000\u0164\u0165\u0006\u0010\uffff\uffff\u0000\u0165\u0169"+
		"\u0001\u0000\u0000\u0000\u0166\u0167\u0005\u001d\u0000\u0000\u0167\u0169"+
		"\u0006\u0010\uffff\uffff\u0000\u0168\u0163\u0001\u0000\u0000\u0000\u0168"+
		"\u0166\u0001\u0000\u0000\u0000\u0169\u016a\u0001\u0000\u0000\u0000\u016a"+
		"\u016b\u0005\u0002\u0000\u0000\u016b!\u0001\u0000\u0000\u0000\u016c\u016d"+
		"\u0005\u001e\u0000\u0000\u016d\u017d\u0005\"\u0000\u0000\u016e\u016f\u0005"+
		"\u001e\u0000\u0000\u016f\u0170\u0005\"\u0000\u0000\u0170\u0171\u0005\u0004"+
		"\u0000\u0000\u0171\u0172\u0003\n\u0005\u0000\u0172\u0173\u0005\u001f\u0000"+
		"\u0000\u0173\u0174\u0006\u0011\uffff\uffff\u0000\u0174\u017c\u0001\u0000"+
		"\u0000\u0000\u0175\u0176\u0005\u001a\u0000\u0000\u0176\u0177\u0005\"\u0000"+
		"\u0000\u0177\u0178\u0003$\u0012\u0000\u0178\u0179\u0005\u001b\u0000\u0000"+
		"\u0179\u017a\u0006\u0011\uffff\uffff\u0000\u017a\u017c\u0001\u0000\u0000"+
		"\u0000\u017b\u016e\u0001\u0000\u0000\u0000\u017b\u0175\u0001\u0000\u0000"+
		"\u0000\u017c\u017f\u0001\u0000\u0000\u0000\u017d\u017b\u0001\u0000\u0000"+
		"\u0000\u017d\u017e\u0001\u0000\u0000\u0000\u017e\u0180\u0001\u0000\u0000"+
		"\u0000\u017f\u017d\u0001\u0000\u0000\u0000\u0180\u0189\u0005\u0002\u0000"+
		"\u0000\u0181\u0182\u0003\u0006\u0003\u0000\u0182\u0183\u0006\u0011\uffff"+
		"\uffff\u0000\u0183\u0188\u0001\u0000\u0000\u0000\u0184\u0185\u0003&\u0013"+
		"\u0000\u0185\u0186\u0006\u0011\uffff\uffff\u0000\u0186\u0188\u0001\u0000"+
		"\u0000\u0000\u0187\u0181\u0001\u0000\u0000\u0000\u0187\u0184\u0001\u0000"+
		"\u0000\u0000\u0188\u018b\u0001\u0000\u0000\u0000\u0189\u0187\u0001\u0000"+
		"\u0000\u0000\u0189\u018a\u0001\u0000\u0000\u0000\u018a\u018c\u0001\u0000"+
		"\u0000\u0000\u018b\u0189\u0001\u0000\u0000\u0000\u018c\u0196\u0005\u0003"+
		"\u0000\u0000\u018d\u0195\u0005\"\u0000\u0000\u018e\u018f\u0005 \u0000"+
		"\u0000\u018f\u0195\u0005\"\u0000\u0000\u0190\u0191\u0005!\u0000\u0000"+
		"\u0191\u0195\u0005\"\u0000\u0000\u0192\u0193\u0005\r\u0000\u0000\u0193"+
		"\u0195\u0005\"\u0000\u0000\u0194\u018d\u0001\u0000\u0000\u0000\u0194\u018e"+
		"\u0001\u0000\u0000\u0000\u0194\u0190\u0001\u0000\u0000\u0000\u0194\u0192"+
		"\u0001\u0000\u0000\u0000\u0195\u0198\u0001\u0000\u0000\u0000\u0196\u0194"+
		"\u0001\u0000\u0000\u0000\u0196\u0197\u0001\u0000\u0000\u0000\u0197\u0199"+
		"\u0001\u0000\u0000\u0000\u0198\u0196\u0001\u0000\u0000\u0000\u0199\u019a"+
		"\u0005\u0002\u0000\u0000\u019a\u019b\u0005\u001f\u0000\u0000\u019b#\u0001"+
		"\u0000\u0000\u0000\u019c\u01a4\u0006\u0012\uffff\uffff\u0000\u019d\u019e"+
		"\u0005\u0006\u0000\u0000\u019e\u019f\u0003$\u0012\u0000\u019f\u01a0\u0005"+
		"\u0007\u0000\u0000\u01a0\u01a1\u0006\u0012\uffff\uffff\u0000\u01a1\u01a3"+
		"\u0001\u0000\u0000\u0000\u01a2\u019d\u0001\u0000\u0000\u0000\u01a3\u01a6"+
		"\u0001\u0000\u0000\u0000\u01a4\u01a2\u0001\u0000\u0000\u0000\u01a4\u01a5"+
		"\u0001\u0000\u0000\u0000\u01a5\u01a7\u0001\u0000\u0000\u0000\u01a6\u01a4"+
		"\u0001\u0000\u0000\u0000\u01a7\u01ae\u0006\u0012\uffff\uffff\u0000\u01a8"+
		"\u01a9\u0005\u001e\u0000\u0000\u01a9\u01aa\u0003\n\u0005\u0000\u01aa\u01ab"+
		"\u0005\u001f\u0000\u0000\u01ab\u01ac\u0006\u0012\uffff\uffff\u0000\u01ac"+
		"\u01ae\u0001\u0000\u0000\u0000\u01ad\u019c\u0001\u0000\u0000\u0000\u01ad"+
		"\u01a8\u0001\u0000\u0000\u0000\u01ae%\u0001\u0000\u0000\u0000\u01af\u01b0"+
		"\u0005\"\u0000\u0000\u01b0\u01b1\u0005\n\u0000\u0000\u01b1\u01b2\u0005"+
		"#\u0000\u0000\u01b2\u01b3\u0005\"\u0000\u0000\u01b3\u01b4\u0005\n\u0000"+
		"\u0000\u01b4\u01b5\u0005#\u0000\u0000\u01b5\u01b6\u0005\u0005\u0000\u0000"+
		"\u01b6\u01b7\u0003\u0010\b\u0000\u01b7\u01b8\u0005\u0002\u0000\u0000\u01b8"+
		"\u01b9\u0006\u0013\uffff\uffff\u0000\u01b9\'\u0001\u0000\u0000\u0000%"+
		".=?GTmp\u0085\u0092\u0096\u0099\u00a6\u00b3\u00b7\u00ba\u00eb\u00ef\u00fb"+
		"\u00fd\u0104\u010f\u011a\u012a\u013a\u0144\u0151\u0157\u015f\u0168\u017b"+
		"\u017d\u0187\u0189\u0194\u0196\u01a4\u01ad";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}