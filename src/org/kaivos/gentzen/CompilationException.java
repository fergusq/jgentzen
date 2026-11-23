package org.kaivos.gentzen;

public class CompilationException extends Exception {
    public CompilationException(String message) {
        super(message);
    }
    public CompilationException(Exception cause) {
        super(cause);
    }
}
