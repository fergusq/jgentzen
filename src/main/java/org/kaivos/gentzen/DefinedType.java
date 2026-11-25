package org.kaivos.gentzen;

import java.util.List;

public sealed interface DefinedType permits TypeAlias, TypeAlias.AliasParam, Typeclass {
    public List<TypeParam> getParams();

    public sealed interface TypeParam {
        public record ParamWithKind(
            String name,
            Kind kind
        ) implements TypeParam {}

        public record ValueParam(
            String name,
            RuleSignature type
        ) implements TypeParam {}
    }
}
