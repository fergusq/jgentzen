package org.kaivos.gentzen;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.kaivos.gentzen.Utils.fromEntries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kaivos.gentzen.ExpressionType.TypeVar;
import org.kaivos.gentzen.exceptions.ExpressionTypeUnificationException;
import org.kaivos.gentzen.exceptions.KindCheckException;
import org.kaivos.gentzen.exceptions.RuleSignatureUnificationException;
import org.kaivos.gentzen.exceptions.SequentUnificationException;

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
    public RuleSignature substitute(SequentUnificationResult substitutions, Scope scope) throws ExpressionTypeUnificationException {
        return substitute(substitutions, scope, false);
    }
    public RuleSignature substitute(SequentUnificationResult substitutions, Scope scope, boolean noContextVars) throws ExpressionTypeUnificationException {
        ArrayList<Sequent> newAbove = new ArrayList<>();
        for (var a : above) {
            newAbove.add(a.substitute(substitutions, scope, noContextVars));
        }
        return new RuleSignature(
            newAbove,
            below.substitute(substitutions, scope, noContextVars)
        );
    }
    public SequentUnificationResult unify(RuleSignature other, Scope scope) throws RuleSignatureUnificationException, SequentUnificationException, ExpressionTypeUnificationException {
        if (above.size() != other.above().size()) {
            throw new RuleSignatureUnificationException("rules have different number of above sequents", this, other);
        }
        var result = below.unify(other.below(), scope);
        for (int i = 0; i < above.size(); i++) {
            result = result.mergeWith(above.get(i).unify(other.above().get(i), scope), scope);
        }
        return result;
    }

    public void kindCheck(Scope scope) throws KindCheckException {
        for (var sequent : above) {
            sequent.kindCheck(scope);
        }
        below.kindCheck(scope);
    }

    public static record ContextVarParameter(ContextVar var, Boolean input) implements HasTypeVars<ContextVarParameter> {
		@Override
		public ContextVarParameter withInstance(int instance) {
			return new ContextVarParameter(var.withInstance(instance), input);
		}

		@Override
		public ContextVarParameter substitute(Map<TypeVar, ExpressionType> substitutions) {
			return new ContextVarParameter(var.substitute(substitutions), input);
		}
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

    public static record SequentParameter(ExpressionType type, Boolean input) implements HasTypeVars<SequentParameter> {
		@Override
		public SequentParameter withInstance(int instance) {
			return new SequentParameter(type.withInstance(instance), input);
		}

		@Override
		public SequentParameter substitute(Map<TypeVar, ExpressionType> substitutions) {
			return new SequentParameter(type.substitute(substitutions), input);
		}

		public void kindCheck(Scope scope) throws KindCheckException {
		    type.kindCheck(scope);
		}
    }
    public static record Sequent(
        Map<Integer, SequentParameter> numberedParams,
        Map<String, SequentParameter> boundParams,
        java.util.List<ContextVarParameter> contextVars
    ) {
        public String toGentzen() {
            return List.of(true, false).stream().map(input -> {
                ArrayList<String> strings = new ArrayList<>();
                numberedParams.forEach((k, v) -> { if (v.input() == input) strings.add("$" + k + "(" + v.type().toGentzen() + ")"); });
                boundParams.forEach((k, v) -> { if (v.input() == input) strings.add(k + ":" + v.type().toGentzen()); });
                contextVars.forEach(v -> { if (v.input() == input) strings.add(v.var().toGentzen()); });
                return strings.stream().collect(joining(", "));
            }).collect(joining(" |- ")).strip();
        }
        public Sequent withInstance(int instance) {
            return new Sequent(
                HasTypeVars.mapWithInstance(numberedParams, instance),
                HasTypeVars.mapWithInstance(boundParams, instance),
                HasTypeVars.listWithInstance(contextVars, instance)
            );
        }
        public Sequent substitute(SequentUnificationResult substitutions, Scope scope, boolean noContextVars) throws ExpressionTypeUnificationException {
            HashMap<String, SequentParameter> newBoundParams = new HashMap<>(boundParams);
            List<ContextVarParameter> newContextVars = new ArrayList<>();
            if (noContextVars) {
                newContextVars = contextVars;
            } else {
                for (ContextVarParameter cvarp : contextVars) {
                    if (substitutions.contextUnification().containsKey(cvarp)) {
                        for (Map.Entry<String, SequentParameter> entry : substitutions.contextUnification().get(cvarp).entrySet()) {
                            String variable = entry.getKey();
                            SequentParameter newValue = entry.getValue();
                            if (newBoundParams.containsKey(variable)) {
                                newValue = newValue.substitute(newValue.type().unify(newBoundParams.get(variable).type(), scope));
                                /*throw new RuntimeException(
                                    "illegal substitution of existing parameter " + variable
                                    + ": tried to subtitute " + variable + " -> " + newValue
                                    + ", but " + boundParams + " already has " + variable
                                    + "; substitutions: " + substitutions);*/
                            }
                            newBoundParams.put(variable, newValue);
                        }
                    } else {
                        newContextVars.add(cvarp);
                    }
                }
            }
            return new Sequent(
                HasTypeVars.mapSubstitute(numberedParams, substitutions.typeUnification()),
                newBoundParams,
                newContextVars
            );
        }
        public SequentUnificationResult unify(Sequent other, Scope scope) throws SequentUnificationException, ExpressionTypeUnificationException {
            var ans = new SequentUnificationResult(Map.of(), Map.of());
            for (var input : List.of(true, false)) {
                var thisNumberedParams = numberedParams
                    .entrySet().stream()
                    .filter(e -> e.getValue().input() == input)
                    .collect(fromEntries());
                var otherNumberedParams = other.numberedParams()
                    .entrySet().stream()
                    .filter(e -> e.getValue().input() == input)
                    .collect(fromEntries());
                var thisBoundParams = boundParams
                    .entrySet().stream()
                    .filter(e -> e.getValue().input() == input)
                    .collect(fromEntries());
                var otherBoundParams = other.boundParams()
                    .entrySet().stream()
                    .filter(e -> e.getValue().input() == input)
                    .collect(fromEntries());
                var thisContextVars = contextVars.stream().filter(c -> c.input() == input).collect(toList());
                var otherContextVars = other.contextVars().stream().filter(c -> c.input() == input).collect(toList());

                if (!thisNumberedParams.keySet().containsAll(otherNumberedParams.keySet())
                    || !otherNumberedParams.keySet().containsAll(thisNumberedParams.keySet())) {
                    throw new SequentUnificationException("numbered parameters mismatch", this, other);
                }
                if (thisContextVars.size() > 1| otherContextVars.size() > 1) {
                    throw new SequentUnificationException("cannot unify multiple contexts", this, other);
                }
                if (!thisBoundParams.keySet().containsAll(otherBoundParams.keySet()) && thisContextVars.size() == 0
                    || !otherBoundParams.keySet().containsAll(thisBoundParams.keySet()) && otherContextVars.size() == 0) {
                    throw new SequentUnificationException("non-numbered parameters mismatch", this, other);
                }
                Map<TypeVar, ExpressionType> result = Map.of();
                for (int i : thisNumberedParams.keySet()) {
                    result = ExpressionType.mergeUnification(result, thisNumberedParams.get(i).type().unify(otherNumberedParams.get(i).type(), scope), scope);
                }
                Set<String> intersection = thisBoundParams.keySet().stream().filter(otherBoundParams::containsKey).collect(toSet());
                Map<String, SequentParameter> leftDifference = new HashMap<>(thisBoundParams);
                otherBoundParams.keySet().forEach(leftDifference::remove);
                Map<String, SequentParameter> rightDifference = new HashMap<>(otherBoundParams);
                thisBoundParams.keySet().forEach(rightDifference::remove);
                Map<String, SequentParameter> xor = new HashMap<>();
                /*System.err.println("this: " + this.toGentzen());
                System.err.println("other: " + other.toGentzen());
                System.err.println("thisContextVars: " + thisContextVars);
                System.err.println("otherContextVars: " + otherContextVars);
                System.err.println("thisBoundParams: " + thisBoundParams);
                System.err.println("otherBoundParams: " + otherBoundParams);
                System.err.println("intersection: " + intersection);
                System.err.println("leftDifference: " + leftDifference);
                System.err.println("rightDifference: " + rightDifference);*/
                if (thisContextVars.size() > 0 && thisContextVars.equals(otherContextVars)) {
                    xor.putAll(leftDifference);
                    for (var entry : rightDifference.entrySet()) {
                        if (xor.containsKey(entry.getKey())) {
                            throw new RuntimeException("context var unification not implemented");
                        }
                        xor.put(entry.getKey(), entry.getValue());
                    }
                    for (var key : xor.keySet()) {
                        if (thisBoundParams.containsKey(key) || otherBoundParams.containsKey(key)) {
                            throw new SequentUnificationException("cannot unify context variables", this, other);
                        }
                    }
                }
                //System.err.println("xor: " + xor);
                for (String key : intersection) {
                    result = ExpressionType.mergeUnification(result, thisBoundParams.get(key).type().unify(otherBoundParams.get(key).type(), scope), scope);
                }
                ans = ans.mergeWith(new SequentUnificationResult(
                    result,
                    otherContextVars.size() > 0 && thisContextVars.size() > 0 ? (
                        thisContextVars.get(0).equals(otherContextVars.get(0))
                        ? Map.of(thisContextVars.get(0), xor)
                        : Map.of(thisContextVars.get(0), rightDifference, otherContextVars.get(0), leftDifference)
                    ) :
                    otherContextVars.size() > 0 ? Map.of(otherContextVars.get(0), leftDifference) :
                    thisContextVars.size() > 0 ? Map.of(thisContextVars.get(0), rightDifference) :
                    Map.of()
                    /*
                    leftDifference.size() > 0 && rightDifference.size() > 0 ? Map.of(contextVars.get(0), rightDifference, other.contextVars().get(0), leftDifference) :
                    leftDifference.size() > 0 ? Map.of(other.contextVars().get(0), leftDifference) :
                    rightDifference.size() > 0 ? Map.of(contextVars.get(0), rightDifference) :
                    Map.of()*/
                ), scope);
            }
            return ans;
        }
        public void kindCheck(Scope scope) throws KindCheckException {
            for (var param : numberedParams.values()) {
                param.kindCheck(scope);
            }
            for (var param : boundParams.values()) {
                param.kindCheck(scope);
            }
        }
    }

    public static record SequentUnificationResult(
        Map<TypeVar, ExpressionType> typeUnification,
        Map<ContextVarParameter, Map<String, SequentParameter>> contextUnification
    ) {
        SequentUnificationResult mergeWith(SequentUnificationResult other, Scope scope) throws ExpressionTypeUnificationException {
            var typeUnification = ExpressionType.mergeUnification(this.typeUnification(), other.typeUnification(), scope);
            var contextUnification = new HashMap<>(this.contextUnification());
            for (var entry : other.contextUnification().entrySet()) {
                var cvar = entry.getKey();
                var unification = entry.getValue();
                contextUnification.compute(cvar, (k, v) -> v == null ? unification : unifyContextVar(v, unification));
            }
            return new SequentUnificationResult(typeUnification, contextUnification);
        }
        private static Map<String, SequentParameter> unifyContextVar(Map<String, SequentParameter> v1, Map<String, SequentParameter> v2) {
            var result = new HashMap<>(v1);
            for (var entry : v2.entrySet()) {
                result.compute(entry.getKey(), (k, v) -> v == null ? entry.getValue() : entry.getValue());  // TODO: no unification
            }
            return result;
        }
    }
}
