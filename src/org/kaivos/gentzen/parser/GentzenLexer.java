// Generated from Gentzen.g4 by ANTLR 4.13.2

package org.kaivos.gentzen.parser;

import java.util.HashMap;
import java.util.Map;

import org.kaivos.gentzen.DeclDef;
import org.kaivos.gentzen.Expression;
import org.kaivos.gentzen.ExpressionType;
import org.kaivos.gentzen.Rule;
import org.kaivos.gentzen.RuleSignature;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GentzenLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, IDENT=27, NUM=28, STRING=29, WS=30, LINE_COMMENT=31;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
			"T__25", "IDENT", "NUM", "STRING", "WS", "LINE_COMMENT"
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


	public GentzenLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Gentzen.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u001f\u00a7\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0005"+
		"\u001a\u0083\b\u001a\n\u001a\f\u001a\u0086\t\u001a\u0001\u001b\u0004\u001b"+
		"\u0089\b\u001b\u000b\u001b\f\u001b\u008a\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0005\u001c\u0091\b\u001c\n\u001c\f\u001c\u0094\t\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001d\u0004\u001d\u0099\b\u001d\u000b\u001d"+
		"\f\u001d\u009a\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0005\u001e"+
		"\u00a1\b\u001e\n\u001e\f\u001e\u00a4\t\u001e\u0001\u001e\u0001\u001e\u0000"+
		"\u0000\u001f\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016"+
		"-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f\u0001"+
		"\u0000\u0007\u0003\u0000AZ__az\u0005\u0000--/9AZ__az\u0001\u000009\u0001"+
		"\u0000\"\"\u0004\u0000\"\"nnrrtt\u0003\u0000\t\n\r\r  \u0002\u0000\n\n"+
		"\r\r\u00ac\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%"+
		"\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000"+
		"\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000"+
		"3\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001"+
		"\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000"+
		"\u0000\u0000=\u0001\u0000\u0000\u0000\u0001?\u0001\u0000\u0000\u0000\u0003"+
		"B\u0001\u0000\u0000\u0000\u0005D\u0001\u0000\u0000\u0000\u0007G\u0001"+
		"\u0000\u0000\u0000\tI\u0001\u0000\u0000\u0000\u000bK\u0001\u0000\u0000"+
		"\u0000\rM\u0001\u0000\u0000\u0000\u000fO\u0001\u0000\u0000\u0000\u0011"+
		"Q\u0001\u0000\u0000\u0000\u0013T\u0001\u0000\u0000\u0000\u0015V\u0001"+
		"\u0000\u0000\u0000\u0017X\u0001\u0000\u0000\u0000\u0019Z\u0001\u0000\u0000"+
		"\u0000\u001b\\\u0001\u0000\u0000\u0000\u001d^\u0001\u0000\u0000\u0000"+
		"\u001fa\u0001\u0000\u0000\u0000!d\u0001\u0000\u0000\u0000#f\u0001\u0000"+
		"\u0000\u0000%j\u0001\u0000\u0000\u0000\'m\u0001\u0000\u0000\u0000)q\u0001"+
		"\u0000\u0000\u0000+t\u0001\u0000\u0000\u0000-w\u0001\u0000\u0000\u0000"+
		"/z\u0001\u0000\u0000\u00001|\u0001\u0000\u0000\u00003~\u0001\u0000\u0000"+
		"\u00005\u0080\u0001\u0000\u0000\u00007\u0088\u0001\u0000\u0000\u00009"+
		"\u008c\u0001\u0000\u0000\u0000;\u0098\u0001\u0000\u0000\u0000=\u009e\u0001"+
		"\u0000\u0000\u0000?@\u0005<\u0000\u0000@A\u0005-\u0000\u0000A\u0002\u0001"+
		"\u0000\u0000\u0000BC\u0005;\u0000\u0000C\u0004\u0001\u0000\u0000\u0000"+
		"DE\u0005-\u0000\u0000EF\u0005>\u0000\u0000F\u0006\u0001\u0000\u0000\u0000"+
		"GH\u0005:\u0000\u0000H\b\u0001\u0000\u0000\u0000IJ\u0005=\u0000\u0000"+
		"J\n\u0001\u0000\u0000\u0000KL\u0005(\u0000\u0000L\f\u0001\u0000\u0000"+
		"\u0000MN\u0005)\u0000\u0000N\u000e\u0001\u0000\u0000\u0000OP\u0005/\u0000"+
		"\u0000P\u0010\u0001\u0000\u0000\u0000QR\u0005|\u0000\u0000RS\u0005-\u0000"+
		"\u0000S\u0012\u0001\u0000\u0000\u0000TU\u0005*\u0000\u0000U\u0014\u0001"+
		"\u0000\u0000\u0000VW\u0005$\u0000\u0000W\u0016\u0001\u0000\u0000\u0000"+
		"XY\u0005,\u0000\u0000Y\u0018\u0001\u0000\u0000\u0000Z[\u0005?\u0000\u0000"+
		"[\u001a\u0001\u0000\u0000\u0000\\]\u0005~\u0000\u0000]\u001c\u0001\u0000"+
		"\u0000\u0000^_\u0005/\u0000\u0000_`\u0005\\\u0000\u0000`\u001e\u0001\u0000"+
		"\u0000\u0000ab\u0005\\\u0000\u0000bc\u0005/\u0000\u0000c \u0001\u0000"+
		"\u0000\u0000de\u0005@\u0000\u0000e\"\u0001\u0000\u0000\u0000fg\u0005|"+
		"\u0000\u0000gh\u0005\\\u0000\u0000hi\u0005|\u0000\u0000i$\u0001\u0000"+
		"\u0000\u0000jk\u0005+\u0000\u0000kl\u0005+\u0000\u0000l&\u0001\u0000\u0000"+
		"\u0000mn\u0005_\u0000\u0000no\u0005|\u0000\u0000op\u0005_\u0000\u0000"+
		"p(\u0001\u0000\u0000\u0000qr\u0005.\u0000\u0000rs\u0005.\u0000\u0000s"+
		"*\u0001\u0000\u0000\u0000tu\u0005[\u0000\u0000uv\u0005]\u0000\u0000v,"+
		"\u0001\u0000\u0000\u0000wx\u0005<\u0000\u0000xy\u0005>\u0000\u0000y.\u0001"+
		"\u0000\u0000\u0000z{\u0005!\u0000\u0000{0\u0001\u0000\u0000\u0000|}\u0005"+
		"[\u0000\u0000}2\u0001\u0000\u0000\u0000~\u007f\u0005]\u0000\u0000\u007f"+
		"4\u0001\u0000\u0000\u0000\u0080\u0084\u0007\u0000\u0000\u0000\u0081\u0083"+
		"\u0007\u0001\u0000\u0000\u0082\u0081\u0001\u0000\u0000\u0000\u0083\u0086"+
		"\u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0084\u0085"+
		"\u0001\u0000\u0000\u0000\u00856\u0001\u0000\u0000\u0000\u0086\u0084\u0001"+
		"\u0000\u0000\u0000\u0087\u0089\u0007\u0002\u0000\u0000\u0088\u0087\u0001"+
		"\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000\u008a\u0088\u0001"+
		"\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b8\u0001\u0000"+
		"\u0000\u0000\u008c\u0092\u0005\"\u0000\u0000\u008d\u0091\b\u0003\u0000"+
		"\u0000\u008e\u008f\u0005\\\u0000\u0000\u008f\u0091\u0007\u0004\u0000\u0000"+
		"\u0090\u008d\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000"+
		"\u0091\u0094\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0095\u0001\u0000\u0000\u0000"+
		"\u0094\u0092\u0001\u0000\u0000\u0000\u0095\u0096\u0005\"\u0000\u0000\u0096"+
		":\u0001\u0000\u0000\u0000\u0097\u0099\u0007\u0005\u0000\u0000\u0098\u0097"+
		"\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u0098"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009c"+
		"\u0001\u0000\u0000\u0000\u009c\u009d\u0006\u001d\u0000\u0000\u009d<\u0001"+
		"\u0000\u0000\u0000\u009e\u00a2\u0005#\u0000\u0000\u009f\u00a1\b\u0006"+
		"\u0000\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a1\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a2\u00a3\u0001\u0000"+
		"\u0000\u0000\u00a3\u00a5\u0001\u0000\u0000\u0000\u00a4\u00a2\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a6\u0006\u001e\u0000\u0000\u00a6>\u0001\u0000\u0000"+
		"\u0000\u0007\u0000\u0084\u008a\u0090\u0092\u009a\u00a2\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}