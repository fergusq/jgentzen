package org.kaivos.gentzen;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.kaivos.gentzen.exceptions.CompilationException;
import org.kaivos.gentzen.exceptions.ExpressionTypeUnificationException;
import org.kaivos.gentzen.exceptions.TypeCheckException;
import org.kaivos.gentzen.parser.GentzenLexer;
import org.kaivos.gentzen.parser.GentzenParser;

public class Namespace {
    public static class MetaNamespace {
        private HashMap<String, Namespace> namespaces = new HashMap<>();

        public Namespace getNamespace(String name) {
            if (namespaces.containsKey(name)) {
                return namespaces.get(name);
            } else {
                var ns = new Namespace(name, this);
                namespaces.put(name, ns);
                return ns;
            }
        }

        public boolean hasNamespace(String name) {
            return namespaces.containsKey(name);
        }
    }

    public static record DefinedDeclDef<I>(DeclDef declDef, I instance) {}

    private String name;
    private MetaNamespace metaNamespace;
    private ArrayList<Path> searchPaths;
    private HashMap<String, DefinedDeclDef<Rule>> definedRules;
    private HashMap<String, DefinedDeclDef<DefinedType>> definedTypes;
    private ArrayList<DefinedDeclDef<Typeclass>> typeclasses;
    private HashMap<String, Namespace> importedFiles;
    private HashMap<String, ExportType> exportedSymbols;
    private ArrayList<String> rawScheme;
    private ArrayList<CompilationException> errors;

    private Namespace(String name, MetaNamespace metaNamespace) {
        this.name = name;
        this.metaNamespace = metaNamespace;
        searchPaths = new ArrayList<>();
        definedRules = new HashMap<>();
        definedTypes = new HashMap<>();
        typeclasses = new ArrayList<>();
        importedFiles = new HashMap<>();
        exportedSymbols = new HashMap<>();
        rawScheme = new ArrayList<>();
        errors = new ArrayList<>();
    }

    public void addRuleDecl(DeclDef.RuleDeclaration decl) throws DuplicateException {
        var rule = new Rule(decl.name(), decl.signature());
        if (definedRules.containsKey(rule.getName())) {
            throw new DuplicateException("duplicate declaration of " + rule.getName());
        }
        definedRules.put(rule.getName(), new DefinedDeclDef<Rule>(decl, rule));
    }

    public void addRuleDef(String name, Expression body) throws MissingException, DuplicateException, TypeCheckException {
        if (!definedRules.containsKey(name)) {
            throw new MissingException("definition of undeclared rule " + name);
        }
        var rule = definedRules.get(name);
        if (rule.instance().getBody().isPresent()) {
            throw new DuplicateException("duplicate definition of " + name);
        }
        rule.instance().setBody(body);
        body.typeCheck(toScope(), rule.instance().getSignature());
    }

    public void addTypeAlias(DeclDef.TypeAliasDecl decl) throws DuplicateException {
        if (definedTypes.containsKey(decl.alias().name())) {
            throw new DuplicateException("duplicate definition of type " + decl.alias().name());
        }
        definedTypes.put(decl.alias().name(), new DefinedDeclDef<DefinedType>(decl, decl.alias()));
    }

    public void addTypeclass(DeclDef.TypeclassDecl decl) throws DeclarationException, CompilationException {
        if (definedTypes.containsKey(decl.typeclass().name())) {
            throw new DuplicateException("duplicate definition of type " + decl.typeclass().name());
        }
        if (!decl.typeclass().exports().containsKey(decl.typeclass().name())) {
            throw new DeclarationException("typeclass " + decl.typeclass().name() + " does not export its own type");
        }
        definedTypes.put(decl.typeclass().name(), new DefinedDeclDef<DefinedType>(decl, decl.typeclass()));
        typeclasses.add(new DefinedDeclDef<>(decl, decl.typeclass()));

        // The inner scope only adds non-exported rules since
        var scope = new Scope(toScope(), decl.typeclass().classRules(), Map.of(), Map.of());

        var allRules = decl.typeclass().classRules();
        for (var cutEliminationRule : decl.typeclass().cutEliminationRules()) {
            if (!allRules.containsKey(cutEliminationRule.primaryRule())) {
                throw new MissingException("cut elimination rule " + cutEliminationRule.nameToGentzen() + " refers to an undeclared class rule " + cutEliminationRule.primaryRule());
            }
            if (!allRules.containsKey(cutEliminationRule.secondaryRule())) {
                throw new MissingException("cut elimination rule " + cutEliminationRule.nameToGentzen() + " refers to an undeclared class rule " + cutEliminationRule.secondaryRule());
            }
            var primaryRule = allRules.get(cutEliminationRule.primaryRule());
            var secondaryRule = allRules.get(cutEliminationRule.secondaryRule());
            var expectedAbove = new ArrayList<RuleSignature.Sequent>();
            expectedAbove.addAll(primaryRule.above());
            expectedAbove.addAll(secondaryRule.above());
            var expectedBelowNumbered = new HashMap<Integer, RuleSignature.SequentParameter>();
            var j = 0;
            for (var i : primaryRule.below().numberedParams().keySet().stream().sorted().toList()) {
                if (i == cutEliminationRule.primaryArg()) {
                    if (primaryRule.below().numberedParams().get(i).input()) {
                        throw new DeclarationException("cannot cut an input parameter of a primary rule: " + cutEliminationRule.primaryRule() + " $" + cutEliminationRule.primaryArg());
                    }
                    continue;
                } else {
                    expectedBelowNumbered.put(++j, primaryRule.below().numberedParams().get(i));
                }
            }
            for (var i : secondaryRule.below().numberedParams().keySet().stream().sorted().toList()) {
                if (i == cutEliminationRule.secondaryArg()) {
                    if (!secondaryRule.below().numberedParams().get(i).input()) {
                        throw new DeclarationException("cannot cut an output parameter of a secondary rule: " + cutEliminationRule.secondaryRule() + " $" + cutEliminationRule.secondaryArg());
                    }
                    continue;
                } else {
                    expectedBelowNumbered.put(++j, secondaryRule.below().numberedParams().get(i));
                }
            }
            var expectedBelowContextVars = new ArrayList<RuleSignature.ContextVarParameter>();
            expectedBelowContextVars.addAll(primaryRule.below().contextVars());
            expectedBelowContextVars.addAll(secondaryRule.below().contextVars());
            cutEliminationRule.body().typeCheck(
                scope,
                new RuleSignature(
                    expectedAbove,
                    new RuleSignature.Sequent(
                        expectedBelowNumbered,
                        Map.of(),
                        expectedBelowContextVars
                    )
                )
            );
        }

        // Add exported rules to the namespace
        for (var classRule : decl.typeclass().classRules().entrySet()) {
            System.out.println(classRule.getKey() + " : " + classRule.getValue().toGentzen());
            // TODO: exports only partially handled
            // non-exported names still cause duplicate errors since their names are not mangled
            if (definedRules.containsKey(classRule.getKey())) {
                throw new DuplicateException("duplicate declaration of " + classRule.getKey());
            }

            if (decl.typeclass().exports().containsKey(classRule.getKey())) {
                var exportType = decl.typeclass().exports().get(classRule.getKey());
                if (exportType == ExportType.PRIMARY || exportType == ExportType.SECONDARY) {
                    throw new DeclarationException("primary and secondary rule exports are not yet implemented");
                }
                var substitutions = decl.typeclass().getParams().stream()
                    .filter(param -> param instanceof DefinedType.TypeParam.ParamWithKind p && p.canBeRepresentedAsDefinedType())
                    .map(param -> (DefinedType.TypeParam.ParamWithKind) param)
                    .collect(toMap(param -> param.name(), param -> new ExpressionType.TypeVar(param.name(), 0)));
                try {
                var signature = classRule.getValue().substituteTypeParams(substitutions);
                definedRules.put(
                    classRule.getKey(),
                    new DefinedDeclDef<Rule>(decl, new Rule(classRule.getKey(), signature))
                );
                } catch (ExpressionTypeUnificationException ex) {
                    throw new CompilationException(ex);
                }
            }
        }
    }

    public void addExports(Map<String, ExportType> exports) throws DeclarationException {
        var missing = exports.keySet().stream()
            .filter(e ->
                !definedRules.containsKey(e)
                && !definedTypes.containsKey(e)
            )
            .toList();
        if (missing.size() > 0) {
            throw new MissingException("cannot export undefined symbol(s): " + missing.stream().collect(joining(", ")));
        }
        if (exports.values().stream().filter(t -> t == ExportType.PRIMARY || t == ExportType.SECONDARY).count() > 0) {
            throw new DeclarationException("primary and secondary rule exports are not yet implemented");
        }
        this.exportedSymbols.putAll(exports);
    }

    public boolean importFile(String file) {
        if (importedFiles.containsKey(file)) {
            return true;
        }

        var alreadyLoaded = metaNamespace.hasNamespace(file);

        Namespace importedNamespace = metaNamespace.getNamespace(file);
        importedFiles.put(file, importedNamespace);

        if (!alreadyLoaded) {
            importedNamespace.searchPaths.addAll(searchPaths);
            return importedNamespace.loadFile(file);
        } else {
            return true;
        }
    }

    public void addRawScheme(String text) {
        rawScheme.add(text);
    }

    public boolean loadText(String text) {
        return loadCharStream(CharStreams.fromString(text));
    }

    public boolean loadFile(String file) {
        Path target = Path.of(file + ".gentzen");
        Path rawSchemeTarget = Path.of(file + ".ss");
        for (var path : searchPaths) {
            var resolved = path.resolve(target);
            var resolvedRawScheme = path.resolve(rawSchemeTarget);
            if (Files.isRegularFile(resolved)) {
                if (Files.isRegularFile(resolvedRawScheme)) {
                    try {
                        addRawScheme(Files.readString(resolvedRawScheme));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return false;
                    }
                }
                return loadPath(resolved);
            }
        }

        // not found
        System.err.println("failed to load " + file + ": not found in any of the search paths " + searchPaths.toString());
        return false;
    }

    public boolean loadPath(Path target) {
        try {
            return loadCharStream(CharStreams.fromPath(target));
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean loadCharStream(CharStream stream) {
        boolean success = true;
        try {
            GentzenLexer lexer = new GentzenLexer(stream);
            MacroProcessor processor = new MacroProcessor(lexer);
            GentzenParser parser = new GentzenParser(new CommonTokenStream(processor));
            parser.addErrorListener(new ANTLRErrorListener() {

				@Override
				public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
						int charPositionInLine, String msg, RecognitionException e) {
						if (offendingSymbol instanceof Token t) {
						    errors.add(new CompilationException(msg, t, t));
						} else {
                		    errors.add(new CompilationException(msg));
						}
				}

				@Override
				public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact,
						BitSet ambigAlts, ATNConfigSet configs) {}

				@Override
				public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
						BitSet conflictingAlts, ATNConfigSet configs) {}

				@Override
				public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
						int prediction, ATNConfigSet configs) {}

            });
            var decldefs = parser.gentzenFile().decldefs;
            for (var decldef : decldefs) {
                var location = decldef.getStart().toString();
                try {
                    decldef.register(this);
                } catch (CompilationException ex) {
                    System.err.println(ex.getLocation(location) + ": " + ex.getMessage() + (ex.getCause() != null ? ": " + ex.getCause().getMessage() : ""));
                    //ex.printStackTrace();
                    success = false;
                    errors.add(ex);
                } catch (DeclarationException ex) {
                    System.err.println(location + ": " + ex.getMessage());
                    //ex.printStackTrace();
                    success = false;
                    errors.add(new CompilationException(ex.getMessage(), decldef.getStart(), decldef.getStop()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        var scope = toScope();
        for (DefinedDeclDef<Rule> dr : definedRules.values()) {
            if (dr.declDef() instanceof DeclDef.TypeclassDecl) continue; // These are checked below
            try {
                dr.instance().getSignature().kindCheck(scope);
            } catch (CompilationException ex) {
                System.err.println(ex.getLocation(dr.declDef().getStart().toString()) + ": error while type checking " + dr.instance().getName() + ": " + ex.getMessage());
                ex.printStackTrace();
                success = false;
                if (ex.getStart().isEmpty()) {
                    ex = new CompilationException(ex, dr.declDef().getStart(), dr.declDef().getStop());
                }
                errors.add(ex);
            }
        }
        for (var dtc : typeclasses) {
            var typeclassScope = new Scope(
                scope,
                Map.of(),
                dtc.instance().getParams().stream()
                    .filter(param -> param instanceof DefinedType.TypeParam.ParamWithKind p && p.canBeRepresentedAsDefinedType())
                    .map(param -> (DefinedType.TypeParam.ParamWithKind) param)
                    .map(param -> param.toDefinedType().get())
                    .collect(toMap(dt -> dt.getName(), dt -> dt)),
                // TODO rule and value parameters
                Map.of()
            );
            for (var classRule : dtc.instance().classRules().values()) {
                try {
                    classRule.kindCheck(typeclassScope);
                } catch (CompilationException ex) {
                    System.err.println(ex.getLocation(dtc.declDef().getStart().toString()) + ": error while type checking " + dtc.instance().getName() + ": " + ex.getMessage());
                    ex.printStackTrace();
                    success = false;
                    if (ex.getStart().isEmpty()) {
                        ex = new CompilationException(ex, dtc.declDef().getStart(), dtc.declDef().getStop());
                    }
                    errors.add(ex);
                }
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

    public Map<String, DefinedDeclDef<Rule>> getDefinedRules() {
        return definedRules;
    }

    public Map<String, DefinedDeclDef<DefinedType>> getDefinedTypes() {
        return definedTypes;
    }

    public List<CompilationException> getErrors() {
        return errors;
    }

    public Scope toScope() {
        var rules = new HashMap<String, DefinedDeclDef<Rule>>();
        var types = new HashMap<String, DefinedDeclDef<DefinedType>>();
        for (var importedNamespace : importedFiles.values()) {
            rules.putAll(importedNamespace.definedRules);
            types.putAll(importedNamespace.definedTypes);
        }
        rules.putAll(definedRules);
        types.putAll(definedTypes);
        return new Scope(
            rules.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().instance().getSignature())),
            types.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().instance())),
            Map.of()
        );
    }

    List<String> toScheme() {
        return toScheme(new HashSet<>());
    }

    List<String> toScheme(Set<String> alreadyEmitted) {
        ArrayList<String> lines = new ArrayList<>();
        if (alreadyEmitted.contains(name)) {
            return lines;
        }
        for (var importedNamespace : importedFiles.values()) {
            lines.addAll(importedNamespace.toScheme(alreadyEmitted));
        }
        alreadyEmitted.add(name);
        var exports = exportedSymbols.keySet().stream()
            .filter(definedRules::containsKey)
            .map(s -> "g/" + s)
            .collect(joining(" "));
        if (name.equals("stdlib")) {
            // This hardcoded rule is not really a rule since it's not representable by the type system
            // It cannot be exported normally, so we just hardcode it here so that it gets exported in Scheme
            exports += " g/neces";
        }
        lines.add("(library (" + name + ")");
        lines.add("  (export " + exports + ")");
        lines.add("  (import (rnrs) " + importedFiles.keySet().stream().map(n -> "(" + n + ")").collect(joining(" ")) + ")");
        lines.add("");
        lines.addAll(rawScheme);
        if (rawScheme.size() > 0) {
            lines.add("");
        }
        for (DefinedDeclDef<Rule> dr : definedRules.values().stream().sorted((a, b) -> a.declDef().getStart().getTokenIndex() - b.declDef().getStart().getTokenIndex()).toList()) {
            var r = dr.instance();
            lines.add("  ; " + r.getName() + " : " + r.getSignature().toGentzen());
            r.getBody().ifPresent(body -> {
                lines.add("  ; " + r.getName() + " = " + body.toGentzen());
                lines.add(
                    "  (define g/" + r.getName() + "\n" +
                    "    " + body.toScheme() + ")"
                );
                lines.add("");
            });
        }
        for (var dtc : typeclasses) {
            var rules = new HashMap<String, RuleSignature>();
            for (var classRule : dtc.instance().classRules().entrySet()) {
                // TODO: mangling
                var ruleName = classRule.getKey();
                var signature = classRule.getValue();
                rules.put(ruleName, signature);

                var numberedParams = signature.below().numberedParams().entrySet();
                var outputParams = numberedParams.stream()
                    .filter(p -> !p.getValue().input()
                        && p.getValue().type() instanceof ExpressionType.PossibleCustomType pct
                        && pct.isCustomType(dtc.instance().getName()))
                    .map(Map.Entry::getKey)
                    .toList();
                var inputParams = numberedParams.stream()
                    .filter(p -> p.getValue().input()
                        && p.getValue().type() instanceof ExpressionType.PossibleCustomType pct
                        && pct.isCustomType(dtc.instance().getName()))
                    .map(Map.Entry::getKey)
                    .toList();
                if (outputParams.size() >= 1 && inputParams.size() == 0) {
                    // Primary rules "create" tc values
                    var params = Stream.concat(
                        numberedParams.stream().map(p -> "p" + p.getKey()),
                        IntStream.range(0, signature.above().size()).mapToObj(i -> "f" + i)
                    ).collect(joining(" "));
                    var paramsWithoutOutput = numberedParams.stream()
                        .filter(p -> p.getKey() != outputParams.get(0)).map(p -> "p" + p.getKey())
                        .collect(joining(" "));
                    var above = IntStream.range(0, signature.above().size()).mapToObj(i -> "f" + i).collect(joining(" "));
                    lines.add("  ; " + ruleName + " : " + signature.toGentzen());
                    lines.add(
                        "  (define (g/%s %s)\n".formatted(ruleName, params) +
                        "    (p%d (list '%s (list %s) (list %s))))".formatted(outputParams.get(0), ruleName, paramsWithoutOutput, above)
                    );
                } else if (outputParams.size() == 0 && inputParams.size() >= 1) {
                    // Secondary rules consume tc values
                    var params = Stream.concat(
                        numberedParams.stream().map(p -> "p" + p.getKey()),
                        IntStream.range(0, signature.above().size()).mapToObj(i -> "f" + i)
                    ).collect(joining(" "));
                    Function<Integer, String> paramsWithoutInput = ip -> numberedParams.stream()
                        .filter(p -> p.getKey() != ip).map(p -> "p" + p.getKey())
                        .collect(joining(" "));
                    var above = IntStream.range(0, signature.above().size()).mapToObj(i -> "f" + i).collect(joining(" "));
                    lines.add("  ; " + ruleName + " : " + signature.toGentzen());
                    for (var ip : inputParams) {
                        lines.add("  (define gct/%s$%d (vector '()))".formatted(ruleName, ip));
                    }
                    lines.add(
                        "  (define (g/%s %s)\n".formatted(ruleName, params) +
                        "    (call/cc (lambda (exit)"
                    );
                    for (var ip : inputParams) {
                        lines.add(
                            "      (let ((cer (assq (car p%d) (vector-ref gct/%s$%d 0))))\n".formatted(ip, ruleName, ip) +
                            "        (when cer (exit (apply (cadr cer) (append (cadr p%s) (list %s) (caddr p%s) (list %s))))))".formatted(ip, paramsWithoutInput.apply(ip), ip, above)
                        );
                    }
                    lines.add("      (error (string-append \"missing cut elimination rule for \" (symbol->string p%d) \" %s\")))))".formatted(inputParams.get(0), ruleName));
                } else if (outputParams.size() >= 1 && inputParams.size() >= 1) {
                    // Primary and secondary rule: checks if the cut elimination rule table contains a rule
                    // if so: acts like secondary
                    // if not: acts like primary
                    var params = Stream.concat(
                        numberedParams.stream().map(p -> "p" + p.getKey()),
                        IntStream.range(0, signature.above().size()).mapToObj(i -> "f" + i)
                    ).collect(joining(" "));
                    Function<Integer, String> paramsWithoutInput = ip -> numberedParams.stream()
                        .filter(p -> p.getKey() != ip).map(p -> "p" + p.getKey())
                        .collect(joining(" "));
                    var paramsWithoutOutput = numberedParams.stream()
                        .filter(p -> p.getKey() != outputParams.get(0)).map(p -> "p" + p.getKey())
                        .collect(joining(" "));
                    var above = IntStream.range(0, signature.above().size()).mapToObj(i -> "f" + i).collect(joining(" "));
                    lines.add("  ; " + ruleName + " : " + signature.toGentzen());
                    for (var ip : inputParams) {
                        lines.add("  (define gct/%s$%d (vector '()))".formatted(ruleName, ip));
                    }
                    lines.add(
                        "  (define (g/%s %s)\n".formatted(ruleName, params) +
                        "    (call/cc (lambda (exit)"
                    );
                    for (var ip : inputParams) {
                        lines.add(
                            "      (let ((cer (assq (car p%d) (vector-ref gct/%s$%d 0))))\n".formatted(ip, ruleName, ip) +
                            "        (when cer (exit (apply (cadr cer) (append (cadr p%s) (list %s) (caddr p%s) (list %s))))))".formatted(ip, paramsWithoutInput.apply(ip), ip, above)
                        );
                    }
                    // Act as primary:
                    lines.add("      (exit (p%d (list '%s (list %s) (list %s)))))))".formatted(outputParams.get(0), ruleName, paramsWithoutOutput, above));
                }
                lines.add("");
            }
            for (var cer : dtc.instance().cutEliminationRules()) {
                // This is already checked during declaration time, so no error here
                if (rules.containsKey(cer.primaryRule()) && rules.containsKey(cer.secondaryRule())) {
                    var mangledCerName = cer.primaryRule() + "$" + cer.primaryArg() + "/" + cer.secondaryRule() + "$" + cer.secondaryArg();
                    lines.add("  ; " + cer.toGentzen());
                    lines.add("  (define " + mangledCerName + "\n    " + cer.body().toScheme() + ")");
                    lines.add("");
                }
            }
            // Scheme does not allow mutating defined names in a library, so we use vector as a mutable cell
            // Calling vector-set! changes the context so that we cannot define more names, so we must call it the very last
            for (var cer : dtc.instance().cutEliminationRules()) {
                // This is already checked during declaration time, so no error here
                if (rules.containsKey(cer.primaryRule()) && rules.containsKey(cer.secondaryRule())) {
                    var mangledCerName = cer.primaryRule() + "$" + cer.primaryArg() + "/" + cer.secondaryRule() + "$" + cer.secondaryArg();
                    lines.add("  (vector-set! gct/" + cer.secondaryRule() + "$" + cer.secondaryArg() + " 0 (cons (list '" + cer.primaryRule() + " " + mangledCerName + ") (vector-ref gct/" + cer.secondaryRule() + "$" + cer.secondaryArg() + " 0)))");
                }
            }
        }
        lines.add(")");
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
