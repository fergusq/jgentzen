package org.kaivos.gentzen.exceptions;

import org.kaivos.gentzen.RuleSignature.Sequent;

public class SequentUnificationException extends Exception {

    private Sequent sequent1;
    private Sequent sequent2;

    public SequentUnificationException(Sequent sequent1, Sequent sequent2) {
        super(
            "cannot unify " +
                sequent1.toGentzen() +
                " and " +
                sequent2.toGentzen()
        );
        this.sequent1 = sequent1;
        this.sequent2 = sequent2;
    }

    public SequentUnificationException(
        String message,
        Sequent sequent1,
        Sequent sequent2
    ) {
        super(
            "cannot unify " +
                sequent1.toGentzen() +
                " and " +
                sequent2.toGentzen() +
                ": " +
                message
        );
        this.sequent1 = sequent1;
        this.sequent2 = sequent2;
    }

    public Sequent getSequent1() {
        return sequent1;
    }

    public Sequent getSequent2() {
        return sequent2;
    }
}
