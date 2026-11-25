package org.kaivos.gentzen;

import java.util.List;

import org.antlr.v4.runtime.Token;
import org.kaivos.gentzen.Namespace.DeclarationException;
import org.kaivos.gentzen.exceptions.CompilationException;

public sealed interface DeclDef {

    public Token getStart();
    public Token getStop();
    public void register(Namespace namespace) throws DeclarationException, CompilationException;

    public record RuleDeclaration(String name, RuleSignature signature, Token start, Token stop) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws DeclarationException, CompilationException {
            System.out.println(name + " : " + signature.toGentzen());
            namespace.addRuleDecl(new Rule(name, signature, start, stop));
        }
        @Override
        public Token getStart() {
            return start;
        }
        @Override
        public Token getStop() {
            return stop;
        }
    }

    public record RuleDefinition(String name, Expression body, Token start, Token stop) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws DeclarationException, CompilationException {
            System.out.println(name + " = " + body.toGentzen());
            namespace.addRuleDef(name, body);
        }
        @Override
        public Token getStart() {
            return start;
        }
        @Override
        public Token getStop() {
            return stop;
        }
    }

    public record ImportDecl(String file, Token start, Token stop) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws DeclarationException, CompilationException {
            namespace.importFile(file);
        }
        @Override
        public Token getStart() {
            return start;
        }
        @Override
        public Token getStop() {
            return stop;
        }
    }

    public record ExportDecl(List<String> names, Token start, Token stop) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws DeclarationException, CompilationException {
            // TODO
        }
        @Override
        public Token getStart() {
            return start;
        }
        @Override
        public Token getStop() {
            return stop;
        }
    }

    public record TypeclassDecl(Typeclass typeclass, Token start, Token stop) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws DeclarationException, CompilationException {
            // TODO
        }
        @Override
        public Token getStart() {
            return start;
        }
        @Override
        public Token getStop() {
            return stop;
        }
    }

    public record TypeAliasDecl(TypeAlias alias, Token start, Token stop) implements DeclDef {
        @Override
        public void register(Namespace namespace) throws DeclarationException, CompilationException {
            namespace.addTypeAlias(alias.name(), alias);
        }
        @Override
        public Token getStart() {
            return start;
        }
        @Override
        public Token getStop() {
            return stop;
        }
    }
}
