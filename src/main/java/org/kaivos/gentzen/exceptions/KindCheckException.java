package org.kaivos.gentzen.exceptions;

public class KindCheckException extends CompilationException {

    public KindCheckException(String message) {
        super(message);
    }

    public KindCheckException(Throwable cause) {
        super(cause);
    }

    public KindCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
