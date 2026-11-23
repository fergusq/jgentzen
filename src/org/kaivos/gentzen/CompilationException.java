package org.kaivos.gentzen;

import org.antlr.v4.runtime.Token;

public class CompilationException extends Exception {
    private Token start;
    private Token stop;
    public CompilationException(String message, Token start, Token stop) {
        super(message);
        this.start = start;
        this.stop = stop;
    }
    public CompilationException(Throwable cause, Token start, Token stop) {
        super(cause);
        this.start = start;
        this.stop = stop;
    }
    public CompilationException(String message, Throwable cause, Token start, Token stop) {
        super(message, cause);
        this.start = start;
        this.stop = stop;
    }
    public Token getStart() {
        return start;
    }
    public Token getStop() {
        return stop;
    }
}
