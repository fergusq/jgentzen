package org.kaivos.gentzen;

import java.util.Optional;

public class Rule {
    private RuleSignature signature;
    private Optional<Expression> body;

    public Rule(RuleSignature signature) {
        this.signature = signature;
        this.body = Optional.empty();
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
