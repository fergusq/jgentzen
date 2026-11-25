package org.kaivos.gentzen;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

public record TypeAlias(
    String name,
    List<String> params,
    Optional<ExpressionType> body
) implements DefinedType {
    public record AliasParam(String name, Kind kind, Optional<ExpressionType> body) implements DefinedType {
        @Override
        public List<TypeParam> getParams() {
            return switch (kind) {
                case Kind.TypeKind(var subkinds) -> {
                    yield subkinds.stream().map(k -> new TypeParam.ParamWithKind("_", k)).collect(toList());
                }
                case Kind.RuleKind(var _) -> {
                    yield List.of();
                }
            };
        }
    }
    @Override
    public List<TypeParam> getParams() {
        return params
            .stream()
            .map(name ->
                new TypeParam.ParamWithKind(name, Kind.CONCRETE)
            )
            .collect(toList());
    }
}
