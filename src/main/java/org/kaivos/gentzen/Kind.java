package org.kaivos.gentzen;

import static java.util.stream.Collectors.joining;

import java.util.List;
import org.kaivos.gentzen.exceptions.KindCheckException;

public sealed interface Kind {
    public static final Kind CONCRETE = new TypeKind(List.of());

    String toGentzen();
    String toKindNotation();
    String toKindNotationArg();

    public default void expectConcrete(
        String message
    ) throws KindCheckException {
        if (!equals(CONCRETE)) {
            throw new KindCheckException(
                message + ": expected concrete type, got " + toGentzen()
            );
        }
    }

    public static record TypeKind(List<Kind> subkinds) implements Kind {
        @Override
        public String toGentzen() {
            return subkinds
                .stream()
                .map(k -> "(" + k + ")")
                .collect(joining(" "));
        }

        @Override
        public String toKindNotation() {
            return (
                subkinds
                    .stream()
                    .map(Kind::toKindNotationArg)
                    .collect(joining()) + "*"
            );
        }

        @Override
        public String toKindNotationArg() {
            if (subkinds.isEmpty()) {
                return "* -> ";
            } else {
                return "(" + toKindNotation() + ") -> ";
            }
        }
    }

    public static record RuleKind(RuleSignature ruleSignature) implements Kind {
        @Override
        public String toGentzen() {
            return "{" + ruleSignature.toGentzen() + "}";
        }

        @Override
        public String toKindNotation() {
            return toGentzen();
        }

        @Override
        public String toKindNotationArg() {
            return toGentzen();
        }
    }
}
