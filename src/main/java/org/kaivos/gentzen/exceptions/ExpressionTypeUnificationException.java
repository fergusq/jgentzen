package org.kaivos.gentzen.exceptions;

import org.kaivos.gentzen.ExpressionType;

public class ExpressionTypeUnificationException extends Exception {

    private ExpressionType type1;
    private ExpressionType type2;

    public ExpressionTypeUnificationException(ExpressionType type1, ExpressionType type2) {
        super(
            "cannot unify " + type1.toGentzen() + " with " + type2.toGentzen()
        );
        this.type1 = type1;
        this.type2 = type2;
    }

    public ExpressionTypeUnificationException(
        String message,
        ExpressionType type1,
        ExpressionType type2
    ) {
        super(message);
        this.type1 = type1;
        this.type2 = type2;
    }

    public ExpressionType getType1() {
        return type1;
    }

    public ExpressionType getType2() {
        return type2;
    }
}
