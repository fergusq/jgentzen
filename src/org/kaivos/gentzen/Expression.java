package org.kaivos.gentzen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;

import org.kaivos.gentzen.ExpressionType.UnificationException;
import org.kaivos.gentzen.RuleSignature.RuleSignatureUnificationException;
import org.kaivos.gentzen.RuleSignature.SequentUnificationException;

public sealed interface Expression {
    public String toGentzen();
    public String toGentzenWithType();
    default String toGentzenParens() {
        return "(" + toGentzen() + ")";
    }
    default String toGentzenParensWithType() {
        return "(" + toGentzenWithType() + ")";
    }
    public Type typeCheck(Namespace ns, Type expectedType) throws TypeCheckException;
    public class TypeCheckException extends Exception {
        public TypeCheckException(String message) {
            super(message);
        }
        public TypeCheckException(Exception cause) {
            super(cause);
        }
    }
    public final class AnonymousRule implements Expression {
        private List<String> above;
        private List<String> params;
        private Expression body;
        private Optional<Type> type;

        public AnonymousRule(List<String> above, List<String> params, Expression body) {
            this.above = above;
            this.params = params;
            this.body = body;
            this.type = Optional.empty();
        }

        @Override
        public String toGentzen() {
            if (above.size() == 0) {
                return "[" + params.stream().collect(joining(",")) + "] " + body.toGentzen();
            } else {
                return "[" + above.stream().collect(joining(",")) + "/" + params.stream().collect(joining(",")) + "] " + body.toGentzen();
            }
        }

        @Override
        public String toGentzenWithType() {
            if (above.size() == 0) {
                return "[" + params.stream().collect(joining(",")) + "] " + body.toGentzenParensWithType() + " :: " + type.get().toGentzen();
            } else {
                return "[" + above.stream().collect(joining(",")) + "/" + params.stream().collect(joining(",")) + "] " + body.toGentzenParensWithType() + " :: " + type.get().toGentzen();
            }
        }

        @Override
        public Type typeCheck(Namespace ns, Type expectedType) throws TypeCheckException {
            if (expectedType instanceof RuleSignature rs) {
                if (above.size() != rs.above().size()) {
                    throw new TypeCheckException("rule declaration and rule definition must have the same number of above sequents");
                }
                HashMap<String, RuleSignature> definedRules = new HashMap<>();
                for (int i = 0; i < above.size(); i++) {
                    definedRules.put(above.get(i), new RuleSignature(List.of(), rs.above().get(i)));
                }
                HashMap<String, ExpressionType> boundVariables = new HashMap<>();
                var numberedInputParams = new HashMap<>(rs.below().input().numberedParams());
                var boundInputParams = new HashMap<>(rs.below().input().boundParams());
                var inputContextVars = rs.below().input().contextVars();
                var numberedOutputParams = new HashMap<>(rs.below().output().numberedParams());
                var boundOutputParams = new HashMap<>(rs.below().output().boundParams());
                var outputContextVars = rs.below().output().contextVars();
                for (int i = 0; i < params.size(); i++) {
                    if (numberedInputParams.containsKey(i+1)) {
                        boundVariables.put(params.get(i), numberedInputParams.get(i+1));
                        boundInputParams.put(params.get(i), numberedInputParams.get(i+1));
                        numberedInputParams.remove(i+1);
                    } else if (numberedOutputParams.containsKey(i+1)) {
                        boundVariables.put(params.get(i), numberedOutputParams.get(i+1));
                        boundOutputParams.put(params.get(i), numberedOutputParams.get(i+1));
                        numberedOutputParams.remove(i+1);
                    } else {
                        throw new TypeCheckException("rule declaration has no parameter $" + (i+1));
                    }
                }
                var subNs = new Namespace(ns, definedRules, boundVariables);
                var subExpectedType = new RuleSignature(
                    List.of(),
                    new RuleSignature.Sequent(
                        new RuleSignature.SequentParameters(numberedInputParams, boundInputParams, inputContextVars),
                        new RuleSignature.SequentParameters(numberedOutputParams, boundOutputParams, outputContextVars)
                    )
                );
                var bodyType = body.typeCheck(subNs, subExpectedType);
                if (bodyType instanceof RuleSignature bodyRs) {
                var newNumberedInputParams = new HashMap<>(bodyRs.below().input().numberedParams());
                var newBoundInputParams = new HashMap<>(bodyRs.below().input().boundParams());
                var newInputContextVars = bodyRs.below().input().contextVars();
                var newNumberedOutputParams = new HashMap<>(bodyRs.below().output().numberedParams());
                var newBoundOutputParams = new HashMap<>(bodyRs.below().output().boundParams());
                var newOutputContextVars = bodyRs.below().output().contextVars();
                for (int i = 0; i < params.size(); i++) {
                    if (newBoundInputParams.containsKey(params.get(i))) {
                        newNumberedInputParams.put(i+1, newBoundInputParams.get(params.get(i)));
                        newBoundInputParams.remove(params.get(i));
                    } else if (newBoundOutputParams.containsKey(params.get(i))) {
                        newNumberedOutputParams.put(i+1, newBoundOutputParams.get(params.get(i)));
                        newBoundOutputParams.remove(params.get(i));
                    }
                }
                var newType = new RuleSignature(
                    rs.above(),
                    new RuleSignature.Sequent(
                        new RuleSignature.SequentParameters(newNumberedInputParams, newBoundInputParams, newInputContextVars),
                        new RuleSignature.SequentParameters(newNumberedOutputParams, newBoundOutputParams, newOutputContextVars)
                    )
                );
                this.type = Optional.of(newType);
                return newType;
                } else {
                    throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got " + bodyType.toGentzen());
                }
            } else {
                throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got rule definition");
            }
        }
    }
    public final class Application implements Expression {
        private String rule;
        private List<Expression> args;
        private Optional<Type> type;

        public Application(String rule, List<Expression> args) {
            this.rule = rule;
            this.args = args;
            this.type = Optional.empty();
        }

        @Override
        public String toGentzen() {
            return Stream.concat(Stream.of(rule), args.stream().map(Expression::toGentzenParens)).collect(joining(" "));
        }

        @Override
        public String toGentzenWithType() {
            return Stream.concat(Stream.of(rule), args.stream().map(Expression::toGentzenParensWithType)).collect(joining(" ")) + " :: " + type.get().toGentzen();
        }

        @Override
        public Type typeCheck(Namespace ns, Type expectedType) throws TypeCheckException {
            if (!(expectedType instanceof RuleSignature)) {
                throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got rule " + rule);
            }
            Optional<RuleSignature> resolved = ns.resolveRule(rule);
            if (resolved.isEmpty()) {
                throw new TypeCheckException("cannot resolve symbol " + rule + " as a rule");
            }
            RuleSignature resolvedRule = resolved.get();
            var numberedInputParams = resolvedRule.below().input().numberedParams();
            var numberedOutputParams = resolvedRule.below().output().numberedParams();
            int numParams = numberedInputParams.size() + numberedOutputParams.size();
            int numAbove = resolvedRule.above().size();
            if (args.size() != numParams + numAbove) {
                throw new TypeCheckException("wrong number of arguments for " + rule + ", expected " + numParams + " parameters and " + numAbove + " above sequents");
            }
            var newBoundInputParams = new HashMap<>(resolvedRule.below().input().boundParams());
            var newBoundOutputParams = new HashMap<>(resolvedRule.below().output().boundParams());
            for (int i = 0; i < numParams; i++) {
                if (numberedInputParams.containsKey(i+1) && numberedOutputParams.containsKey(i+1)) {
                    throw new TypeCheckException("parameter $" + (i+1) + " appears on both the left and the right side of the sequent " + resolvedRule.below().toGentzen());
                } else if (!numberedInputParams.containsKey(i+1) && !numberedOutputParams.containsKey(i+1)) {
                    throw new TypeCheckException("parameter $" + (i+1) + " is missing from the sequent " + resolvedRule.below().toGentzen());
                } else if (numberedInputParams.containsKey(i+1)) {
                    if (args.get(i) instanceof Symbol s) {
                        Type t = args.get(i).typeCheck(ns, numberedInputParams.get(i+1));
                        newBoundInputParams.put(s.symbol(), (ExpressionType) t);
                    } else {
                        throw new TypeCheckException("parameter $" + (i+1) + ": expected a variable, got " + args.get(i).toGentzen());
                    }
                } else if (numberedOutputParams.containsKey(i+1)) {
                    if (args.get(i) instanceof Symbol s) {
                        Type t = args.get(i).typeCheck(ns, numberedOutputParams.get(i+1));
                        newBoundOutputParams.put(s.symbol(), (ExpressionType) t);
                    } else {
                        throw new TypeCheckException("parameter $" + (i+1) + ": expected a variable, got " + args.get(i).toGentzen());
                    }
                } else {
                    throw new RuntimeException("dead code");
                }
            }
            RuleSignature.SequentUnificationResult unification = new RuleSignature.SequentUnificationResult(Map.of(), Map.of());
            for (int i = 0; i < numAbove; i++) {
                var type = new RuleSignature(List.of(), resolvedRule.above().get(i));
                var value = args.get(i + numParams);
                var t = (RuleSignature) value.typeCheck(ns, type);
                try {
                    unification = unification.mergeWith(t.unify(type));
                } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException(e);
                }
            }
            var newType = new RuleSignature(
                List.of(),
                new RuleSignature.Sequent(
                    new RuleSignature.SequentParameters(Map.of(), newBoundInputParams, resolvedRule.below().input().contextVars()),
                    new RuleSignature.SequentParameters(Map.of(), newBoundOutputParams, resolvedRule.below().output().contextVars())
                )
            ).substitute(unification);
            try {
                System.err.println(this.toGentzen());
                System.err.println("got: " + newType.toGentzen());
                System.err.println("expected: " + expectedType.toGentzen());
                var finalUnification = newType.unify((RuleSignature) expectedType);
                newType = newType.substitute(finalUnification);
            } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                throw new TypeCheckException(e);
            }
            this.type = Optional.of(newType);
            return newType;
        }
    }
    public final class Symbol implements Expression {
        private String symbol;
        private Optional<Type> type;

        public Symbol(String symbol) {
            this.symbol = symbol;
            this.type = Optional.empty();
        }

        public String symbol() {
            return symbol;
        }

        @Override
        public String toGentzen() {
            return symbol;
        }

        @Override
        public String toGentzenWithType() {
            return symbol + " :: " + type.get().toGentzen();
        }

        @Override
        public String toGentzenParens() {
            return symbol;
        }

        @Override
        public Type typeCheck(Namespace ns, Type expectedType) throws TypeCheckException {
            if (expectedType instanceof RuleSignature rs) {
                Optional<RuleSignature> resolved = ns.resolveRule(symbol);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol " + symbol + " as a rule");
                }
                try {
                    var unification = rs.unify(resolved.get());
                    var newType = resolved.get().substitute(unification);
                    this.type = Optional.of(newType);
                    return newType;
                } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException(e);
                }
            } else {
                var et = (ExpressionType) expectedType;
                Optional<ExpressionType> resolved = ns.resolveVariable(symbol);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol " + symbol + " as a variable");
                }
                try {
                    var unification = et.unify(resolved.get());
                    var newType = resolved.get().substitute(unification);
                    this.type = Optional.of(newType);
                    return newType;
                } catch (UnificationException e) {
                    throw new TypeCheckException(e);
                }
            }
        }
    }
}
