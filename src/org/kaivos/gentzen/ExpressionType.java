package org.kaivos.gentzen;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

public sealed interface ExpressionType extends Type, HasTypeVars<ExpressionType> {
    public String toGentzen();
    default String toParensGentzen() {
        return toGentzen();
    }
    public Set<TypeVar> getTypeVars();
    public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions);
    public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException;
    default Optional<Map<TypeVar, ExpressionType>> tryUnify(ExpressionType other) {
        try {
            return Optional.of(unify(other));
        } catch (UnificationException ex) {
            return Optional.empty();
        }
    }
    default ExpressionType withInstance(int instance) {
        Map<TypeVar, ExpressionType> typeVars = getTypeVars()
            .stream()
            .map(v -> Map.entry(v, v.withInstance(instance)))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        return substitute(typeVars);
    }

    public static class UnificationException extends Exception {
        private ExpressionType type1;
        private ExpressionType type2;

        public UnificationException(ExpressionType type1, ExpressionType type2) {
            super("cannot unify " + type1.toGentzen() + " with " + type2.toGentzen());
            this.type1 = type1;
            this.type2 = type2;
        }

        public UnificationException(String message, ExpressionType type1, ExpressionType type2) {
            super(message);
            this.type1 = type1;
            this.type2 = type2;
        }

        public ExpressionType getType1() {
            return type1;
        }

        public ExpressionType getType2() {
            return type2;
        }
    }

    static Map<TypeVar, ExpressionType> mergeBindings(Map<TypeVar, ExpressionType> bindings, Map<TypeVar, ExpressionType> otherBindings) throws UnificationException {
        HashMap<TypeVar, ExpressionType> result = new HashMap<>(bindings);
        ArrayDeque<Map.Entry<TypeVar, ExpressionType>> stack = new ArrayDeque<>();
        stack.addAll(otherBindings.entrySet());
        while (!stack.isEmpty()) {
            var entry = stack.removeLast();
            TypeVar newTv = entry.getKey();
            ExpressionType newType = entry.getValue().substitute(result);
            if (newType.equals(newTv)) {
                continue;
            } else if (newType.getTypeVars().contains(newTv)) {
                throw new UnificationException(newTv, newType);
            }
            if (result.containsKey(newTv)) {
                stack.addAll(newType.unify(result.get(newTv)).entrySet());
            }
            var tmp = Map.of(newTv, newType);
            result.replaceAll((_, v) -> v.substitute(tmp));
            result.put(newTv, newType);
        }
        return result;
    }

    public record TypeVar(String name, int instance) implements ExpressionType {
        public TypeVar withInstance(int newInstance) {
            if (instance == 0) {
                return new TypeVar(name, newInstance);
            } else {
                return this;
            }
        }
        @Override
        public String toGentzen() {
            return "?" + name + (instance != 0 ? "."+instance : "");
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Set.of(this);
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            if (substitutions.containsKey(this)) {
                return substitutions.get(this);
            } else {
                return this;
            }
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other.equals(this)) {
                return Map.of();
            }
            if (other.getTypeVars().contains(this)) {
                throw new UnificationException(this, other);
            }
            return Map.of(this, other);
        }
    }

    public record Not(ExpressionType type) implements ExpressionType {
        @Override
        public String toGentzen() {
            return switch (type) {
                case Box b -> switch (b.type()) {
                    case Not n -> "<> " + n.toParensGentzen();
                    default -> "~ " + type.toParensGentzen();
                };
                default -> "~ " + type.toParensGentzen();
            };
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return type.getTypeVars();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new Not(type.substitute(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Not t) {
                return type.unify(t.type);
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record And(ExpressionType left, ExpressionType right) implements ExpressionType {
        @Override
        public String toGentzen() {
            return left.toParensGentzen() + " /\\ " + right.toParensGentzen();
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Stream.concat(left.getTypeVars().stream(), right.getTypeVars().stream()).collect(toSet());
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new And(left.substitute(substitutions), right.substitute(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof And t) {
                return mergeBindings(left.unify(t.left), right.unify(t.right));
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Or(ExpressionType left, ExpressionType right) implements ExpressionType {
        @Override
        public String toGentzen() {
            return left.toParensGentzen() + " \\/ " + right.toParensGentzen();
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Stream.concat(left.getTypeVars().stream(), right.getTypeVars().stream()).collect(toSet());
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new Or(left.substitute(substitutions), right.substitute(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Or t) {
                return mergeBindings(left.unify(t.left), right.unify(t.right));
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Maybe(ExpressionType type) implements ExpressionType {
        @Override
        public String toGentzen() {
            return "@ " + type.toParensGentzen();
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return type.getTypeVars();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new Maybe(type.substitute(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Maybe t) {
                return type.unify(t.type);
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Nat() implements ExpressionType {
        @Override
        public String toGentzen() {
            return "|\\|";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Set.of();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Nat) {
                return Map.of();
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Unit() implements ExpressionType {
        @Override
        public String toGentzen() {
            return "++";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Set.of();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Unit) {
                return Map.of();
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Bottom() implements ExpressionType {
        @Override
        public String toGentzen() {
            return "_|_";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Set.of();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Bottom) {
                return Map.of();
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record List(ExpressionType type) implements ExpressionType {
        @Override
        public String toGentzen() {
            return ".. " + type.toParensGentzen();
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return type.getTypeVars();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new List(type.substitute(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof List t) {
                return type.unify(t.type);
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Box(ExpressionType type) implements ExpressionType {
        @Override
        public String toGentzen() {
            return "[] " + type.toParensGentzen();
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return type.getTypeVars();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new Box(type.substitute(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Box t) {
                return type.unify(t.type);
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record Atomic(String name) implements ExpressionType {
        @Override
        public String toGentzen() {
            return "!" + name;
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Set.of();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof Atomic t && name.equals(t.name())) {
                return Map.of();
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
    public record CustomType(String name, java.util.List<ExpressionType> args) implements ExpressionType {
        @Override
        public String toGentzen() {
            return Stream.concat(Stream.of(name), args.stream().map(ExpressionType::toParensGentzen)).collect(joining(" "));
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return args.stream().flatMap(t -> t.getTypeVars().stream()).collect(toSet());
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions) {
            return new CustomType(name, args.stream().map(t -> t.substitute(substitutions)).collect(toList()));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other) throws UnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this);
            } else if (other instanceof CustomType t && name.equals(t.name()) && args.size() == t.args().size()) {
                if (args.size() == 0) {
                    return Map.of();
                } else {
                    var bindings = args.get(0).unify(t.args().get(0));
                    for (var i = 1; i < args.size(); i++) {
                        bindings = mergeBindings(bindings, args.get(i).unify(t.args().get(i)));
                    }
                    return bindings;
                }
            } else {
                throw new UnificationException(this, other);
            }
        }
    }
}
