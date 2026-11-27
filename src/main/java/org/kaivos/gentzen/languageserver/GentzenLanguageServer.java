package org.kaivos.gentzen.languageserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.DiagnosticRegistrationOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

public class GentzenLanguageServer implements LanguageServer {

    private GentzenTextDocumentService textDocumentService;
    private GentzenWorkspaceService workspaceService;

    public GentzenLanguageServer() {
        textDocumentService = new GentzenTextDocumentService();
        workspaceService = new GentzenWorkspaceService();
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(
        InitializeParams params
    ) {
        ServerCapabilities capabilities = new ServerCapabilities();
        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
        capabilities.setCompletionProvider(new CompletionOptions());
        capabilities.setDiagnosticProvider(new DiagnosticRegistrationOptions());
        capabilities.setDocumentSymbolProvider(true);
        capabilities.setHoverProvider(true);
        return CompletableFuture.completedFuture(
            new InitializeResult(capabilities)
        );
    }

    public void connect(LanguageClient client) {
        textDocumentService.setClient(client);
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return CompletableFuture.completedFuture(null);
    }

    public void exit() {}

    @Override
    public TextDocumentService getTextDocumentService() {
        return textDocumentService;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return workspaceService;
    }

    public static void main(String[] args)
        throws InterruptedException, ExecutionException
    {
        startServer(System.in, System.out);
    }

    public static void startServer(InputStream in, OutputStream out)
        throws InterruptedException, ExecutionException
    {
        var myServer = new GentzenLanguageServer();

        Launcher<LanguageClient> l = LSPLauncher.createServerLauncher(
            myServer,
            in,
            out
        );
        Future<?> startListening = l.startListening();
        myServer.connect(l.getRemoteProxy());
        startListening.get();
    }
}
