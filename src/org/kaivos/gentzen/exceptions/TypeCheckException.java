package org.kaivos.gentzen.exceptions;

import org.antlr.v4.runtime.Token;

public class TypeCheckException extends CompilationException {

    public TypeCheckException(String message, Token start, Token stop) {
        super(message, start, stop);
    }

    public TypeCheckException(
        String message,
        Throwable cause,
        Token start,
        Token stop
    ) {
        super(message, cause, start, stop);
    }
}
