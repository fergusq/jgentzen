package org.kaivos.gentzen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import org.antlr.v4.runtime.Token;

import org.kaivos.gentzen.ExpressionType.UnificationException;
import org.kaivos.gentzen.RuleSignature.RuleSignatureUnificationException;
import org.kaivos.gentzen.RuleSignature.SequentUnificationException;
import org.kaivos.gentzen.RuleSignature.SequentUnificationResult;

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
    public class TypeCheckException extends CompilationException {
        public TypeCheckException(String message, Token start, Token stop) {
            super(message, start, stop);
        }
        public TypeCheckException(String message, Throwable cause, Token start, Token stop) {
            super(message, cause, start, stop);
        }
    }
    public final class AnonymousRule implements Expression {
        private List<String> above;
        private List<String> params;
        private Expression body;
        private Token start;
        private Token stop;
        private Optional<Type> type;

        public AnonymousRule(List<String> above, List<String> params, Expression body, Token start, Token stop) {
            this.above = above;
            this.params = params;
            this.body = body;
            this.start = start;
            this.stop = stop;
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
            String p = Stream.concat(params.stream(), above.stream().map(a -> "g/" + a)).collect(joining(" "));
            return "(lambda (" + p + ") " + body.toScheme() + ")";
        }

        @Override
        public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException {
            if (expectedType instanceof RuleSignature rs) {
                if (above.size() != rs.above().size()) {
                    throw new TypeCheckException("rule declaration and rule definition must have the same number of above sequents", start, stop);
                }
                HashMap<String, RuleSignature> definedRules = new HashMap<>();
                for (int i = 0; i < above.size(); i++) {
                    definedRules.put(above.get(i), new RuleSignature(List.of(), rs.above().get(i)));
                }
                HashMap<String, ExpressionType> boundVariables = new HashMap<>();
                var numberedInputParams = new HashMap<>(rs.below().input().numberedParams());
                var boundInputParams = new HashMap<>(rs.below().input().boundParams());
                var ignoredInputParams = new HashMap<String, ExpressionType>();
                var inputContextVars = rs.below().input().contextVars();
                var numberedOutputParams = new HashMap<>(rs.below().output().numberedParams());
                var boundOutputParams = new HashMap<>(rs.below().output().boundParams());
                var ignoredOutputParams = new HashMap<String, ExpressionType>();
                var outputContextVars = rs.below().output().contextVars();
                for (int i = 0; i < params.size(); i++) {
                    if (numberedInputParams.containsKey(i+1)) {
                        if (!params.get(i).startsWith("_")) {
                            boundVariables.put(params.get(i), numberedInputParams.get(i+1));
                            boundInputParams.put(params.get(i), numberedInputParams.get(i+1));
                        } else {
                            ignoredInputParams.put(params.get(i), numberedInputParams.get(i+1));
                        }
                        numberedInputParams.remove(i+1);
                    } else if (numberedOutputParams.containsKey(i+1)) {
                        if (!params.get(i).startsWith("_")) {
                            boundVariables.put(params.get(i), numberedOutputParams.get(i+1));
                            boundOutputParams.put(params.get(i), numberedOutputParams.get(i+1));
                        } else {
                            ignoredOutputParams.put(params.get(i), numberedOutputParams.get(i+1));
                        }
                        numberedOutputParams.remove(i+1);
                    } else {
                        throw new TypeCheckException("rule declaration " + rs.toGentzen() + " has no parameter $" + (i+1), start, stop);
                    }
                }
                if (numberedInputParams.size() > 0 || numberedOutputParams.size() > 0) {
                    throw new TypeCheckException("rule definition is missing " + (numberedInputParams.size() + numberedOutputParams.size()) + " parameters from " + rs.toGentzen(), start, stop);
                }
                var subScope = new Scope(scope, definedRules, boundVariables);
                var subExpectedType = new RuleSignature(
                    List.of(),
                    new RuleSignature.Sequent(
                        new RuleSignature.SequentParameters(numberedInputParams, boundInputParams, inputContextVars),
                        new RuleSignature.SequentParameters(numberedOutputParams, boundOutputParams, outputContextVars)
                    )
                );
                var bodyType = body.typeCheck(subScope, subExpectedType);
                if (bodyType.type instanceof RuleSignature bodyRs) {
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
                        } else if (ignoredInputParams.containsKey(params.get(i))) {
                            newNumberedInputParams.put(i+1, ignoredInputParams.get(params.get(i)));
                        } else if (ignoredOutputParams.containsKey(params.get(i))) {
                            newNumberedOutputParams.put(i+1, ignoredOutputParams.get(params.get(i)));
                        }
                    }
                    var newType = new RuleSignature(
                        rs.above(),
                        new RuleSignature.Sequent(
                            new RuleSignature.SequentParameters(newNumberedInputParams, newBoundInputParams, newInputContextVars),
                            new RuleSignature.SequentParameters(newNumberedOutputParams, newBoundOutputParams, newOutputContextVars)
                        )
                    );
                    System.err.println(this.toGentzen());
                    System.err.println(" got: " + newType.toGentzen());
                    System.err.println(" expected: " + expectedType.toGentzen());
                    try {
                        var unification = newType.unify((RuleSignature) expectedType);
                        this.type = Optional.of(newType);
                        return new TypeCheckResult(newType, unification.mergeWith(bodyType.unification()));
                    } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                        throw new TypeCheckException("type error", e, start, stop);
                    }
                } else {
                    throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got " + bodyType.type.toGentzen(), start, stop);
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

        public Application(String rule, List<Expression> args, Token start, Token stop) {
            this.rule = rule;
            this.args = args;
            this.start = start;
            this.stop = stop;
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
            String code = Stream.concat(Stream.of("g/"+rule), args.stream().map(Expression::toScheme)).collect(joining(" "));
            return "(" + code + ")";
        }

        @Override
        public TypeCheckResult typeCheck(Scope scope, Type expectedType) throws TypeCheckException {
            if (!(expectedType instanceof RuleSignature)) {
                throw new TypeCheckException("expected " + expectedType.toGentzen() + ", got rule " + rule, start, stop);
            }
            Optional<RuleSignature> resolved;
            if (rule.equals("neces")) {
                // neces is defined for any number of parameters, so we construct its type here dynamically
                resolved = Optional.of(new RuleSignature(
                    List.of(new RuleSignature.Sequent(
                        new RuleSignature.SequentParameters(
                            IntStream.range(1, args.size()-1).mapToObj(Integer::valueOf).collect(toMap(i -> i, i -> new ExpressionType.TypeVar("neces_in_"+i, 0))),
                            Map.of(),
                            List.of()
                        ),
                        new RuleSignature.SequentParameters(
                            Map.of(args.size(), new ExpressionType.TypeVar("neces_out", 0)),
                            Map.of(),
                            List.of()
                        )
                    )),
                    new RuleSignature.Sequent(
                        new RuleSignature.SequentParameters(
                            IntStream.range(1, args.size()-1).mapToObj(Integer::valueOf).collect(toMap(i -> i, i -> new ExpressionType.Box(new ExpressionType.TypeVar("neces_in_"+i, 0)))),
                            Map.of(),
                            List.of()
                        ),
                        new RuleSignature.SequentParameters(
                            Map.of(args.size(), new ExpressionType.Box(new ExpressionType.TypeVar("neces_out", 0))),
                            Map.of(),
                            List.of()
                        )
                    )
                ).withInstance(Scope.nextCounter()));
            } else {
                resolved = scope.resolveRule(rule);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol " + rule + " as a rule", start, stop);
                }
            }
            RuleSignature resolvedRule = resolved.get();
            var numberedInputParams = resolvedRule.below().input().numberedParams();
            var numberedOutputParams = resolvedRule.below().output().numberedParams();
            int numParams = numberedInputParams.size() + numberedOutputParams.size();
            int numAbove = resolvedRule.above().size();
            if (args.size() != numParams + numAbove) {
                throw new TypeCheckException("wrong number of arguments for " + rule + ", expected " + numParams + " parameters and " + numAbove + " above sequents, got total of " + args.size(), start, stop);
            }
            var newBoundInputParams = new HashMap<>(resolvedRule.below().input().boundParams());
            var newBoundOutputParams = new HashMap<>(resolvedRule.below().output().boundParams());
            var unification = new RuleSignature.SequentUnificationResult(Map.of(), Map.of());
            for (int i = 0; i < numParams; i++) {
                if (numberedInputParams.containsKey(i+1) && numberedOutputParams.containsKey(i+1)) {
                    throw new TypeCheckException("parameter $" + (i+1) + " appears on both the left and the right side of the sequent " + resolvedRule.below().toGentzen(), start, stop);
                } else if (!numberedInputParams.containsKey(i+1) && !numberedOutputParams.containsKey(i+1)) {
                    throw new TypeCheckException("parameter $" + (i+1) + " is missing from the sequent " + resolvedRule.below().toGentzen(), start, stop);
                } else if (numberedInputParams.containsKey(i+1)) {
                    if (args.get(i) instanceof Symbol s) {
                        TypeCheckResult tcr = args.get(i).typeCheck(scope, numberedInputParams.get(i+1));
                        newBoundInputParams.put(s.symbol(), (ExpressionType) tcr.type);
                        try {
                            unification = unification.mergeWith(tcr.unification());
                        } catch (UnificationException e) {
                            throw new TypeCheckException("type error", e, start, stop);
                        }
                    } else {
                        throw new TypeCheckException("parameter $" + (i+1) + ": expected a variable, got " + args.get(i).toGentzen(), start, stop);
                    }
                } else if (numberedOutputParams.containsKey(i+1)) {
                    if (args.get(i) instanceof Symbol s) {
                        TypeCheckResult tcr = args.get(i).typeCheck(scope, numberedOutputParams.get(i+1));
                        newBoundOutputParams.put(s.symbol(), (ExpressionType) tcr.type);
                        try {
                            unification = unification.mergeWith(tcr.unification());
                        } catch (UnificationException e) {
                            throw new TypeCheckException("type error", e, start, stop);
                        }
                    } else {
                        throw new TypeCheckException("parameter $" + (i+1) + ": expected a variable, got " + args.get(i).toGentzen(), start, stop);
                    }
                } else {
                    throw new RuntimeException("dead code");
                }
            }
            for (int i = 0; i < numAbove; i++) {
                var type = new RuleSignature(List.of(), resolvedRule.above().get(i));
                var value = args.get(i + numParams);
                var tcr = value.typeCheck(scope, type);
                try {
                    var rs = (RuleSignature) tcr.type;
                    unification = unification.mergeWith(tcr.unification()).mergeWith(rs.unify(type));
                } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException("type error", e, start, stop);
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
                System.err.println(" got: " + newType.toGentzen());
                System.err.println(" expected: " + expectedType.toGentzen());
                var finalUnification = newType.unify((RuleSignature) expectedType);
                newType = newType.substitute(finalUnification);
                this.type = Optional.of(newType);
                return new TypeCheckResult(newType, finalUnification);
            } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                throw new TypeCheckException("type error", e, start, stop);
            }
        }
    }
    public final class Symbol implements Expression {
        private String symbol;
        private Token start;
        private Token stop;
        private Optional<Type> type;

        public Symbol(String symbol, Token start, Token stop) {
            this.symbol = symbol;
            this.start = start;
            this.stop = stop;
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
                    throw new TypeCheckException("cannot resolve symbol " + symbol + " as a rule", start, stop);
                }
                try {
                    var unification = rs.unify(resolved.get());
                    var newType = resolved.get().substitute(unification);
                    this.type = Optional.of(newType);
                    return new TypeCheckResult(newType, unification);
                } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
                    throw new TypeCheckException("type error", e, start, stop);
                }
            } else {
                var et = (ExpressionType) expectedType;
                Optional<ExpressionType> resolved = scope.resolveVariable(symbol);
                if (resolved.isEmpty()) {
                    throw new TypeCheckException("cannot resolve symbol " + symbol + " as a variable", start, stop);
                }
                try {
                    System.err.println(this.toGentzen());
                    System.err.println(" got: " + resolved.get().toGentzen());
                    System.err.println(" expected: " + expectedType.toGentzen());
                    var unification = et.unify(resolved.get());
                    var newType = resolved.get().substitute(unification);
                    this.type = Optional.of(newType);
                    return new TypeCheckResult(newType, new RuleSignature.SequentUnificationResult(unification, Map.of()));
                } catch (UnificationException e) {
                    throw new TypeCheckException("type error", e, start, stop);
                }
            }
        }
    }

    public sealed abstract class Literal implements Expression permits StringLiteral, NaturalLiteral {
        private RuleSignature type;
        private Token start;
        private Token stop;

        protected Literal(RuleSignature type, Token start, Token stop) {
            this.type = type;
            this.start = start;
            this.stop = stop;
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
                    var unification = rs.unify(type);
                    return new TypeCheckResult(type, unification);
                } catch (UnificationException | SequentUnificationException | RuleSignatureUnificationException e) {
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
                new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of()),
                new RuleSignature.SequentParameters(
                    Map.of(1, new ExpressionType.List(new ExpressionType.Nat())),
                    Map.of(),
                    List.of()
                )
            )
        );

        private String literal;

        public StringLiteral(String literal, Token start, Token stop) {
            super(LITERAL_TYPE, start, stop);
            this.literal = literal;
        }

        @Override
        public String toGentzen() {
            return literal;
        }

        @Override
        public String toScheme() {
            return "(lambda (output) (output (string->list " + literal + ")))";
        }
    }

    public final class NaturalLiteral extends Literal {
        /** () / (|- $1(|\|)) */
        public static final RuleSignature LITERAL_TYPE = new RuleSignature(
            List.of(),
            new RuleSignature.Sequent(
                new RuleSignature.SequentParameters(Map.of(), Map.of(), List.of()),
                new RuleSignature.SequentParameters(
                    Map.of(1, new ExpressionType.Nat()),
                    Map.of(),
                    List.of()
                )
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
