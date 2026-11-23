package org.kaivos.gentzen;

public sealed interface DeclDef {
    public void register(Namespace namespace) throws CompilationException;
    public record RuleDeclaration(String name, RuleSignature signature) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws CompilationException {
            System.out.println(name + " : " + signature.toGentzen());
            namespace.addRuleDecl(new Rule(name, signature));
        }
    }
    public record RuleDefinition(String name, Expression body) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws CompilationException {
            System.out.println(name + " = " + body.toGentzen());
            namespace.addRuleDef(name, body);
        }
    }
}
