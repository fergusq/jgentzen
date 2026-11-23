package org.kaivos.gentzen;

public sealed interface Type permits RuleSignature, ExpressionType {
    public String toGentzen();
}
