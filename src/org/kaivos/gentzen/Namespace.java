package org.kaivos.gentzen;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.kaivos.gentzen.parser.GentzenLexer;
import org.kaivos.gentzen.parser.GentzenParser;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;

public class Namespace {
    private HashMap<String, Rule> definedRules;
    private ArrayList<String> importedFiles;

    public Namespace() {
        definedRules = new HashMap<>();
        importedFiles = new ArrayList<>();
    }

    public void addRuleDecl(Rule rule) throws DuplicateException {
        if (definedRules.containsKey(rule.getName())) {
            throw new DuplicateException("duplicate declaration of " + rule.getName());
        }
        definedRules.put(rule.getName(), rule);
    }

    public void addRuleDef(String name, Expression body) throws MissingException, DuplicateException, Expression.TypeCheckException {
        if (!definedRules.containsKey(name)) {
            throw new MissingException("definition of undeclared rule " + name);
        }
        var rule = definedRules.get(name);
        if (rule.getBody().isPresent()) {
            throw new DuplicateException("duplicate definition of " + name);
        }
        rule.setBody(body);
        body.typeCheck(toScope(), rule.getSignature());
    }

    public void importFile(String file) {
        if (importedFiles.contains(file)) {
            return;
        }
        importedFiles.add(file);
        try {
            GentzenLexer lexer = new GentzenLexer(new ANTLRFileStream(file + ".gentzen"));
            GentzenParser parser = new GentzenParser(new CommonTokenStream(lexer));
            var decldefs = parser.gentzenFile().decldefs;
            for (var decldef : decldefs) {
                try {
                    decldef.register(this);
                } catch (CompilationException ex) {
                    System.err.println(ex.getStart().toString() + ": " + ex.getMessage() + (ex.getCause() != null ? ": " + ex.getCause().getMessage() : ""));
                } catch (DeclarationException ex) {
                    System.err.println(decldef.getStart().toString() + ": " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Scope toScope() {
        return new Scope(
            definedRules.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().getSignature())),
            Map.of()
        );
    }

    Iterable<String> toScheme() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("(include \"lib/stdlib.ss\")");
        lines.add("(include \"lib/iolib.ss\")");
        for (String s : importedFiles) {
            lines.add(";(import " + s + ")");
        }
        lines.add("");
        for (Rule r : definedRules.values()) {
            r.getBody().ifPresent(body -> {
                lines.add("; " + r.getName() + " : " + r.getSignature().toGentzen());
                lines.add("; " + r.getName() + " = " + body.toGentzen());
                lines.add("(define g/" + r.getName() + " " + body.toScheme() + ")");
                lines.add("");
            });
        }
        return lines;
    }

    public static class DeclarationException extends Exception {
        public DeclarationException(String message) {
            super(message);
        }
    }

    public static class MissingException extends DeclarationException {
        public MissingException(String message) {
            super(message);
        }
    }

    public static class DuplicateException extends DeclarationException {
        public DuplicateException(String message) {
            super(message);
        }
    }
}
