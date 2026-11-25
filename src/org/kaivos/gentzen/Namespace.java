package org.kaivos.gentzen;

import static java.util.stream.Collectors.toMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.kaivos.gentzen.exceptions.CompilationException;
import org.kaivos.gentzen.exceptions.TypeCheckException;
import org.kaivos.gentzen.parser.GentzenLexer;
import org.kaivos.gentzen.parser.GentzenParser;

public class Namespace {
    private ArrayList<Path> searchPaths;
    private HashMap<String, Rule> definedRules;
    private HashMap<String, DefinedType> definedTypes;
    private ArrayList<String> importedFiles;

    public Namespace() {
        searchPaths = new ArrayList<>();
        definedRules = new HashMap<>();
        definedTypes = new HashMap<>();
        importedFiles = new ArrayList<>();
    }

    public void addRuleDecl(Rule rule) throws DuplicateException {
        if (definedRules.containsKey(rule.getName())) {
            throw new DuplicateException("duplicate declaration of " + rule.getName());
        }
        definedRules.put(rule.getName(), rule);
    }

    public void addRuleDef(String name, Expression body) throws MissingException, DuplicateException, TypeCheckException {
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

    public void addTypeAlias(String name, TypeAlias alias) throws DuplicateException {
        if (definedTypes.containsKey(name)) {
            throw new DuplicateException("duplicate definition of type " + name);
        }
        definedTypes.put(name, alias);
    }

    public boolean importFile(String file) {
        if (importedFiles.contains(file)) {
            return true;
        }
        importedFiles.add(file);

        Path target = Path.of(file + ".gentzen");
        for (var path : searchPaths) {
            var resolved = path.resolve(target);
            if (Files.isRegularFile(resolved)) {
                return importPath(resolved);
            }
        }

        // not found
        System.err.println("failed to import " + file + ": not found in any of the search paths " + searchPaths.toString());
        return false;
    }

    private boolean importPath(Path target) {
        boolean success = true;
        try {
            GentzenLexer lexer = new GentzenLexer(CharStreams.fromPath(target));
            GentzenParser parser = new GentzenParser(new CommonTokenStream(lexer));
            var decldefs = parser.gentzenFile().decldefs;
            for (var decldef : decldefs) {
                var location = decldef.getStart().toString();
                try {
                    decldef.register(this);
                } catch (CompilationException ex) {
                    System.err.println(ex.getLocation(location) + ": " + ex.getMessage() + (ex.getCause() != null ? ": " + ex.getCause().getMessage() : ""));
                    ex.printStackTrace();
                    success = false;
                } catch (DeclarationException ex) {
                    System.err.println(location + ": " + ex.getMessage());
                    ex.printStackTrace();
                    success = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        var scope = toScope();
        for (Rule r : definedRules.values()) {
            try {
                r.getSignature().kindCheck(scope);
            } catch (CompilationException ex) {
                System.err.println(ex.getLocation(r.getDeclStart().toString()) + ": error while type checking " + r.getName() + ": " + ex.getMessage());
                ex.printStackTrace();
                success = false;
            }
        }

        return success;
    }

    public void addSearchPath(Path path, Path... paths) {
        searchPaths.add(path);
        for (var p : paths) {
            searchPaths.add(p);
        }
    }

    public List<Path> getSearchPaths() {
        return searchPaths;
    }

    Scope toScope() {
        return new Scope(
            definedRules.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().getSignature())),
            definedTypes,
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
        for (Rule r : definedRules.keySet().stream().sorted().map(definedRules::get).toList()) {
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
