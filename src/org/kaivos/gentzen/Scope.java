package org.kaivos.gentzen;

import java.util.Map;
import java.util.Optional;

public class Scope {
    private Optional<Scope> parent;
    private Map<String, RuleSignature> definedRules;
    private Map<String, ExpressionType> boundVariables;

    public Scope(Map<String, RuleSignature> definedRules, Map<String, ExpressionType> boundVariables) {
        this.parent = Optional.empty();
        this.definedRules = definedRules;
        this.boundVariables = boundVariables;
    }

    public Scope(Scope parent, Map<String, RuleSignature> definedRules, Map<String, ExpressionType> boundVariables) {
        this.parent = Optional.of(parent);
        this.definedRules = definedRules;
        this.boundVariables = boundVariables;
    }

    public Optional<RuleSignature> resolveRule(String name) {
        if (definedRules.containsKey(name)) {
            return Optional.of(definedRules.get(name).withInstance(nextCounter()));
        } else {
            return parent.flatMap(p -> p.resolveRule(name));
        }
    }

    public Optional<ExpressionType> resolveVariable(String name) {
        if (boundVariables.containsKey(name)) {
            return Optional.of(boundVariables.get(name).withInstance(nextCounter()));
        } else {
            return parent.flatMap(p -> p.resolveVariable(name));
        }
    }

    private static int COUNTER = 0;

    public static int nextCounter() {
        if (COUNTER < 0) {
            throw new RuntimeException("Counter overflow");
        }
        return COUNTER++;
    }
}
