package org.kaivos.gentzen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;

import org.antlr.v4.runtime.Token;

import org.kaivos.gentzen.RuleSignature.SequentParameter;
import org.kaivos.gentzen.RuleSignature.SequentUnificationResult;
import org.kaivos.gentzen.exceptions.ExpressionTypeUnificationException;
import org.kaivos.gentzen.exceptions.RuleSignatureUnificationException;
import org.kaivos.gentzen.exceptions.SequentUnificationException;
import org.kaivos.gentzen.exceptions.TypeCheckException;

public sealed interface Expression {
    public Token getStart();
    public Token getStop();
    public String toGentzen();
    public String toGentzenWithType();
    default String toGentzenParens() {
        return "(" + toGentzen() + ")";
    }
    default String toGentzenParensWithType() {
        return "(" + toGentzenWithType() + ")";
    }
    public String toScheme();
    public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException;
    public record TypeCheckResult(Type type, SequentUnificationResult unification) {}

    public final class AnonymousRule implements Expression {
        private List<String> above;
        private List<String> params;
        private Expression body;
        private Token start;
        private Token stop;
        private Optional<Type> type;

        public AnonymousRule(List<String> above, List<String> params, Expression body, Token start, Token stop) {
            this.above = Objects.requireNonNull(above);
            this.params = Objects.requireNonNull(params);
            this.body = Objects.requireNonNull(body);
            this.start = Objects.requireNonNull(start);
            this.stop = Objects.requireNonNull(stop);
            this.type = Optional.empty();
        }

        @Override
        public Token getStart() {
            return start;
        }

        @Override
        public Token getStop() {
            return stop;
        }

        public List<String> getParams() {
            return params;
        }

        public List<String> getNormalizedParams() {
            // normalize anonymous params to have different names
            var params = new ArrayList<String>();
            for (int i = 0; i < this.params.size(); i++) {
                var param = this.params.get(i);
                params.add(param.equals("_") ? "_@" + i : param);
            }
            return params;
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
        public String toScheme() {
            String p = Stream.concat(getNormalizedParams().stream(), above.stream().map(a -> "g/" + a)).collect(joining(" "));
            return "(lambda (" + p + ") " + body.toScheme() + ")";
        }

        @Override
        public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException {
            var params = this.getNormalizedParams();
            if (expectedType instanceof RuleSignature rs) {
                if (above.size() != rs.above().size()) {
                    throw new TypeCheckException("rule declaration and rule definition must have the same number of above sequents", start, stop);
                }
                HashMap<String, RuleSignature> definedRules = new HashMap<>();
                for (int i = 0; i < above.size(); i++) {
                    definedRules.put(above.get(i), new RuleSignature(List.of(), rs.above().get(i)));
                }
                HashMap<String, ExpressionType> boundVariables = new HashMap<>();
                var numberedParams = new HashMap<>(rs.below().numberedParams());
                if (rs.below().boundParams().size() != 0) throw new RuntimeException();
                var boundParams = new HashMap<>(rs.below().boundParams()); // should be empty
                var ignoredParams = new HashMap<String, SequentParameter>();
                var contextVars = rs.below().contextVars();
                for (int i = 0; i < params.size(); i++) {
                    if (numberedParams.containsKey(i+1)) {
                        if (!params.get(i).startsWith("_")) {
                            boundVariables.put(params.get(i), numberedParams.get(i+1).type());
                            boundParams.put(params.get(i), numberedParams.get(i+1));
                        } else {
                            ignoredParams.put(params.get(i), numberedParams.get(i+1));
                        }
                        numberedParams.remove(i+1);
                    } else {
                        throw new TypeCheckException("rule declaration " + rs.toGentzen() + " has no parameter $" + (i+1), start, stop);
                    }
                }
                if (numberedParams.size() > 0 ) {
                    throw new TypeCheckException("rule definition is missing " + (numberedParams.size()) + " parameters from " + rs.toGentzen(), start, stop);
                }
                var subScope = new Scope(scope, definedRules, Map.of(), boundVariables);
                var subExpectedType = new RuleSignature(
                    List.of(),
                    new RuleSignature.Sequent(numberedParams, boundParams, contextVars)
                );
                var bodyType = body.typeCheck(subScope, subExpectedType);
                if (bodyType.type() instanceof RuleSignature bodyRs) {
                    if (bodyRs.below().numberedParams().size() != 0) throw new RuntimeException();
                    var newNumberedParams = new HashMap<>(bodyRs.below().numberedParams()); // should be empty
                    var newBoundParams = new HashMap<>(bodyRs.below().boundParams());
                    var newContextVars = bodyRs.below().contextVars();
                    for (int i = 0; i < params.size(); i++) {
                        if (newBoundParams.containsKey(params.get(i))) {
                            newNumberedParams.put(i+1, newBoundParams.get(params.get(i)));
                            newBoundParams.remove(params.get(i));
                        } else if (ignoredParams.containsKey(params.get(i))) {
                            newNumberedParams.put(i+1, ignoredParams.get(params.get(i)));
                        }
                    }
                    var newType = new RuleSignature(
                        rs.above(),
                        new RuleSignature.Sequent(newNumberedParams, newBoundParams, newContextVars)
                    );
                    //System.err.println(this.toGentzen());
                    //System.err.println(" got: " + newType.toGentzen());
                    //System.err.println(" expected: " + expectedType.toGentzen());
                    try {
                        var unification = newType.unify((RuleSignature) expectedType, scope);
                        //System.out.println("\\unify got: " + unification);
                        //System.out.println("\\unify received: " + bodyType.unification());
                        this.type = Optional.of(newType);
                        return new TypeCheckResult(newType, unification.mergeWith(bodyType.unification(), scope));
                    } catch (ExpressionTypeUnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                        throw new TypeCheckException(
                            "type mismatch: "
                            + "expected " + expectedType.toGentzen()
                            + ", but the inferred type of \"" + toGentzen() + "\" is " + newType.toGentzen()
                            + " (the inferred type of \"" + body.toGentzen() + "\" is " + bodyType.type().toGentzen() + ")",
                            e, start, stop
                        );
                    }
                } else {
                    throw new TypeCheckException(
                        "expected " + expectedType.toGentzen()
                        + ", but the inferred type is not a rule definition"
                        + " (the inferred type of \"" + body.toGentzen() + "\" is " + bodyType.type().toGentzen() + ")",
                        start, stop
                    );
                }
            } else {
                throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got rule definition", start, stop);
            }
        }
    }
    public final class Application implements Expression {
        private String rule;
        private List<Expression> args;
        private Token start;
        private Token stop;
        private Optional<Type> type;
        private Optional<Set<String>> flippedToOutput;

        public Application(String rule, List<Expression> args, Token start, Token stop) {
            if (rule == null || args.stream().filter(a -> a == null).count() > 0) {
                throw new NullPointerException();
            }
            this.rule = Objects.requireNonNull(rule);
            this.args = Objects.requireNonNull(args);
            this.start = Objects.requireNonNull(start);
            this.stop = Objects.requireNonNull(stop);
            this.type = Optional.empty();
            this.flippedToOutput = Optional.empty();
        }

        @Override
        public Token getStart() {
            return start;
        }

        @Override
        public Token getStop() {
            return stop;
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
        public String toScheme() {
            String code = Stream.concat(
                Stream.of("g/"+rule),
                args.stream().map(e -> switch (e) {
                    case Application a -> "(lambda () " + a.toScheme() + ")";
                    default -> e.toScheme();
                })
            ).collect(joining(" "));
            if (this.flippedToOutput.isPresent() && this.flippedToOutput.get().size() > 0) {
                String prefix = "", suffix = "";
                for (var flippedParam : this.flippedToOutput.get()) {
                    prefix = "(" + flippedParam + " (lambda (" + flippedParam + ") " + prefix;
                    suffix += "))";
                }
                return prefix + "(" + code + ")" + suffix;
            }
            return "(" + code + ")";
        }

        @Override
        public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException {
            if (!(expectedType instanceof RuleSignature)) {
                throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got rule \"" + rule + "\"", start, stop);
            }
            var et = (RuleSignature) expectedType;
            Optional<RuleSignature> resolved;
            if (rule.equals("neces")) {
                // neces is defined for any number of parameters, so we construct its type here dynamically
                var necesAboveParams = new HashMap<Integer, SequentParameter>();
                var necesBelowParams = new HashMap<Integer, SequentParameter>();
                for (int i = 0; i < args.size()-2; i++) {
                    necesAboveParams.put(i+1, new SequentParameter(new ExpressionType.TypeVar("neces_in_"+i, 0), true));
                    necesBelowParams.put(i+1, new SequentParameter(new ExpressionType.Box(new ExpressionType.TypeVar("neces_in_"+i, 0)), true));
                };
                necesAboveParams.put(args.size()-1, new SequentParameter(new ExpressionType.TypeVar("neces_out", 0), false));
                necesBelowParams.put(args.size()-1, new SequentParameter(new ExpressionType.Box(new ExpressionType.TypeVar("neces_out", 0)), false));
                resolved = Optional.of(new RuleSignature(
                    List.of(new RuleSignature.Sequent(necesAboveParams, Map.of(), List.of())),
                    new RuleSignature.Sequent(necesBelowParams, Map.of(), List.of())
                ).withInstance(Scope.nextCounter()));
            } else {
                resolved = scope.resolveRule(rule);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol \"" + rule + "\" as a rule", start, stop);
                }
            }
            RuleSignature resolvedRule = resolved.get().withInstance(Scope.nextCounter());
            var numberedParams = resolvedRule.below().numberedParams();
            int numParams = numberedParams.size();
            int numAbove = resolvedRule.above().size();
            if (args.size() != numParams + numAbove) {
                throw new TypeCheckException(
                    "wrong number of arguments for \"" + rule
                    + "\", expected " + numParams + " parameters and "
                    + numAbove + " above sequents,"
                    + " got total of " + args.size(),
                    start, stop
                );
            }
            var newBoundParams = new HashMap<>(resolvedRule.below().boundParams());
            var flippedToOutput = new HashSet<String>();
            //System.err.println("initially bound: " + newBoundParams);
            var unification = new RuleSignature.SequentUnificationResult(Map.of(), Map.of());
            for (int i = 0; i < numParams; i++) {
                if (numberedParams.containsKey(i+1)) {
                    var param = numberedParams.get(i+1);
                    if (args.get(i) instanceof Symbol s) {
                        TypeCheckResult tcr;

                        // Implicit negation
                        var etParam = et.below().boundParams().get(s.symbol());
                        //System.err.println("type checked " + param + " against expected " + etParam + " of " + et);
                        if (etParam != null) {
                            if (etParam.input() != param.input()) {
                                // Flip input/output flag and add negation
                                //System.err.println("FLIPPED " + s.symbol() + " to " + (!param.input() ? "input" : "output"));
                                newBoundParams.put(s.symbol(), new SequentParameter(new ExpressionType.Not(param.type()), !param.input()));
                                if (param.input()) flippedToOutput.add(s.symbol());
                                tcr = args.get(i).typeCheck(scope, new ExpressionType.Not(param.type()));
                            } else {
                                tcr = args.get(i).typeCheck(scope, param.type());
                                var checkedType = (ExpressionType) tcr.type;
                                newBoundParams.put(s.symbol(), new SequentParameter(checkedType, param.input()));
                            }
                        } else {
                            // This is going to fail later
                            tcr = args.get(i).typeCheck(scope, param.type());
                            var checkedType = (ExpressionType) tcr.type;
                            newBoundParams.put(s.symbol(), new SequentParameter(checkedType, param.input()));
                        }
                        try {
                            unification = unification.mergeWith(tcr.unification(), scope);
                        } catch (ExpressionTypeUnificationException e) {
                            throw new TypeCheckException(
                                "type mismatch: expected argument " + (i+1)
                                + " of \"" + rule + "\" to be " + param.type().substitute(unification.typeUnification()).toGentzen()
                                + ", but the inferred type was " + tcr.type().toGentzen(),
                                e, start, stop
                            );
                        }
                    } else {
                        throw new TypeCheckException("parameter $" + (i+1) + ": expected a variable, got " + args.get(i).toGentzen(), start, stop);
                    }
                } else {
                    throw new TypeCheckException("parameter $" + (i+1) + " is missing from the sequent " + resolvedRule.below().toGentzen(), start, stop);
                }
                //System.err.println("bound: " + newBoundParams);
            }
            for (int i = 0; i < numAbove; i++) {
                var type = new RuleSignature(List.of(), resolvedRule.above().get(i));
                RuleSignature rs = null;
                try {
                    type = type.substitute(unification, scope, true);
                    var value = args.get(i + numParams);
                    var tcr = value.typeCheck(scope, type);
                    rs = (RuleSignature) tcr.type;
                    unification = unification.mergeWith(tcr.unification(), scope).mergeWith(rs.unify(type, scope), scope);
                } catch (ExpressionTypeUnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException(
                        "type mismatch:"
                        + " expected above sequent " + (i+1) + " of \"" + rule + "\" to be " + type.toGentzen()
                        + ", but the above sequent was inferred to have type " + (rs == null ? "_" : rs.toGentzen()),
                        e, start, stop
                    );
                }
            }
            var newType = new RuleSignature(
                List.of(),
                new RuleSignature.Sequent(Map.of(), newBoundParams, resolvedRule.below().contextVars())
            );
            try {
                newType = newType.substitute(unification, scope);
                unification = unification.mergeWith(newType.unify(et, scope), scope);
                newType = newType.substitute(unification, scope);
            } catch (ExpressionTypeUnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                throw new TypeCheckException(
                    "type mismatch: expected below sequent of " + rule + " to be " + et.toGentzen()
                    + ", but the below sequent was inferred to have type " + newType.toGentzen()
                    , e, start, stop
                );
            }
            //System.out.println("/unify: " + unification);
            //System.err.println(this.toGentzen());
            //System.err.println(" expected: " + et.toGentzen());
            //System.err.println(" got: " + newType.toGentzen());
            //System.err.println(" got after unification: " + newType.toGentzen());
            this.type = Optional.of(newType);
            this.flippedToOutput = Optional.of(flippedToOutput);
            return new TypeCheckResult(newType, unification);
        }
    }
    public final class Symbol implements Expression {
        private String symbol;
        private Token start;
        private Token stop;
        private Optional<Type> type;

        public Symbol(String symbol, Token start, Token stop) {
            this.symbol = Objects.requireNonNull(symbol);
            this.start = Objects.requireNonNull(start);
            this.stop = Objects.requireNonNull(stop);
            this.type = Optional.empty();
        }

        public String symbol() {
            return symbol;
        }

        @Override
        public Token getStart() {
            return start;
        }

        @Override
        public Token getStop() {
            return stop;
        }

        @Override
        public String toGentzen() {
            return symbol;
        }

        @Override
        public String toGentzenWithType() {
            return toGentzen() + " :: " + type.get().toGentzen();
        }

        @Override
        public String toGentzenParens() {
            return toGentzen();
        }

        @Override
        public String toScheme() {
            return type.get() instanceof RuleSignature ? "g/" + symbol : symbol;
        }

        @Override
        public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException {
            if (expectedType instanceof RuleSignature rs) {
                Optional<RuleSignature> resolved = scope.resolveRule(symbol);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol \"" + symbol + "\" as a rule", start, stop);
                }
                try {
                    var unification = rs.unify(resolved.get(), scope);
                    var newType = resolved.get().substitute(unification, scope);
                    this.type = Optional.of(newType);
                    return new TypeCheckResult(newType, unification);
                } catch (ExpressionTypeUnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException("type mismatch: expected " + expectedType.toGentzen() + ", but the inferred type of \"" + symbol + "\" was " + resolved.get().toGentzen(), e, start, stop);
                }
            } else {
                var et = (ExpressionType) expectedType;
                Optional<ExpressionType> resolved = scope.resolveVariable(symbol);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol \"" + symbol + "\" as a variable", start, stop);
                }
                try {
                    //System.err.println(this.toGentzen());
                    //System.err.println(" got: " + resolved.get().toGentzen());
                    //System.err.println(" expected: " + expectedType.toGentzen());
                    var unification = et.unify(resolved.get(), scope);
                    var newType = resolved.get().substitute(unification);
                    this.type = Optional.of(newType);
                    return new TypeCheckResult(newType, new RuleSignature.SequentUnificationResult(unification, Map.of()));
                } catch (ExpressionTypeUnificationException e) {
                    throw new TypeCheckException("type mismatch: expected " + expectedType.toGentzen() + ", but the inferred type of \"" + symbol + "\" was " + resolved.get().toGentzen(), e, start, stop);
                }
            }
        }
    }

    public sealed abstract class Literal implements Expression permits StringLiteral, NaturalLiteral {
        private RuleSignature type;
        private Token start;
        private Token stop;

        protected Literal(RuleSignature type, Token start, Token stop) {
            this.type = Objects.requireNonNull(type);
            this.start = Objects.requireNonNull(start);
            this.stop = Objects.requireNonNull(stop);
        }

        public RuleSignature type() {
            return type;
        }

        @Override
        public Token getStart() {
            return start;
        }

        @Override
        public Token getStop() {
            return stop;
        }

        @Override
        public String toGentzenWithType() {
            return toGentzen() + " :: " + type.toGentzen();
        }

        @Override
        public String toGentzenParens() {
            return toGentzen();
        }

        @Override
        public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException {
            if (expectedType instanceof RuleSignature rs) {
                try {
                    var unification = rs.unify(type, scope);
                    return new TypeCheckResult(type, unification);
                } catch (ExpressionTypeUnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException("type error", e, start, stop);
                }
            } else {
                throw new TypeCheckException("literal " + toGentzen() + " can only be used in a rule position", start, stop);
            }
        }
    }

    public final class StringLiteral extends Literal {
        /** () / (|- $1(.. |\|)) */
        public static final RuleSignature LITERAL_TYPE = new RuleSignature(
            List.of(),
            new RuleSignature.Sequent(
                Map.of(1, new SequentParameter(new ExpressionType.List(new ExpressionType.Nat()), false)),
                Map.of(),
                List.of()
            )
        );

        private String literal;

        public StringLiteral(String literal, Token start, Token stop) {
            super(LITERAL_TYPE, start, stop);
            this.literal = Objects.requireNonNull(literal);
        }

        @Override
        public String toGentzen() {
            return literal;
        }

        @Override
        public String toScheme() {
            return "(lambda (output) (output (map char->integer (string->list " + literal + "))))";
        }
    }

    public final class NaturalLiteral extends Literal {
        /** () / (|- $1(|\|)) */
        public static final RuleSignature LITERAL_TYPE = new RuleSignature(
            List.of(),
            new RuleSignature.Sequent(
                Map.of(1, new SequentParameter(new ExpressionType.Nat(), false)),
                Map.of(),
                List.of()
            )
        );

        private int literal;

        public NaturalLiteral(int literal, Token start, Token stop) {
            super(LITERAL_TYPE, start, stop);
            this.literal = literal;
        }

        @Override
        public String toGentzen() {
            return ""+literal;
        }

        @Override
        public String toScheme() {
            return "(lambda (output) (output " + literal + "))";
        }
    }
}
