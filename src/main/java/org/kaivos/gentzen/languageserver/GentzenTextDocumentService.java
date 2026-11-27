package org.kaivos.gentzen.languageserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentDiagnosticParams;
import org.eclipse.lsp4j.DocumentDiagnosticReport;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.InsertTextFormat;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;

public class GentzenTextDocumentService implements TextDocumentService {

    private static final Map<String, String> SNIPPETS = Map.of(
        "main",
        "main : () / (\\$1(iosys) |- \\$2(++));\nmain = [io, done] $0;"
    );

    private final Map<String, GentzenDocument> documents =
        new ConcurrentHashMap<>();

    private static final GentzenDocument EMPTY = new GentzenDocument(
        "_empty_",
        ""
    );
    private LanguageClient client;

    public void setClient(LanguageClient client) {
        this.client = client;
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        update(
            params.getTextDocument().getUri(),
            params.getTextDocument().getText()
        );
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        List<TextDocumentContentChangeEvent> changes =
            params.getContentChanges();
        if (!changes.isEmpty()) {
            update(
                params.getTextDocument().getUri(),
                changes.get(changes.size() - 1).getText()
            );
        }
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        documents.remove(params.getTextDocument().getUri());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        //update(params.getTextDocument().getUri(), params.getText());
    }

    private void update(String uri, String text) {
        documents.put(uri, new GentzenDocument(uri, text));
    }

    @Override
    public CompletableFuture<
        Either<List<CompletionItem>, CompletionList>
    > completion(CompletionParams params) {
        var items = new ArrayList<CompletionItem>();
        SNIPPETS.forEach((label, snippet) -> {
            CompletionItem item = new CompletionItem(label);
            item.setKind(CompletionItemKind.Snippet);
            item.setInsertText(snippet);
            item.setInsertTextFormat(InsertTextFormat.Snippet);
            item.setDetail("snippet");
            items.add(item);
        });
        var uri = params.getTextDocument().getUri();
        if (documents.containsKey(uri)) {
            items.addAll(documents.get(uri).getCompletions(params));
        }
        return CompletableFuture.completedFuture(Either.forLeft(items));
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        var uri = params.getTextDocument().getUri();
        if (documents.containsKey(uri)) {
            return CompletableFuture.completedFuture(
                documents.get(uri).getHover(params)
            );
        } else {
            return CompletableFuture.completedFuture(EMPTY.getHover(params));
        }
    }

    @Override
    public CompletableFuture<DocumentDiagnosticReport> diagnostic(
        DocumentDiagnosticParams params
    ) {
        var uri = params.getTextDocument().getUri();
        if (documents.containsKey(uri)) {
            return CompletableFuture.completedFuture(
                documents.get(uri).getDiagnosticReport()
            );
        } else {
            return CompletableFuture.completedFuture(
                EMPTY.getDiagnosticReport()
            );
        }
    }

    @Override
    public CompletableFuture<
        List<Either<SymbolInformation, DocumentSymbol>>
    > documentSymbol(DocumentSymbolParams params) {
        var uri = params.getTextDocument().getUri();
        if (documents.containsKey(uri)) {
            return CompletableFuture.completedFuture(
                documents.get(uri).getDocumentSymbols()
            );
        } else {
            return CompletableFuture.completedFuture(
                EMPTY.getDocumentSymbols()
            );
        }
    }
}
