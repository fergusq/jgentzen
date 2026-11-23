package org.kaivos.gentzen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import org.kaivos.gentzen.ExpressionType.TypeVar;
import org.kaivos.gentzen.ExpressionType.UnificationException;

public record RuleSignature(java.util.List<Sequent> above, Sequent below) implements Type {
    @Override
    public String toGentzen() {
        return "(" + above.stream().map(Sequent::toGentzen).collect(joining("; ")) + ") / (" + below.toGentzen() + ")";
    }
    public RuleSignature withInstance(int instance) {
        return new RuleSignature(
            above.stream().map(s -> s.withInstance(instance)).toList(),
            below.withInstance(instance)
        );
    }
    public RuleSignature substitute(SequentUnificationResult substitutions) {
        return new RuleSignature(
            above.stream().map(s -> s.substitute(substitutions)).toList(),
            below.substitute(substitutions)
        );
    }
    public SequentUnificationResult unify(RuleSignature other) throws RuleSignatureUnificationException, SequentUnificationException, UnificationException {
        if (above.size() != other.above().size()) {
            throw new RuleSignatureUnificationException("rules have different number of above sequents", this, other);
        }
        var result = below.unify(other.below());
        for (int i = 0; i < above.size(); i++) {
            result = result.mergeWith(above.get(i).unify(other.above().get(i)));
        }
        return result;
    }
    public static record ContextVar(String name, int instance) implements HasTypeVars<ContextVar> {
        public String toGentzen() {
            return "*" + name + (instance != 0 ? "." + instance : "");
        }

        public ContextVar withInstance(int newInstance) {
            if (instance == 0) {
                return new ContextVar(name, newInstance);
            } else {
                return this;
            }
        }

        @Override
        public ContextVar substitute(Map<TypeVar, ExpressionType> substitutions) {
            return this;
        }
    }

    public static record SequentParameters(Map<Integer, ExpressionType> numberedParams, Map<String, ExpressionType> boundParams, java.util.List<ContextVar> contextVars) {
        public String toGentzen() {
            ArrayList<String> strings = new ArrayList<>();
            numberedParams.forEach((k, v) -> strings.add("$" + k + "(" + v.toGentzen() + ")"));
            boundParams.forEach((k, v) -> strings.add(k + ":" + v.toGentzen()));
            contextVars.forEach(v -> strings.add(v.toGentzen()));
            return strings.stream().collect(joining(", "));
        }
        public SequentParameters withInstance(int instance) {
            return new SequentParameters(
                HasTypeVars.mapWithInstance(numberedParams, instance),
                HasTypeVars.mapWithInstance(boundParams, instance),
                HasTypeVars.listWithInstance(contextVars, instance)
            );
        }
        public SequentParameters substitute(SequentUnificationResult substitutions) {
            HashMap<String, ExpressionType> newBoundParams = new HashMap<>(boundParams);
            ArrayList<ContextVar> newContextVars = new ArrayList<>();
            for (ContextVar cvar : contextVars) {
                if (substitutions.contextUnification().containsKey(cvar)) {
                    for (Map.Entry<String, ExpressionType> entry : substitutions.contextUnification().get(cvar).entrySet()) {
                        String variable = entry.getKey();
                        ExpressionType newValue = entry.getValue();
                        if (newBoundParams.containsKey(variable)) {
                            throw new RuntimeException("illegal substitution of existing parameter " + variable);
                        }
                        newBoundParams.put(variable, newValue);
                    }
                } else {
                    newContextVars.add(cvar);
                }
            }
            return new SequentParameters(
                HasTypeVars.mapSubstitute(numberedParams, substitutions.typeUnification()),
                newBoundParams,
                newContextVars
            );
        }
        public SequentUnificationResult unify(SequentParameters other, Sequent thisSequent, Sequent otherSequent) throws SequentUnificationException, UnificationException {
            if (!numberedParams.keySet().containsAll(other.numberedParams().keySet())
                || !other.numberedParams().keySet().containsAll(numberedParams.keySet())) {
                throw new SequentUnificationException("numbered parameters mismatch", thisSequent, otherSequent);
            }
            if (contextVars.size() > 1 || other.contextVars().size() > 1) {
                throw new SequentUnificationException("cannot unify multiple contexts", thisSequent, otherSequent);
            }
            if (!boundParams.keySet().containsAll(other.boundParams().keySet()) && contextVars.size() == 0
                || !other.boundParams().keySet().containsAll(boundParams.keySet()) && other.contextVars().size() == 0) {
                throw new SequentUnificationException("non-numbered parameters mismatch", thisSequent, otherSequent);
            }
            Map<TypeVar, ExpressionType> result = Map.of();
            for (int i : numberedParams().keySet()) {
                result = ExpressionType.mergeBindings(result, numberedParams.get(i).unify(other.numberedParams().get(i)));
            }
            Set<String> intersection = boundParams.keySet().stream().filter(other.boundParams()::containsKey).collect(toSet());
            Map<String, ExpressionType> leftDifference = new HashMap<>(boundParams);
            leftDifference.keySet().removeAll(other.boundParams().keySet());
            Map<String, ExpressionType> rightDifference = new HashMap<>(other.boundParams());
            rightDifference.keySet().removeAll(boundParams.keySet());
            for (String key : intersection) {
                result = ExpressionType.mergeBindings(result, boundParams.get(key).unify(other.boundParams().get(key)));
            }
            return new SequentUnificationResult(
                result,
                other.contextVars().size() > 0 && contextVars.size() > 0 ? Map.of(contextVars.get(0), rightDifference, other.contextVars().get(0), leftDifference) :
                other.contextVars().size() > 0 ? Map.of(other.contextVars().get(0), leftDifference) :
                contextVars.size() > 0 ? Map.of(contextVars.get(0), rightDifference) :
                Map.of()
                /*
                leftDifference.size() > 0 && rightDifference.size() > 0 ? Map.of(contextVars.get(0), rightDifference, other.contextVars().get(0), leftDifference) :
                leftDifference.size() > 0 ? Map.of(other.contextVars().get(0), leftDifference) :
                rightDifference.size() > 0 ? Map.of(contextVars.get(0), rightDifference) :
                Map.of()*/
            );
        }
    }

    public static record SequentUnificationResult(
        Map<TypeVar, ExpressionType> typeUnification,
        Map<ContextVar, Map<String, ExpressionType>> contextUnification
    ) {
        SequentUnificationResult mergeWith(SequentUnificationResult other) throws UnificationException {
            var typeUnification = ExpressionType.mergeBindings(this.typeUnification(), other.typeUnification());
            var contextUnification = new HashMap<>(this.contextUnification());
            for (var entry : other.contextUnification().entrySet()) {
                var cvar = entry.getKey();
                var unification = entry.getValue();
                contextUnification.compute(cvar, (k, v) -> v == null ? unification : unifyContextVar(v, unification));
            }
            return new SequentUnificationResult(typeUnification, contextUnification);
        }
        private static Map<String, ExpressionType> unifyContextVar(Map<String, ExpressionType> v1, Map<String, ExpressionType> v2) {
            var result = new HashMap<>(v1);
            for (var entry : v2.entrySet()) {
                result.compute(entry.getKey(), (k, v) -> v == null ? entry.getValue() : entry.getValue());  // TODO: no unification
            }
            return result;
        }
    }

    public static record Sequent(SequentParameters input,SequentParameters output) {
        public String toGentzen() {
            return (input.toGentzen() + " |- " + output.toGentzen()).trim();
        }
        public Sequent withInstance(int instance) {
            return new Sequent(input.withInstance(instance), output.withInstance(instance));
        }

        public Sequent substitute(SequentUnificationResult substitutions) {
            return new Sequent(input.substitute(substitutions), output.substitute(substitutions));
        }

        public SequentUnificationResult unify(Sequent other) throws SequentUnificationException, UnificationException {
            // Implicit negation
            var thisInputNumberedParams = new HashMap<>(input.numberedParams());
            var thisOutputNumberedParams = new HashMap<>(output.numberedParams());
            var otherInputNumberedParams = new HashMap<>(other.input().numberedParams());
            var otherOutputNumberedParams = new HashMap<>(other.output().numberedParams());

            // (*a ⊢ A, *b) / (*a, ¬A ⊢ *b)  (¬L)
            // (*a, A ⊢ *b) / (*a ⊢ ¬A, *b)  (¬R)
            // This is intuitive, because output params are implemented as continuations:
            // ¬L: Input param that is a continuation is just the same as an output param
            // ¬R: A becomes Count<Cont<A>> and the whole expression is wrapped in a lambda

            // ¬L this / other
            for (var i : thisOutputNumberedParams.keySet()) {
                if (!otherOutputNumberedParams.containsKey(i)
                    && otherInputNumberedParams.containsKey(i)
                    && otherInputNumberedParams.get(i) instanceof ExpressionType.Not) {
                    var type = thisOutputNumberedParams.remove(i);
                    thisInputNumberedParams.put(i, new ExpressionType.Not(type));
                }
            }

            // ¬L other / this
            for (var i : otherOutputNumberedParams.keySet()) {
                if (!thisOutputNumberedParams.containsKey(i)
                    && thisInputNumberedParams.containsKey(i)
                    && thisInputNumberedParams.get(i) instanceof ExpressionType.Not) {
                    var type = otherOutputNumberedParams.remove(i);
                    otherInputNumberedParams.put(i, new ExpressionType.Not(type));
                }
            }

            // ¬R this / other
            for (var i : thisInputNumberedParams.keySet()) {
                if (!otherInputNumberedParams.containsKey(i)
                    && otherOutputNumberedParams.containsKey(i)
                    && otherOutputNumberedParams.get(i) instanceof ExpressionType.Not) {
                    var type = thisInputNumberedParams.remove(i);
                    thisOutputNumberedParams.put(i, new ExpressionType.Not(type));
                }
            }

            // ¬R other / this
            for (var i : otherInputNumberedParams.keySet()) {
                if (!thisInputNumberedParams.containsKey(i)
                    && thisOutputNumberedParams.containsKey(i)
                    && thisOutputNumberedParams.get(i) instanceof ExpressionType.Not) {
                    var type = otherInputNumberedParams.remove(i);
                    otherOutputNumberedParams.put(i, new ExpressionType.Not(type));
                }
            }

            var thisInput = new SequentParameters(thisInputNumberedParams, input.boundParams(), input.contextVars());
            var thisOutput = new SequentParameters(thisOutputNumberedParams, output.boundParams(), output.contextVars());
            var otherInput = new SequentParameters(otherInputNumberedParams, other.input().boundParams(), other.input().contextVars());
            var otherOutput = new SequentParameters(otherOutputNumberedParams, other.output().boundParams(), other.output().contextVars());

            // Normal unification
            var inputUnification = thisInput.unify(otherInput, this, other);
            var outputUnification = thisOutput.unify(otherOutput, this, other);
            return inputUnification.mergeWith(outputUnification);
        }
    }

    public static class SequentUnificationException extends Exception {
        private Sequent sequent1;
        private Sequent sequent2;
        public SequentUnificationException(Sequent sequent1, Sequent sequent2) {
            super("cannot unify " + sequent1.toGentzen() + " and " + sequent2.toGentzen());
            this.sequent1 = sequent1;
            this.sequent2 = sequent2;
        }
        public SequentUnificationException(String message, Sequent sequent1, Sequent sequent2) {
            super("cannot unify " + sequent1.toGentzen() + " and " + sequent2.toGentzen() + ": " + message);
            this.sequent1 = sequent1;
            this.sequent2 = sequent2;
        }
    }

    public static class RuleSignatureUnificationException extends Exception {
        private RuleSignature rule1;
        private RuleSignature rule2;
        public RuleSignatureUnificationException(RuleSignature rule1, RuleSignature rule2) {
            super("cannot unify " + rule1.toGentzen() + " and " + rule2.toGentzen());
            this.rule1 = rule1;
            this.rule2 = rule2;
        }
        public RuleSignatureUnificationException(String message, RuleSignature rule1, RuleSignature rule2) {
            super("cannot unify " + rule1.toGentzen() + " and " + rule2.toGentzen() + ": " + message);
            this.rule1 = rule1;
            this.rule2 = rule2;
        }
    }
}
