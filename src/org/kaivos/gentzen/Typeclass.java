package org.kaivos.gentzen;

import java.util.List;
import java.util.Map;

public record Typeclass(
    String name,
    List<TypeParam> params,
    Map<String, RuleSignature> classRules,
    List<CutEliminationRule> cutEliminationRules
) implements DefinedType {
    public record CutEliminationRule(
        String primaryRule,
        int primaryArg,
        String secondaryRule,
        int secondaryArg,
        Expression body
    ) {}

	@Override
	public List<TypeParam> getParams() {
		return params;
	}
}
