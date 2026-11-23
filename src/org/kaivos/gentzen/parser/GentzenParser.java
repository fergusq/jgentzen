// Generated from Gentzen.g4 by ANTLR 4.13.2

package org.kaivos.gentzen.parser;

import java.util.HashMap;
import java.util.Map;

import org.kaivos.gentzen.DeclDef;
import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.Rule;
import org.kaivos.gentzen.RuleSignature;

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
		T__24=25, T__25=26, IDENT=27, NUM=28, STRING=29, WS=30, LINE_COMMENT=31;
	public static final int
		RULE_gentzenFile = 0, RULE_importDecl = 1, RULE_exportDecl = 2, RULE_ruleDecl = 3, 
		RULE_ruleDef = 4, RULE_ruleSignature = 5, RULE_sequent = 6, RULE_seqParameters = 7, 
		RULE_type = 8, RULE_expression = 9, RULE_expressionArg = 10, RULE_arule = 11, 
		RULE_identlist = 12, RULE_symbol = 13, RULE_application = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"gentzenFile", "importDecl", "exportDecl", "ruleDecl", "ruleDef", "ruleSignature", 
			"sequent", "seqParameters", "type", "expression", "expressionArg", "arule", 
			"identlist", "symbol", "application"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<-'", "';'", "'->'", "':'", "'='", "'('", "')'", "'/'", "'|-'", 
			"'*'", "'$'", "','", "'?'", "'~'", "'/\\'", "'\\/'", "'@'", "'|\\|'", 
			"'++'", "'_|_'", "'..'", "'[]'", "'<>'", "'!'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "IDENT", "NUM", "STRING", "WS", "LINE_COMMENT"
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
		public RuleDeclContext ruleDecl;
		public RuleDefContext ruleDef;
		public ExportDeclContext exportDecl;
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
		public List<ImportDeclContext> importDecl() {
			return getRuleContexts(ImportDeclContext.class);
		}
		public ImportDeclContext importDecl(int i) {
			return getRuleContext(ImportDeclContext.class,i);
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
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 134217738L) != 0)) {
				{
				setState(43);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(31);
					((GentzenFileContext)_localctx).ruleDecl = ruleDecl();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).ruleDecl.r);
					}
					break;
				case 2:
					{
					setState(34);
					((GentzenFileContext)_localctx).ruleDef = ruleDef();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).ruleDef.rd);
					}
					break;
				case 3:
					{
					setState(37);
					importDecl();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).exportDecl.d);
					}
					break;
				case 4:
					{
					setState(40);
					((GentzenFileContext)_localctx).exportDecl = exportDecl();
					_localctx.decldefs.add(((GentzenFileContext)_localctx).exportDecl.d);
					}
					break;
				}
				}
				setState(47);
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
			setState(48);
			match(T__0);
			setState(49);
			((ImportDeclContext)_localctx).STRING = match(STRING);
			setState(50);
			match(T__1);
			((ImportDeclContext)_localctx).d =  new DeclDef.ImportDecl((((ImportDeclContext)_localctx).STRING!=null?((ImportDeclContext)_localctx).STRING.getText():null));
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
			ArrayList<String> names = new ArrayList<>();
			setState(54);
			match(T__2);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENT) {
				{
				{
				setState(55);
				((ExportDeclContext)_localctx).IDENT = match(IDENT);
				names.add((((ExportDeclContext)_localctx).IDENT!=null?((ExportDeclContext)_localctx).IDENT.getText():null);)
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(62);
			match(T__1);
			((ExportDeclContext)_localctx).d =  new DeclDef.ExportDecl(names);
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
	public static class RuleDeclContext extends ParserRuleContext {
		public DeclDef.RuleDeclaration r;
		public Token IDENT;
		public RuleSignatureContext ruleSignature;
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
			setState(65);
			((RuleDeclContext)_localctx).IDENT = match(IDENT);
			setState(66);
			match(T__3);
			setState(67);
			((RuleDeclContext)_localctx).ruleSignature = ruleSignature();
			setState(68);
			match(T__1);
			((RuleDeclContext)_localctx).r =  new DeclDef.RuleDeclaration((((RuleDeclContext)_localctx).IDENT!=null?((RuleDeclContext)_localctx).IDENT.getText():null), ((RuleDeclContext)_localctx).ruleSignature.rs);
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
	public static class RuleDefContext extends ParserRuleContext {
		public DeclDef.RuleDefinition rd;
		public Token IDENT;
		public ExpressionContext expression;
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
			setState(71);
			((RuleDefContext)_localctx).IDENT = match(IDENT);
			setState(72);
			match(T__4);
			setState(73);
			((RuleDefContext)_localctx).expression = expression();
			setState(74);
			match(T__1);
			((RuleDefContext)_localctx).rd =  new DeclDef.RuleDefinition((((RuleDefContext)_localctx).IDENT!=null?((RuleDefContext)_localctx).IDENT.getText():null), ((RuleDefContext)_localctx).expression.expr);
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
			setState(78);
			match(T__5);
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3584L) != 0)) {
				{
				setState(79);
				((RuleSignatureContext)_localctx).sequent = sequent();
				aboves.add(((RuleSignatureContext)_localctx).sequent.seq);
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(81);
					match(T__1);
					setState(82);
					((RuleSignatureContext)_localctx).sequent = sequent();
					aboves.add(((RuleSignatureContext)_localctx).sequent.seq);
					}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(92);
			match(T__6);
			setState(93);
			match(T__7);
			setState(94);
			match(T__5);
			setState(95);
			((RuleSignatureContext)_localctx).sequent = sequent();
			RuleSignature.Sequent below = ((RuleSignatureContext)_localctx).sequent.seq;
			setState(97);
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
		public SeqParametersContext inputs;
		public SeqParametersContext outputs;
		public List<SeqParametersContext> seqParameters() {
			return getRuleContexts(SeqParametersContext.class);
		}
		public SeqParametersContext seqParameters(int i) {
			return getRuleContext(SeqParametersContext.class,i);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			((SequentContext)_localctx).inputs = seqParameters();
			setState(101);
			match(T__8);
			setState(102);
			((SequentContext)_localctx).outputs = seqParameters();
			((SequentContext)_localctx).seq =  new RuleSignature.Sequent(((SequentContext)_localctx).inputs.params, ((SequentContext)_localctx).outputs.params);
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
	public static class SeqParametersContext extends ParserRuleContext {
		public RuleSignature.SequentParameters params;
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
		public SeqParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seqParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).enterSeqParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GentzenListener ) ((GentzenListener)listener).exitSeqParameters(this);
		}
	}

	public final SeqParametersContext seqParameters() throws RecognitionException {
		SeqParametersContext _localctx = new SeqParametersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_seqParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

			    ArrayList<RuleSignature.ContextVar> contextVars = new ArrayList<>();
			    HashMap<Integer, ExpressionType> parameters = new HashMap<>();
			    
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__10) {
				{
				setState(116);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__9:
					{
					setState(106);
					match(T__9);
					setState(107);
					((SeqParametersContext)_localctx).IDENT = match(IDENT);
					contextVars.add(new RuleSignature.ContextVar((((SeqParametersContext)_localctx).IDENT!=null?((SeqParametersContext)_localctx).IDENT.getText():null), 0));
					}
					break;
				case T__10:
					{
					setState(109);
					match(T__10);
					setState(110);
					((SeqParametersContext)_localctx).NUM = match(NUM);
					setState(111);
					match(T__5);
					setState(112);
					((SeqParametersContext)_localctx).type = type(0);
					setState(113);
					match(T__6);
					parameters.put(Integer.parseInt((((SeqParametersContext)_localctx).NUM!=null?((SeqParametersContext)_localctx).NUM.getText():null)), ((SeqParametersContext)_localctx).type.typ);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(118);
					match(T__11);
					setState(129);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__9:
						{
						setState(119);
						match(T__9);
						setState(120);
						((SeqParametersContext)_localctx).IDENT = match(IDENT);
						contextVars.add(new RuleSignature.ContextVar((((SeqParametersContext)_localctx).IDENT!=null?((SeqParametersContext)_localctx).IDENT.getText():null), 0));
						}
						break;
					case T__10:
						{
						setState(122);
						match(T__10);
						setState(123);
						((SeqParametersContext)_localctx).NUM = match(NUM);
						setState(124);
						match(T__5);
						setState(125);
						((SeqParametersContext)_localctx).type = type(0);
						setState(126);
						match(T__6);
						parameters.put(Integer.parseInt((((SeqParametersContext)_localctx).NUM!=null?((SeqParametersContext)_localctx).NUM.getText():null)), ((SeqParametersContext)_localctx).type.typ);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					}
					setState(135);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			((SeqParametersContext)_localctx).params =  new RuleSignature.SequentParameters(parameters, Map.of(), contextVars);
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
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				{
				setState(141);
				match(T__5);
				setState(142);
				((TypeContext)_localctx).type = type(0);
				setState(143);
				match(T__6);
				((TypeContext)_localctx).typ =  ((TypeContext)_localctx).type.typ;
				}
				break;
			case T__12:
				{
				setState(146);
				match(T__12);
				setState(147);
				((TypeContext)_localctx).IDENT = match(IDENT);
				((TypeContext)_localctx).typ =  new ExpressionType.TypeVar((((TypeContext)_localctx).IDENT!=null?((TypeContext)_localctx).IDENT.getText():null), 0);
				}
				break;
			case T__13:
				{
				setState(149);
				match(T__13);
				setState(150);
				((TypeContext)_localctx).type = type(12);
				((TypeContext)_localctx).typ =  new ExpressionType.Not(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__16:
				{
				setState(153);
				match(T__16);
				setState(154);
				((TypeContext)_localctx).type = type(9);
				((TypeContext)_localctx).typ =  new ExpressionType.Maybe(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__17:
				{
				setState(157);
				match(T__17);
				((TypeContext)_localctx).typ =  new ExpressionType.Nat();
				}
				break;
			case T__18:
				{
				setState(159);
				match(T__18);
				((TypeContext)_localctx).typ =  new ExpressionType.Unit();
				}
				break;
			case T__19:
				{
				setState(161);
				match(T__19);
				((TypeContext)_localctx).typ =  new ExpressionType.Bottom();
				}
				break;
			case T__20:
				{
				setState(163);
				match(T__20);
				setState(164);
				((TypeContext)_localctx).type = type(5);
				((TypeContext)_localctx).typ =  new ExpressionType.List(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__21:
				{
				setState(167);
				match(T__21);
				setState(168);
				((TypeContext)_localctx).type = type(4);
				((TypeContext)_localctx).typ =  new ExpressionType.Box(((TypeContext)_localctx).type.typ);
				}
				break;
			case T__22:
				{
				setState(171);
				match(T__22);
				setState(172);
				((TypeContext)_localctx).type = type(3);
				((TypeContext)_localctx).typ =  new ExpressionType.Not(new ExpressionType.Box(new ExpressionType.Not(((TypeContext)_localctx).type.typ)));
				}
				break;
			case T__23:
				{
				setState(175);
				match(T__23);
				setState(176);
				((TypeContext)_localctx).IDENT = match(IDENT);
				((TypeContext)_localctx).typ =  new ExpressionType.Atomic((((TypeContext)_localctx).IDENT!=null?((TypeContext)_localctx).IDENT.getText():null));
				}
				break;
			case IDENT:
				{
				setState(178);
				((TypeContext)_localctx).name = match(IDENT);
				ArrayList<ExpressionType> typeArgs = new ArrayList<>();
				setState(185);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(180);
						((TypeContext)_localctx).type = type(0);
						typeArgs.add(((TypeContext)_localctx).type.typ);
						}
						} 
					}
					setState(187);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				((TypeContext)_localctx).typ =  new ExpressionType.CustomType((((TypeContext)_localctx).name!=null?((TypeContext)_localctx).name.getText():null), typeArgs);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(203);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(201);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new TypeContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(191);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(192);
						match(T__14);
						setState(193);
						((TypeContext)_localctx).right = ((TypeContext)_localctx).type = type(12);
						((TypeContext)_localctx).typ =  new ExpressionType.And(((TypeContext)_localctx).left.typ, ((TypeContext)_localctx).right.typ);
						}
						break;
					case 2:
						{
						_localctx = new TypeContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(196);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(197);
						match(T__15);
						setState(198);
						((TypeContext)_localctx).right = ((TypeContext)_localctx).type = type(11);
						((TypeContext)_localctx).typ =  new ExpressionType.Or(((TypeContext)_localctx).left.typ, ((TypeContext)_localctx).right.typ);
						}
						break;
					}
					} 
				}
				setState(205);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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
		enterRule(_localctx, 18, RULE_expression);
		try {
			setState(215);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(206);
				((ExpressionContext)_localctx).expressionArg = expressionArg();
				((ExpressionContext)_localctx).expr =  ((ExpressionContext)_localctx).expressionArg.expr;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(209);
				((ExpressionContext)_localctx).application = application();
				((ExpressionContext)_localctx).expr =  ((ExpressionContext)_localctx).application.app;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(212);
				((ExpressionContext)_localctx).arule = arule();
				((ExpressionContext)_localctx).expr =  ((ExpressionContext)_localctx).arule.ar;
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
		public ExpressionContext expression;
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public TerminalNode STRING() { return getToken(GentzenParser.STRING, 0); }
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
		enterRule(_localctx, 20, RULE_expressionArg);
		try {
			setState(227);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(217);
				((ExpressionArgContext)_localctx).symbol = symbol();
				((ExpressionArgContext)_localctx).expr =  ((ExpressionArgContext)_localctx).symbol.sym;
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(220);
				((ExpressionArgContext)_localctx).STRING = match(STRING);
				((ExpressionArgContext)_localctx).expr =  new Expression.StringLiteral((((ExpressionArgContext)_localctx).STRING!=null?((ExpressionArgContext)_localctx).STRING.getText():null), _localctx.start, _localctx.stop);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(222);
				match(T__5);
				setState(223);
				((ExpressionArgContext)_localctx).expression = expression();
				setState(224);
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
		enterRule(_localctx, 22, RULE_arule);
		try {
			setState(243);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(229);
				match(T__24);
				setState(230);
				((AruleContext)_localctx).below = identlist();
				setState(231);
				match(T__25);
				setState(232);
				((AruleContext)_localctx).body = expression();
				((AruleContext)_localctx).ar =  new Expression.AnonymousRule(List.of(), ((AruleContext)_localctx).below.list, ((AruleContext)_localctx).body.expr, _localctx.start, _localctx.stop);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(235);
				match(T__24);
				setState(236);
				((AruleContext)_localctx).above = identlist();
				setState(237);
				match(T__7);
				setState(238);
				((AruleContext)_localctx).below = identlist();
				setState(239);
				match(T__25);
				setState(240);
				((AruleContext)_localctx).body = expression();
				((AruleContext)_localctx).ar =  new Expression.AnonymousRule(((AruleContext)_localctx).above.list, ((AruleContext)_localctx).below.list, ((AruleContext)_localctx).body.expr, _localctx.start, _localctx.stop);
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
		enterRule(_localctx, 24, RULE_identlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			((IdentlistContext)_localctx).list =  new ArrayList<>();
			setState(246);
			((IdentlistContext)_localctx).IDENT = match(IDENT);
			_localctx.list.add((((IdentlistContext)_localctx).IDENT!=null?((IdentlistContext)_localctx).IDENT.getText():null));
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(248);
				match(T__11);
				setState(249);
				((IdentlistContext)_localctx).IDENT = match(IDENT);
				_localctx.list.add((((IdentlistContext)_localctx).IDENT!=null?((IdentlistContext)_localctx).IDENT.getText():null));
				}
				}
				setState(255);
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
		enterRule(_localctx, 26, RULE_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			((SymbolContext)_localctx).IDENT = match(IDENT);
			((SymbolContext)_localctx).sym =  new Expression.Symbol((((SymbolContext)_localctx).IDENT!=null?((SymbolContext)_localctx).IDENT.getText():null), _localctx.start, _localctx.stop);
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
		public Token IDENT;
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
		enterRule(_localctx, 28, RULE_application);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			((ApplicationContext)_localctx).IDENT = match(IDENT);
			String ruleName = (((ApplicationContext)_localctx).IDENT!=null?((ApplicationContext)_localctx).IDENT.getText():null); ArrayList<Expression> args = new ArrayList<>();
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 671088704L) != 0)) {
				{
				{
				setState(261);
				((ApplicationContext)_localctx).expressionArg = expressionArg();
				args.add(((ApplicationContext)_localctx).expressionArg.expr);
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(269);
				((ApplicationContext)_localctx).arule = arule();
				args.add(((ApplicationContext)_localctx).arule.ar);
				}
			}

			((ApplicationContext)_localctx).app =  new Expression.Application(ruleName, args, _localctx.start, _localctx.stop);
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
		case 8:
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
		"\u0004\u0001\u001f\u0115\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u0000,\b\u0000\n\u0000\f\u0000/\t\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002:\b\u0002\n\u0002\f\u0002=\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005V\b\u0005\n\u0005"+
		"\f\u0005Y\t\u0005\u0003\u0005[\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007u\b\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0082\b\u0007"+
		"\u0005\u0007\u0084\b\u0007\n\u0007\f\u0007\u0087\t\u0007\u0003\u0007\u0089"+
		"\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0005\b\u00b8\b\b\n\b\f\b\u00bb\t\b\u0001\b\u0003\b"+
		"\u00be\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0005\b\u00ca\b\b\n\b\f\b\u00cd\t\b\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00d8"+
		"\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0003\n\u00e4\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00f4"+
		"\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00fc"+
		"\b\f\n\f\f\f\u00ff\t\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0109\b\u000e\n\u000e"+
		"\f\u000e\u010c\t\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u0111\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0000\u0001\u0010\u000f"+
		"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u0000\u0000\u0126\u0000\u001e\u0001\u0000\u0000\u0000\u00020\u0001"+
		"\u0000\u0000\u0000\u00045\u0001\u0000\u0000\u0000\u0006A\u0001\u0000\u0000"+
		"\u0000\bG\u0001\u0000\u0000\u0000\nM\u0001\u0000\u0000\u0000\fd\u0001"+
		"\u0000\u0000\u0000\u000ei\u0001\u0000\u0000\u0000\u0010\u00bd\u0001\u0000"+
		"\u0000\u0000\u0012\u00d7\u0001\u0000\u0000\u0000\u0014\u00e3\u0001\u0000"+
		"\u0000\u0000\u0016\u00f3\u0001\u0000\u0000\u0000\u0018\u00f5\u0001\u0000"+
		"\u0000\u0000\u001a\u0100\u0001\u0000\u0000\u0000\u001c\u0103\u0001\u0000"+
		"\u0000\u0000\u001e-\u0006\u0000\uffff\uffff\u0000\u001f \u0003\u0006\u0003"+
		"\u0000 !\u0006\u0000\uffff\uffff\u0000!,\u0001\u0000\u0000\u0000\"#\u0003"+
		"\b\u0004\u0000#$\u0006\u0000\uffff\uffff\u0000$,\u0001\u0000\u0000\u0000"+
		"%&\u0003\u0002\u0001\u0000&\'\u0006\u0000\uffff\uffff\u0000\',\u0001\u0000"+
		"\u0000\u0000()\u0003\u0004\u0002\u0000)*\u0006\u0000\uffff\uffff\u0000"+
		"*,\u0001\u0000\u0000\u0000+\u001f\u0001\u0000\u0000\u0000+\"\u0001\u0000"+
		"\u0000\u0000+%\u0001\u0000\u0000\u0000+(\u0001\u0000\u0000\u0000,/\u0001"+
		"\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000"+
		".\u0001\u0001\u0000\u0000\u0000/-\u0001\u0000\u0000\u000001\u0005\u0001"+
		"\u0000\u000012\u0005\u001d\u0000\u000023\u0005\u0002\u0000\u000034\u0006"+
		"\u0001\uffff\uffff\u00004\u0003\u0001\u0000\u0000\u000056\u0006\u0002"+
		"\uffff\uffff\u00006;\u0005\u0003\u0000\u000078\u0005\u001b\u0000\u0000"+
		"8:\u0006\u0002\uffff\uffff\u000097\u0001\u0000\u0000\u0000:=\u0001\u0000"+
		"\u0000\u0000;9\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<>\u0001"+
		"\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000>?\u0005\u0002\u0000\u0000"+
		"?@\u0006\u0002\uffff\uffff\u0000@\u0005\u0001\u0000\u0000\u0000AB\u0005"+
		"\u001b\u0000\u0000BC\u0005\u0004\u0000\u0000CD\u0003\n\u0005\u0000DE\u0005"+
		"\u0002\u0000\u0000EF\u0006\u0003\uffff\uffff\u0000F\u0007\u0001\u0000"+
		"\u0000\u0000GH\u0005\u001b\u0000\u0000HI\u0005\u0005\u0000\u0000IJ\u0003"+
		"\u0012\t\u0000JK\u0005\u0002\u0000\u0000KL\u0006\u0004\uffff\uffff\u0000"+
		"L\t\u0001\u0000\u0000\u0000MN\u0006\u0005\uffff\uffff\u0000NZ\u0005\u0006"+
		"\u0000\u0000OP\u0003\f\u0006\u0000PW\u0006\u0005\uffff\uffff\u0000QR\u0005"+
		"\u0002\u0000\u0000RS\u0003\f\u0006\u0000ST\u0006\u0005\uffff\uffff\u0000"+
		"TV\u0001\u0000\u0000\u0000UQ\u0001\u0000\u0000\u0000VY\u0001\u0000\u0000"+
		"\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000X[\u0001\u0000"+
		"\u0000\u0000YW\u0001\u0000\u0000\u0000ZO\u0001\u0000\u0000\u0000Z[\u0001"+
		"\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\]\u0005\u0007\u0000\u0000"+
		"]^\u0005\b\u0000\u0000^_\u0005\u0006\u0000\u0000_`\u0003\f\u0006\u0000"+
		"`a\u0006\u0005\uffff\uffff\u0000ab\u0005\u0007\u0000\u0000bc\u0006\u0005"+
		"\uffff\uffff\u0000c\u000b\u0001\u0000\u0000\u0000de\u0003\u000e\u0007"+
		"\u0000ef\u0005\t\u0000\u0000fg\u0003\u000e\u0007\u0000gh\u0006\u0006\uffff"+
		"\uffff\u0000h\r\u0001\u0000\u0000\u0000i\u0088\u0006\u0007\uffff\uffff"+
		"\u0000jk\u0005\n\u0000\u0000kl\u0005\u001b\u0000\u0000lu\u0006\u0007\uffff"+
		"\uffff\u0000mn\u0005\u000b\u0000\u0000no\u0005\u001c\u0000\u0000op\u0005"+
		"\u0006\u0000\u0000pq\u0003\u0010\b\u0000qr\u0005\u0007\u0000\u0000rs\u0006"+
		"\u0007\uffff\uffff\u0000su\u0001\u0000\u0000\u0000tj\u0001\u0000\u0000"+
		"\u0000tm\u0001\u0000\u0000\u0000u\u0085\u0001\u0000\u0000\u0000v\u0081"+
		"\u0005\f\u0000\u0000wx\u0005\n\u0000\u0000xy\u0005\u001b\u0000\u0000y"+
		"\u0082\u0006\u0007\uffff\uffff\u0000z{\u0005\u000b\u0000\u0000{|\u0005"+
		"\u001c\u0000\u0000|}\u0005\u0006\u0000\u0000}~\u0003\u0010\b\u0000~\u007f"+
		"\u0005\u0007\u0000\u0000\u007f\u0080\u0006\u0007\uffff\uffff\u0000\u0080"+
		"\u0082\u0001\u0000\u0000\u0000\u0081w\u0001\u0000\u0000\u0000\u0081z\u0001"+
		"\u0000\u0000\u0000\u0082\u0084\u0001\u0000\u0000\u0000\u0083v\u0001\u0000"+
		"\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000"+
		"\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0089\u0001\u0000"+
		"\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0088t\u0001\u0000\u0000"+
		"\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000"+
		"\u0000\u008a\u008b\u0006\u0007\uffff\uffff\u0000\u008b\u000f\u0001\u0000"+
		"\u0000\u0000\u008c\u008d\u0006\b\uffff\uffff\u0000\u008d\u008e\u0005\u0006"+
		"\u0000\u0000\u008e\u008f\u0003\u0010\b\u0000\u008f\u0090\u0005\u0007\u0000"+
		"\u0000\u0090\u0091\u0006\b\uffff\uffff\u0000\u0091\u00be\u0001\u0000\u0000"+
		"\u0000\u0092\u0093\u0005\r\u0000\u0000\u0093\u0094\u0005\u001b\u0000\u0000"+
		"\u0094\u00be\u0006\b\uffff\uffff\u0000\u0095\u0096\u0005\u000e\u0000\u0000"+
		"\u0096\u0097\u0003\u0010\b\f\u0097\u0098\u0006\b\uffff\uffff\u0000\u0098"+
		"\u00be\u0001\u0000\u0000\u0000\u0099\u009a\u0005\u0011\u0000\u0000\u009a"+
		"\u009b\u0003\u0010\b\t\u009b\u009c\u0006\b\uffff\uffff\u0000\u009c\u00be"+
		"\u0001\u0000\u0000\u0000\u009d\u009e\u0005\u0012\u0000\u0000\u009e\u00be"+
		"\u0006\b\uffff\uffff\u0000\u009f\u00a0\u0005\u0013\u0000\u0000\u00a0\u00be"+
		"\u0006\b\uffff\uffff\u0000\u00a1\u00a2\u0005\u0014\u0000\u0000\u00a2\u00be"+
		"\u0006\b\uffff\uffff\u0000\u00a3\u00a4\u0005\u0015\u0000\u0000\u00a4\u00a5"+
		"\u0003\u0010\b\u0005\u00a5\u00a6\u0006\b\uffff\uffff\u0000\u00a6\u00be"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a8\u0005\u0016\u0000\u0000\u00a8\u00a9"+
		"\u0003\u0010\b\u0004\u00a9\u00aa\u0006\b\uffff\uffff\u0000\u00aa\u00be"+
		"\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005\u0017\u0000\u0000\u00ac\u00ad"+
		"\u0003\u0010\b\u0003\u00ad\u00ae\u0006\b\uffff\uffff\u0000\u00ae\u00be"+
		"\u0001\u0000\u0000\u0000\u00af\u00b0\u0005\u0018\u0000\u0000\u00b0\u00b1"+
		"\u0005\u001b\u0000\u0000\u00b1\u00be\u0006\b\uffff\uffff\u0000\u00b2\u00b3"+
		"\u0005\u001b\u0000\u0000\u00b3\u00b9\u0006\b\uffff\uffff\u0000\u00b4\u00b5"+
		"\u0003\u0010\b\u0000\u00b5\u00b6\u0006\b\uffff\uffff\u0000\u00b6\u00b8"+
		"\u0001\u0000\u0000\u0000\u00b7\u00b4\u0001\u0000\u0000\u0000\u00b8\u00bb"+
		"\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00b9\u00ba"+
		"\u0001\u0000\u0000\u0000\u00ba\u00bc\u0001\u0000\u0000\u0000\u00bb\u00b9"+
		"\u0001\u0000\u0000\u0000\u00bc\u00be\u0006\b\uffff\uffff\u0000\u00bd\u008c"+
		"\u0001\u0000\u0000\u0000\u00bd\u0092\u0001\u0000\u0000\u0000\u00bd\u0095"+
		"\u0001\u0000\u0000\u0000\u00bd\u0099\u0001\u0000\u0000\u0000\u00bd\u009d"+
		"\u0001\u0000\u0000\u0000\u00bd\u009f\u0001\u0000\u0000\u0000\u00bd\u00a1"+
		"\u0001\u0000\u0000\u0000\u00bd\u00a3\u0001\u0000\u0000\u0000\u00bd\u00a7"+
		"\u0001\u0000\u0000\u0000\u00bd\u00ab\u0001\u0000\u0000\u0000\u00bd\u00af"+
		"\u0001\u0000\u0000\u0000\u00bd\u00b2\u0001\u0000\u0000\u0000\u00be\u00cb"+
		"\u0001\u0000\u0000\u0000\u00bf\u00c0\n\u000b\u0000\u0000\u00c0\u00c1\u0005"+
		"\u000f\u0000\u0000\u00c1\u00c2\u0003\u0010\b\f\u00c2\u00c3\u0006\b\uffff"+
		"\uffff\u0000\u00c3\u00ca\u0001\u0000\u0000\u0000\u00c4\u00c5\n\n\u0000"+
		"\u0000\u00c5\u00c6\u0005\u0010\u0000\u0000\u00c6\u00c7\u0003\u0010\b\u000b"+
		"\u00c7\u00c8\u0006\b\uffff\uffff\u0000\u00c8\u00ca\u0001\u0000\u0000\u0000"+
		"\u00c9\u00bf\u0001\u0000\u0000\u0000\u00c9\u00c4\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cd\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000"+
		"\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc\u0011\u0001\u0000\u0000\u0000"+
		"\u00cd\u00cb\u0001\u0000\u0000\u0000\u00ce\u00cf\u0003\u0014\n\u0000\u00cf"+
		"\u00d0\u0006\t\uffff\uffff\u0000\u00d0\u00d8\u0001\u0000\u0000\u0000\u00d1"+
		"\u00d2\u0003\u001c\u000e\u0000\u00d2\u00d3\u0006\t\uffff\uffff\u0000\u00d3"+
		"\u00d8\u0001\u0000\u0000\u0000\u00d4\u00d5\u0003\u0016\u000b\u0000\u00d5"+
		"\u00d6\u0006\t\uffff\uffff\u0000\u00d6\u00d8\u0001\u0000\u0000\u0000\u00d7"+
		"\u00ce\u0001\u0000\u0000\u0000\u00d7\u00d1\u0001\u0000\u0000\u0000\u00d7"+
		"\u00d4\u0001\u0000\u0000\u0000\u00d8\u0013\u0001\u0000\u0000\u0000\u00d9"+
		"\u00da\u0003\u001a\r\u0000\u00da\u00db\u0006\n\uffff\uffff\u0000\u00db"+
		"\u00e4\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005\u001d\u0000\u0000\u00dd"+
		"\u00e4\u0006\n\uffff\uffff\u0000\u00de\u00df\u0005\u0006\u0000\u0000\u00df"+
		"\u00e0\u0003\u0012\t\u0000\u00e0\u00e1\u0005\u0007\u0000\u0000\u00e1\u00e2"+
		"\u0006\n\uffff\uffff\u0000\u00e2\u00e4\u0001\u0000\u0000\u0000\u00e3\u00d9"+
		"\u0001\u0000\u0000\u0000\u00e3\u00dc\u0001\u0000\u0000\u0000\u00e3\u00de"+
		"\u0001\u0000\u0000\u0000\u00e4\u0015\u0001\u0000\u0000\u0000\u00e5\u00e6"+
		"\u0005\u0019\u0000\u0000\u00e6\u00e7\u0003\u0018\f\u0000\u00e7\u00e8\u0005"+
		"\u001a\u0000\u0000\u00e8\u00e9\u0003\u0012\t\u0000\u00e9\u00ea\u0006\u000b"+
		"\uffff\uffff\u0000\u00ea\u00f4\u0001\u0000\u0000\u0000\u00eb\u00ec\u0005"+
		"\u0019\u0000\u0000\u00ec\u00ed\u0003\u0018\f\u0000\u00ed\u00ee\u0005\b"+
		"\u0000\u0000\u00ee\u00ef\u0003\u0018\f\u0000\u00ef\u00f0\u0005\u001a\u0000"+
		"\u0000\u00f0\u00f1\u0003\u0012\t\u0000\u00f1\u00f2\u0006\u000b\uffff\uffff"+
		"\u0000\u00f2\u00f4\u0001\u0000\u0000\u0000\u00f3\u00e5\u0001\u0000\u0000"+
		"\u0000\u00f3\u00eb\u0001\u0000\u0000\u0000\u00f4\u0017\u0001\u0000\u0000"+
		"\u0000\u00f5\u00f6\u0006\f\uffff\uffff\u0000\u00f6\u00f7\u0005\u001b\u0000"+
		"\u0000\u00f7\u00fd\u0006\f\uffff\uffff\u0000\u00f8\u00f9\u0005\f\u0000"+
		"\u0000\u00f9\u00fa\u0005\u001b\u0000\u0000\u00fa\u00fc\u0006\f\uffff\uffff"+
		"\u0000\u00fb\u00f8\u0001\u0000\u0000\u0000\u00fc\u00ff\u0001\u0000\u0000"+
		"\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000\u00fd\u00fe\u0001\u0000\u0000"+
		"\u0000\u00fe\u0019\u0001\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000\u0000"+
		"\u0000\u0100\u0101\u0005\u001b\u0000\u0000\u0101\u0102\u0006\r\uffff\uffff"+
		"\u0000\u0102\u001b\u0001\u0000\u0000\u0000\u0103\u0104\u0005\u001b\u0000"+
		"\u0000\u0104\u010a\u0006\u000e\uffff\uffff\u0000\u0105\u0106\u0003\u0014"+
		"\n\u0000\u0106\u0107\u0006\u000e\uffff\uffff\u0000\u0107\u0109\u0001\u0000"+
		"\u0000\u0000\u0108\u0105\u0001\u0000\u0000\u0000\u0109\u010c\u0001\u0000"+
		"\u0000\u0000\u010a\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001\u0000"+
		"\u0000\u0000\u010b\u0110\u0001\u0000\u0000\u0000\u010c\u010a\u0001\u0000"+
		"\u0000\u0000\u010d\u010e\u0003\u0016\u000b\u0000\u010e\u010f\u0006\u000e"+
		"\uffff\uffff\u0000\u010f\u0111\u0001\u0000\u0000\u0000\u0110\u010d\u0001"+
		"\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u0112\u0001"+
		"\u0000\u0000\u0000\u0112\u0113\u0006\u000e\uffff\uffff\u0000\u0113\u001d"+
		"\u0001\u0000\u0000\u0000\u0013+-;WZt\u0081\u0085\u0088\u00b9\u00bd\u00c9"+
		"\u00cb\u00d7\u00e3\u00f3\u00fd\u010a\u0110";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}