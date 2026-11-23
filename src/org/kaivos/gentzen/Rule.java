package org.kaivos.gentzen;

import java.util.Optional;

public class Rule {
    private String name;
    private RuleSignature signature;
    private Optional<Expression> body;

    public Rule(String name, RuleSignature signature) {
        this.name = name;
        this.signature = signature;
        this.body = Optional.empty();
    }

    public String getName() {
        return name;
    }

    public RuleSignature getSignature() {
        return signature;
    }

    public Optional<Expression> getBody() {
        return body;
    }

    public void setBody(Expression body) {
        this.body = Optional.of(body);
    }
}
