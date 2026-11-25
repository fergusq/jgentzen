package org.kaivos.gentzen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;

public class MacroProcessor implements TokenSource {
    private static record Macro(List<String> params, List<Token> tokens) {}

    private TokenSource tokenSource;
    private ArrayList<Token> tokens;
    private int position;
    private HashMap<String, Macro> macros;

    public MacroProcessor(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
        this.tokens = new ArrayList<>();
        this.position = 0;
        this.macros = new HashMap<>();
    }

	@Override
	public Token nextToken() {
		if (tokens.size() == 0) {
		    loadTokens();
		}
		Token token = tokens.get(position);
		if (token.getType() != Token.EOF) {
		    position++;
		}
		return token;
	}

	private void loadTokens() {
	    Token token;
	    do {
			token = tokenSource.nextToken();
			tokens.add(token);
		} while (token.getType() != Token.EOF);

		parseMacroDefinitions();
		expandMacros();
	}

	private void parseMacroDefinitions() {
	    for (int i = 0; i < tokens.size(); i++) {
			var tokenText = tokens.get(i).getText();
			if (tokenText.equals("==")) {
			    // Find where the macro starts
			    int start = i;
			    for (; start >= 0 && !tokens.get(start).getText().equals(";"); start--);
				start++;

				if (start == i) {
				    // No macro name found, continue
				    continue;
				}

				// Find where it ends
				int stop = i, depth = 0;
				for (; stop < tokens.size(); stop++) {
				    switch (tokens.get(stop).getText()) {
						case "(" -> { depth++; }
						case ")" -> { depth--; }
						case "[" -> { depth++; }
						case "]" -> { depth--; }
						case "{" -> { depth++; }
						case "}" -> { depth--; }
					}
					if (depth == 0 && tokens.get(stop).getText().equals(";")) {
					    break;
					} else if (depth < 0) {
					    throw new RuntimeException("invalid macro definition: mismatched delimiters");
					}
				}

				System.err.println("Found macro at " + start + ".." + i + ".." + stop);

				var macroTokens = new ArrayList<>(tokens.subList(start, stop));
				for (int k = start; k <= stop && start < tokens.size(); k++) tokens.remove(start); // Removing shifts items so we always remove at start
				i = start; // reset i

				var macroName = macroTokens.removeFirst().getText();
				var params = new ArrayList<String>();
				while (!macroTokens.get(0).getText().equals("==")) {
				    params.add(macroTokens.removeFirst().getText());
				}

				// eat ==
				macroTokens.removeFirst();
				this.macros.put(macroName, new Macro(params, macroTokens));
			} else if (macros.containsKey(tokenText)) {
			    // eat macro name
				tokens.remove(i);
			    var macro = macros.get(tokenText);
				var args = new ArrayList<ArrayList<Token>>();
				for (int arg = 0; arg < macro.params().size(); arg++) {
				    var argTokens = new ArrayList<Token>();
				    int depth = 0;
					do {
					    Token t = tokens.remove(i);
						argTokens.add(t);
						switch (t.getText()) {
    						case "(" -> { depth++; }
    						case ")" -> { depth--; }
    						case "[" -> { depth++; }
    						case "]" -> { depth--; }
    						case "{" -> { depth++; }
    						case "}" -> { depth--; }
						}
					    if (depth < 0) {
							throw new RuntimeException("invalid macro use: mismatched delimiters");
						}
					} while (depth != 0);
					args.add(argTokens);
				}
				for (var macroToken : macro.tokens()) {
				    var index = macro.params().indexOf(macroToken.getText());
					if (index != -1) {
					    tokens.addAll(i, args.get(index));
						i += args.get(index).size();
					} else {
					    tokens.add(i++, macroToken);
					}
				}
			}
		}
	}

	private void expandMacros() {

	}

	@Override
	public int getCharPositionInLine() {
		return 0;
	}

	@Override
	public int getLine() {
		return 0;
	}

	@Override
	public CharStream getInputStream() {
		return tokenSource.getInputStream();
	}

	@Override
	public String getSourceName() {
		return tokenSource.getSourceName();
	}

	@Override
	public TokenFactory<?> getTokenFactory() {
		return tokenSource.getTokenFactory();
	}

	@Override
	public void setTokenFactory(TokenFactory<?> arg0) {
	    throw new RuntimeException("not implemented");
	}
}
