package org.kaivos.gentzen;

import java.util.List;
import java.util.Map;

public record Typeclass(
    String name,
    List<TypeParam> params,
    Map<String, RuleSignature> classRules,
    List<CutEliminationRule> cutEliminationRules,
    Map<String, ExportType> exports
) implements DefinedType {
    public record CutEliminationRule(
        String primaryRule,
        int primaryArg,
        String secondaryRule,
        int secondaryArg,
        Expression body
    ) {
        public String nameToGentzen() {
            return primaryRule + " $" + primaryArg + " " + secondaryRule + " $" + secondaryArg();
        }

        public String toGentzen() {
            return nameToGentzen() + " = " + body.toGentzen() + ";";
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TypeParam> getParams() {
        return params;
    }
}
