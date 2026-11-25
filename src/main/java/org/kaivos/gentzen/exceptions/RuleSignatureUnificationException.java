package org.kaivos.gentzen.exceptions;

import org.kaivos.gentzen.RuleSignature;

public class RuleSignatureUnificationException extends Exception {

    private RuleSignature rule1;
    private RuleSignature rule2;

    public RuleSignatureUnificationException(
        RuleSignature rule1,
        RuleSignature rule2
    ) {
        super(
            "cannot unify " + rule1.toGentzen() + " and " + rule2.toGentzen()
        );
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    public RuleSignatureUnificationException(
        String message,
        RuleSignature rule1,
        RuleSignature rule2
    ) {
        super(
            "cannot unify " +
                rule1.toGentzen() +
                " and " +
                rule2.toGentzen() +
                ": " +
                message
        );
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    public RuleSignature getRule1() {
        return rule1;
    }

    public RuleSignature getRule2() {
        return rule2;
    }
}
