package org.kaivos.gentzen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

import org.kaivos.gentzen.exceptions.ExpressionTypeUnificationException;
import org.kaivos.gentzen.exceptions.KindCheckException;

import static org.kaivos.gentzen.Utils.fromEntries;

public sealed interface ExpressionType extends Type, HasTypeVars<ExpressionType> {

    String toGentzen();
    default String toParensGentzen() {
        return toGentzen();
    }

    Set<TypeVar> getTypeVars();
    ExpressionType substitute(Map<TypeVar, ExpressionType> substitutions);

    Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException;
    Kind kindCheck(Scope scope) throws KindCheckException;

    default Optional<Map<TypeVar, ExpressionType>> tryUnify(ExpressionType other, Scope scope) {
        try {
            return Optional.of(unify(other, scope));
        } catch (ExpressionTypeUnificationException ex) {
            return Optional.empty();
        }
    }

    default ExpressionType withInstance(int instance) {
        Map<TypeVar, ExpressionType> typeVars = getTypeVars()
            .stream()
            .map(v -> Map.entry(v, v.withInstance(instance)))
            .collect(fromEntries());
        return substitute(typeVars);
    }

    static Map<TypeVar, ExpressionType> mergeUnification(Map<TypeVar, ExpressionType> bindings, Map<TypeVar, ExpressionType> otherBindings, Scope scope) throws ExpressionTypeUnificationException {
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
                throw new ExpressionTypeUnificationException(newTv, newType);
            }
            if (result.containsKey(newTv)) {
                stack.addAll(newType.unify(result.get(newTv), scope).entrySet());
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other.equals(this)) {
                return Map.of();
            }
            if (other.getTypeVars().contains(this)) {
                throw new ExpressionTypeUnificationException(this, other);
            }
            return Map.of(this, other);
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Not t) {
                return type.unify(t.type, scope);
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            type.kindCheck(scope).expectConcrete("type argument for ~");
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof And t) {
                return mergeUnification(left.unify(t.left, scope), right.unify(t.right, scope), scope);
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            left.kindCheck(scope).expectConcrete("left type argument for /\\");
            right.kindCheck(scope).expectConcrete("right type argument for /\\");
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Or t) {
                return mergeUnification(left.unify(t.left, scope), right.unify(t.right, scope), scope);
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            left.kindCheck(scope).expectConcrete("left type argument for \\/");
            right.kindCheck(scope).expectConcrete("right type argument for \\/");
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Maybe t) {
                return type.unify(t.type, scope);
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            type.kindCheck(scope).expectConcrete("type argument for @");
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Nat) {
                return Map.of();
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Unit) {
                return Map.of();
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Bottom) {
                return Map.of();
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof List t) {
                return type.unify(t.type, scope);
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            type.kindCheck(scope).expectConcrete("type argument for ..");
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Box t) {
                return type.unify(t.type, scope);
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            type.kindCheck(scope).expectConcrete("type argument for []");
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof Atomic t && name.equals(t.name())) {
                return Map.of();
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            return Kind.CONCRETE;
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
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            var resolvedType = scope.resolveType(name);
            var args = this.args;
            while (resolvedType.isPresent()) {
                var rt = resolvedType.get();
                var expectedParams = rt.getParams();
                if (rt instanceof TypeAlias alias) {
                    if (alias.body().isPresent()) {
                        if (expectedParams.size() <= args.size()) {
                            // The type alias has enough params, add them to the scope and unify with the new type
                            var newAliases = new HashMap<String, DefinedType>();
                            for (var i = 0; i < expectedParams.size(); i++) {
                                var param = (DefinedType.TypeParam.ParamWithKind) expectedParams.get(i);
                                var arg = args.get(i);
                                newAliases.put(param.name(), new TypeAlias.AliasParam(param.name(), param.kind(), Optional.of(arg)));
                            }
                            scope = new Scope(scope, Map.of(), newAliases, Map.of());
                            if (alias.body().get() instanceof CustomType(var bodyName, var bodyArgs)) {
                                var newBodyArgs = new ArrayList<>(bodyArgs);
                                resolvedType = scope.resolveType(bodyName);
                                for (var i = expectedParams.size(); i < args.size(); i++) {
                                    newBodyArgs.add(args.get(i));
                                }
                                args = newBodyArgs;
                                continue;
                            } else {
                                scope = new Scope(scope, Map.of(), newAliases, Map.of());
                                return alias.body().get().unify(other, scope);
                            }
                        } else {
                            throw new ExpressionTypeUnificationException(this, other);
                        }
                    } else {
                        if (expectedParams.size() != args.size()) {
                            throw new ExpressionTypeUnificationException(this, other);
                        } else {
                            break;
                        }
                    }
                }
                break;
            }

            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType t && name.equals(t.name) && args.size() == t.args().size()) {
                if (args.size() == 0) {
                    return Map.of();
                } else {
                    var bindings = args.get(0).unify(t.args().get(0), scope);
                    for (var i = 1; i < args.size(); i++) {
                        bindings = mergeUnification(bindings, args.get(i).unify(t.args().get(i), scope), scope);
                    }
                    return bindings;
                }
            } else {
                throw new ExpressionTypeUnificationException(this, other);
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            var resolvedType = scope.resolveType(name);
            if (resolvedType.isEmpty()) {
                throw new KindCheckException("unknown type " + name);
            }

            var rt = resolvedType.get();
            var expectedParams = rt.getParams();

            for (var i = 0; i < Math.min(args.size(), expectedParams.size()); i++) {
                var arg = args.get(i);
                var argKind = arg.kindCheck(scope);
                var param = expectedParams.get(i);
                switch (param) {
                    case DefinedType.TypeParam.ParamWithKind(var paramName, var paramKind) -> {
                        if (!argKind.equals(paramKind)) {
                            throw new KindCheckException(
                                "type parameter " + paramName + " of " + name
                                + " expected argument of kind " + paramKind.toKindNotation()
                                + ", got " + argKind.toKindNotation()
                            );
                        }
                    }
                    case DefinedType.TypeParam.ValueParam _ -> {
                        throw new KindCheckException("value parameters are current unsupported");
                    }
                }
            }

            if (expectedParams.size() >= args.size()) {
                var params = new ArrayList<Kind>();
                var result = new Kind.TypeKind(params);
                for (var i = args.size(); i < expectedParams.size(); i++) {
                    var param = expectedParams.get(i);
                    switch (param) {
                        case DefinedType.TypeParam.ParamWithKind(var _, var paramKind) -> {
                            params.addFirst(paramKind);
                        }
                        case DefinedType.TypeParam.ValueParam(var paramName, var _) -> {
                            throw new KindCheckException("missing argument for value parameter " + paramName + " of " + name);
                        }
                    }
                }
                return result;
            } else if (rt instanceof Typeclass) {
                throw new KindCheckException("typeclass " + name + " received too many type arguments: expected " + expectedParams.size() + ", got " + args.size());
            } else if (rt instanceof TypeAlias) {
                throw new KindCheckException("type alias " + name + " received too many type arguments: expected " + expectedParams.size() + ", got " + args.size());
            } else if (rt instanceof TypeAlias.AliasParam) {
                throw new KindCheckException("type alias " + name + " received too many type arguments: expected " + expectedParams.size() + ", got " + args.size());
            } else {
                return Kind.CONCRETE;
            }
        }
    }
}
