package org.kaivos.gentzen.languageserver;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.antlr.v4.runtime.Token;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DocumentDiagnosticReport;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RelatedFullDocumentDiagnosticReport;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.kaivos.gentzen.Namespace;
import org.kaivos.gentzen.Namespace.MetaNamespace;

public class GentzenDocument {
    private String uri;
    private Namespace namespace;
    private String[] lines;

    public GentzenDocument(String uri, String text) {
        this.uri = uri;
        this.namespace = new MetaNamespace().getNamespace(uri);
        this.namespace.addSearchPath(Path.of("/home/iikka/Projektit/gentzen/jgentzen/lib"));
        this.namespace.addSearchPath(Path.of("/home/iikka/Projektit/gentzen/jgentzen/tests"));
        this.namespace.loadText(text);
        this.lines = text.split("\n");
    }

    public DocumentDiagnosticReport getDiagnosticReport() {
        return new DocumentDiagnosticReport(new RelatedFullDocumentDiagnosticReport(getDiagnostics()));
    }

    private List<Diagnostic> getDiagnostics() {
        return namespace.getErrors()
            .stream()
            .filter(error -> error.getStart().isPresent() && error.getStop().isPresent())
            .map(error -> new Diagnostic(
                new Range(
                    tokenToPosition(error.getStart().get(), false),
                    tokenToPosition(error.getStop().get(), true)
                ),
                error.getMessage() + (error.getCause() != null ? ": " + error.getCause().getMessage() : ""),
                DiagnosticSeverity.Error,
                "gentzen"
            ))
            .toList();
    }

    public List<CompletionItem> getCompletions(CompletionParams params) {
        var before = getTextOnLineBefore(params.getPosition());
        var items = new ArrayList<CompletionItem>();
        var scope = namespace.toScope();

        if (before.contains(":")) {
            for (var type : scope.getDefinedTypes().values()) {
                var item = new CompletionItem(type.getName());
                item.setKind(CompletionItemKind.Class);
                item.setDocumentation("type");
                items.add(item);
            }
        } else {
            for (var rule : scope.getDefinedRules().entrySet()) {
                var item = new CompletionItem(rule.getKey());
                item.setKind(CompletionItemKind.Function);
                item.setDocumentation("rule");
                item.setDetail(": " + rule.getValue().toGentzen());
                items.add(item);
            }
        }

        return items;
    }

    public Hover getHover(HoverParams params) {
        var word = getWordAt(params.getPosition());
        if (word.isEmpty()) {
            return null;
        }
        var scope = namespace.toScope();
        var list = new ArrayList<Either<String, MarkedString>>();
        var resolvedRule = scope.resolveRule(word.get().text());
        if (resolvedRule.isPresent()) {
            list.add(Either.forLeft(word.get().text() + " : " + resolvedRule.get().toGentzen()));
        }
        var resolvedType = scope.resolveType(word.get().text());
        if (resolvedType.isPresent()) {
            list.add(Either.forLeft(resolvedType.get().toGentzen()));
        }
        return new Hover(list, word.get().range());
    }

    public List<Either<SymbolInformation, DocumentSymbol>> getDocumentSymbols() {
        return Stream.concat(
            namespace.getDefinedRules().values()
                .stream()
                .map(rule -> new DocumentSymbol(
                    rule.instance().getName(),
                    SymbolKind.Function,
                    new Range(
                        tokenToPosition(rule.declDef().getStart(), false),
                        tokenToPosition(rule.declDef().getStop(), true)
                    ),
                    new Range(
                        tokenToPosition(rule.declDef().getStart(), false),
                        tokenToPosition(rule.declDef().getStop(), true)
                    ),
                    rule.instance().getSignature().toGentzen()
                )),
            namespace.getDefinedTypes().entrySet()
                .stream()
                .map(entry -> new DocumentSymbol(
                    entry.getKey(),
                    SymbolKind.Class,
                    new Range(
                        tokenToPosition(entry.getValue().declDef().getStart(), false),
                        tokenToPosition(entry.getValue().declDef().getStop(), true)
                    ),
                    new Range(
                        tokenToPosition(entry.getValue().declDef().getStart(), false),
                        tokenToPosition(entry.getValue().declDef().getStop(), true)
                    ),
                    "type"
                ))
        ).map(Either::<SymbolInformation, DocumentSymbol>forRight).toList();
    }

    private Position tokenToPosition(Token token, boolean stop) {
        return new Position(token.getLine()-1, token.getCharPositionInLine() + (stop ? token.getText().length() : 0));
    }

    private String getLine(Position position) {
        if (position.getLine() >= lines.length) {
            return "";
        } else {
            return lines[position.getLine()];
        }
    }

    private String getTextOnLineBefore(Position position) {
        var line = getLine(position);
        return line.substring(0, Math.min(position.getCharacter(), line.length()));
    }

    private static record Word(Range range, String text) {}

    private Optional<Word> getWordAt(Position position) {
        var wordRegex = Pattern.compile("[!\\-_=^`\\w]");
        var line = getLine(position);
        var matcher = wordRegex.matcher(line);
        while (matcher.find()) {
            if (matcher.start() <= position.getCharacter()
                && position.getCharacter() < matcher.end()) {
                var range = new Range(
                    new Position(position.getLine(), matcher.start()),
                    new Position(position.getLine(), matcher.end())
                );
                return Optional.of(new Word(range, matcher.group()));
            }
        }
        return Optional.empty();
    }
}
