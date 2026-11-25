package org.kaivos.gentzen;

import java.util.Optional;

import org.antlr.v4.runtime.Token;

public class Rule {
    private String name;
    private RuleSignature signature;
    private Optional<Expression> body;
    private Token declStart;
    private Token declStop;

    public Rule(String name, RuleSignature signature, Token start, Token stop) {
        this.name = name;
        this.signature = signature;
        this.body = Optional.empty();
        this.declStart = start;
        this.declStop = stop;
    }

    public String getName() {
        return name;
    }

    public RuleSignature getSignature() {
        return signature;
    }

    public Token getDeclStart() {
        return declStart;
    }

    public Token getDeclStop() {
        return declStop;
    }

    public Optional<Expression> getBody() {
        return body;
    }

    public void setBody(Expression body) {
        this.body = Optional.of(body);
    }
}
