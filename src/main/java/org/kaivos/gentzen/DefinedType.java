package org.kaivos.gentzen;

import java.util.List;
import java.util.Optional;

public sealed interface DefinedType permits TypeAlias, TypeAlias.AliasParam, Typeclass, DefinedType.DefinedTypeImpl {
    String getName();
    List<? extends TypeParam> getParams();

    default String toGentzen() {
        String result = getName();
        for (var param : getParams()) {
            result += " " + param.toGentzen();
        }
        return result;
    }

    public sealed interface TypeParam {
        String toGentzen();
        public record ParamWithKind(
            String name,
            Kind kind
        ) implements TypeParam {
            @Override
            public String toGentzen() {
                return "[" + name + (" " + kind.toGentzen()).strip() + "]";
            }
            public boolean canBeRepresentedAsDefinedType() {
                return kind instanceof Kind.TypeKind;
            }
            public Optional<DefinedType> toDefinedType() {
                if (kind instanceof Kind.TypeKind tk) {
                    return Optional.of(new DefinedTypeImpl(
                        name,
                        tk.subkinds().stream().map(sk -> new ParamWithKind("_", sk)).toList()
                    ));
                } else {
                    return Optional.empty();
                }
            }
        }

        public record ValueParam(
            String name,
            RuleSignature type
        ) implements TypeParam {
            @Override
            public String toGentzen() {
                return "{" + name + " : " + type.toGentzen() + "}";
            }
        }
    }

    public static record DefinedTypeImpl(String name, List<? extends TypeParam> params) implements DefinedType {
		@Override
		public String getName() {
			return name;
		}

		@Override
		public List<? extends TypeParam> getParams() {
			return params;
		}

    }
}
