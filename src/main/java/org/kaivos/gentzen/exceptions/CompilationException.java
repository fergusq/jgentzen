package org.kaivos.gentzen.exceptions;

import java.util.Optional;
import org.antlr.v4.runtime.Token;

public class CompilationException extends Exception {

    private Optional<Token> start;
    private Optional<Token> stop;

    public CompilationException(String message, Token start, Token stop) {
        super(message);
        this.start = Optional.of(start);
        this.stop = Optional.of(stop);
    }

    public CompilationException(Throwable cause, Token start, Token stop) {
        super(cause);
        this.start = Optional.of(start);
        this.stop = Optional.of(stop);
    }

    public CompilationException(
        String message,
        Throwable cause,
        Token start,
        Token stop
    ) {
        super(message, cause);
        this.start = Optional.of(start);
        this.stop = Optional.of(stop);
    }

    public CompilationException(String message) {
        super(message);
        this.start = Optional.empty();
        this.stop = Optional.empty();
    }

    public CompilationException(Throwable cause) {
        super(cause);
        this.start = Optional.empty();
        this.stop = Optional.empty();
    }

    public CompilationException(String message, Throwable cause) {
        super(message, cause);
        this.start = Optional.empty();
        this.stop = Optional.empty();
    }

    public String getLocation(String fallback) {
        return start
            .map(
                s ->
                    "[" +
                    s.getLine() +
                    ":" +
                    (s.getCharPositionInLine() + 1) +
                    "]"
            )
            .orElse(fallback);
    }

    public Optional<Token> getStart() {
        return start;
    }

    public Optional<Token> getStop() {
        return stop;
    }
}
