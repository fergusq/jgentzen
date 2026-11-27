package org.kaivos.gentzen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions);
    ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions);

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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            if (substitutions.containsKey(this)) {
                return substitutions.get(this);
            } else {
                return this;
            }
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other.equals(this)) {
                return Map.of();
            } else if (instance == 0 && other instanceof TypeVar tv && tv.instance != 0) {
                return Map.of(tv, this);
            } else if (other.getTypeVars().contains(this) || instance == 0) {
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new Not(type.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new Not(type.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new And(left.substitute(substitutions), right.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new And(left.substituteTypeParams(substitutions), right.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new Or(left.substitute(substitutions), right.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new Or(left.substituteTypeParams(substitutions), right.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new Maybe(type.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new Maybe(type.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new List(type.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new List(type.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new Box(type.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new Box(type.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof TypeVar tv) {
                return tv.unify(this, scope);
            } else if (other instanceof CustomType ct) {
                return ct.unify(this, scope);
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

    public sealed interface PossibleCustomType permits Apply, CustomType {
        boolean isCustomType(String name);
    }

    public record Apply(ExpressionType highOrderType, ExpressionType arg) implements ExpressionType, PossibleCustomType {
        @Override
        public String toGentzen() {
            return highOrderType.toParensGentzen() + " " + arg.toParensGentzen();
        }
        @Override
        public String toParensGentzen() {
            return "(" + toGentzen() + ")";
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Stream.concat(highOrderType.getTypeVars().stream(), arg.getTypeVars().stream()).collect(toSet());
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return new Apply(highOrderType.substitute(substitutions), arg.substitute(substitutions));
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return new Apply(highOrderType.substituteTypeParams(substitutions), arg.substituteTypeParams(substitutions));
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof Apply a) {
                var thisAliasApplied = applyAlias(scope);
                var otherAliasApplied = applyAlias(scope);
                if (thisAliasApplied.isPresent() && otherAliasApplied.isPresent()) {
                    try {
                        return thisAliasApplied.get().unify(other, scope);
                    } catch (ExpressionTypeUnificationException _) {
                        return thisAliasApplied.get().unify(otherAliasApplied.get(), scope);
                    }
                } else if (thisAliasApplied.isPresent()) {
                    return thisAliasApplied.get().unify(other, scope);
                } else if (otherAliasApplied.isPresent()) {
                    return this.unify(otherAliasApplied.get(), scope);
                } else {
                    var substitutionsHot = highOrderType.unify(a.highOrderType, scope);
                    var substitutionsArg = arg.unify(a.arg, scope);
                    return mergeUnification(substitutionsHot, substitutionsArg, scope);
                }
            } else {
                var aliasApplied = applyAlias(scope);
                if (aliasApplied.isPresent()) {
                    return aliasApplied.get().unify(other, scope);
                } else if (other instanceof TypeVar tv) {
                    return tv.unify(this, scope);
                } else if (other instanceof CustomType ct) {
                    return ct.unify(this, scope);
                } else {
                    throw new ExpressionTypeUnificationException(this, other);
                }
            }
        }
        private static record FullApplication(ExpressionType highOrderType, java.util.List<ExpressionType> args) {}
        private FullApplication getFullApplication() {
            var args = new ArrayList<ExpressionType>();
            ExpressionType type = this;
            while (type instanceof Apply a) {
                type = a.highOrderType;
                args.addFirst(a.arg);
            }
            return new FullApplication(type, args);
        }
        private Optional<ExpressionType> applyAlias(Scope scope) {
            var thisFull = getFullApplication();
            if (thisFull.highOrderType instanceof CustomType ct) {
                var resolvedType = scope.resolveType(ct.name);
                if (resolvedType.isPresent() && resolvedType.get() instanceof TypeAlias alias) {
                    if (alias.params().size() <= thisFull.args().size() && alias.body().isPresent()) {
                        Map<String, ExpressionType> subs = new HashMap<>();
                        for (var i = 0; i < alias.params().size(); i++) {
                            subs.put(alias.params().get(i), thisFull.args().get(i));
                        }
                        var appliedType = alias.body().get().substituteTypeParams(subs);
                        for (var i = alias.params().size(); i < thisFull.args().size(); i++) {
                            appliedType = new Apply(appliedType, thisFull.args().get(i));
                        }
                        return Optional.of(appliedType);
                    }
                }
            }
            return Optional.empty();
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            var hotKind = highOrderType.kindCheck(scope);
            switch (hotKind) {
                case Kind.RuleKind(var _) -> {
                    throw new KindCheckException("rule parameters are not supported yet");
                }
                case Kind.TypeKind(var subkinds) -> {
                    if (subkinds.size() == 0) {
                        throw new KindCheckException("high order type " + highOrderType.toGentzen() + " received too many arguments");
                    } else {
                        var argKind = arg.kindCheck(scope);
                        var paramKind = subkinds.get(0);
                        if (!argKind.equals(paramKind)) {
                            throw new KindCheckException(
                                "high order type " + highOrderType.toGentzen()
                                + " received an invalid type argument: expected kind "
                                + paramKind.toKindNotation() + ", got " + argKind.toKindNotation()
                            );
                        }
                        return new Kind.TypeKind(subkinds.subList(1, subkinds.size()));
                    }
                }
            }
        }
        @Override
        public boolean isCustomType(String name) {
            return highOrderType instanceof PossibleCustomType pct && pct.isCustomType(name);
        }
    }

    public record CustomType(String name) implements ExpressionType, PossibleCustomType {
        @Override
        public String toGentzen() {
            return name;
        }
        @Override
        public String toParensGentzen() {
            return name;
        }
        @Override
        public Set<TypeVar> getTypeVars() {
            return Set.of();
        }
        @Override
        public ExpressionType substitute(Map<TypeVar, ? extends ExpressionType> substitutions) {
            return this;
        }
        @Override
        public ExpressionType substituteTypeParams(Map<String, ? extends ExpressionType> substitutions) {
            return substitutions.containsKey(name) ? substitutions.get(name) : this;
        }
        @Override
        public Map<TypeVar, ExpressionType> unify(ExpressionType other, Scope scope) throws ExpressionTypeUnificationException {
            if (other instanceof CustomType ct) {
                if (this.name.equals(ct.name())) {
                    return Map.of();
                }
                var thisAliasApplied = applyAlias(scope);
                var otherAliasApplied = ct.applyAlias(scope);
                if (thisAliasApplied.isPresent() && otherAliasApplied.isPresent()) {
                    try {
                        return thisAliasApplied.get().unify(other, scope);
                    } catch (ExpressionTypeUnificationException _) {
                        return thisAliasApplied.get().unify(otherAliasApplied.get(), scope);
                    }
                } else if (thisAliasApplied.isPresent()) {
                    return thisAliasApplied.get().unify(other, scope);
                } else if (otherAliasApplied.isPresent()) {
                    return this.unify(otherAliasApplied.get(), scope);
                } else {
                    throw new ExpressionTypeUnificationException(this, other);
                }
            } else {
                var aliasApplied = applyAlias(scope);
                if (aliasApplied.isPresent()) {
                    return aliasApplied.get().unify(other, scope);
                } else if (other instanceof TypeVar tv) {
                    return tv.unify(this, scope);
                } else {
                    throw new ExpressionTypeUnificationException(this, other);
                }
            }
        }
        private Optional<ExpressionType> applyAlias(Scope scope) {
            var resolvedType = scope.resolveType(name);
            if (resolvedType.isPresent() && resolvedType.get() instanceof TypeAlias alias && alias.getParams().size() == 0) {
                return alias.body();
            } else {
                return Optional.empty();
            }
        }
        @Override
        public Kind kindCheck(Scope scope) throws KindCheckException {
            var resolvedType = scope.resolveType(name);
            if (resolvedType.isEmpty()) {
                throw new KindCheckException("unknown type " + name);
            }

            var rt = resolvedType.get();
            var params = rt.getParams();
            if (params.size() == 0) {
                return Kind.CONCRETE;
            }

            var subkinds = new ArrayList<Kind>();
            for (var param : params) {
                switch (param) {
                    case DefinedType.TypeParam.ValueParam(var _, var _) -> {
                        throw new KindCheckException("value parameters are not yet supported");
                    }
                    case DefinedType.TypeParam.ParamWithKind(var _, var kind) -> {
                        subkinds.add(kind);
                    }
                }
            }
            return new Kind.TypeKind(subkinds);
        }
        @Override
        public boolean isCustomType(String name) {
            return this.name.equals(name);
        }
    }
}
