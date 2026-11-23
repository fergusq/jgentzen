package org.kaivos.gentzen;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Namespace {
    private HashMap<String, Rule> definedRules;

    public Namespace() {
        definedRules = new HashMap<>();
    }

    public void addRuleDecl(Rule rule) throws DuplicateException {
        if (definedRules.containsKey(rule.getName())) {
            throw new DuplicateException("duplicate declaration of " + rule.getName());
        }
        definedRules.put(rule.getName(), rule);
    }

    public void addRuleDef(String name, Expression body) throws MissingException, DuplicateException, Expression.TypeCheckException {
        if (!definedRules.containsKey(name)) {
            throw new MissingException("definition of undeclared rule " + name);
        }
        var rule = definedRules.get(name);
        if (rule.getBody().isPresent()) {
            throw new DuplicateException("duplicate definition of " + name);
        }
        rule.setBody(body);
        body.typeCheck(toScope(), rule.getSignature());
    }

    Scope toScope() {
        return new Scope(
            definedRules.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().getSignature())),
            Map.of()
        );
    }

    public static class MissingException extends CompilationException {
        public MissingException(String message) {
            super(message);
        }
    }

    public static class DuplicateException extends CompilationException {
        public DuplicateException(String message) {
            super(message);
        }
    }
}
