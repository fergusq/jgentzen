package org.kaivos.gentzen.languageserver;

import org.eclipse.lsp4j.services.LanguageServer;

public class LanguageServer implements LanguageServer {

    private final List<LanguageClient> clients = new CopyOnWriteArrayList<>();
    private TextDocumentService textService;
    private WorkspaceService workspaceService;

    public MyLanguageServerImpl() {
        //initialize textDocumentService and workspaceService
    }

    public CompletableFuture<InitializeResult> initialize(
        InitializeParams params
    ) {
        // TODO Auto-generated method stub
        return null;
    }

    public CompletableFuture<Object> shutdown() {
        // TODO Auto-generated method stub
        return null;
    }

    public void exit() {
        // TODO Auto-generated method stub
    }

    public TextDocumentService getTextDocumentService() {
        // TODO Auto-generated method stub
        return null;
    }

    public WorkspaceService getWorkspaceService() {
        // TODO Auto-generated method stub
        return null;
    }

    public Runnable setRemoteProxy(LanguageClient languageClient) {
        this.clients.add(languageClient);
        return () -> this.clients.remove(languageClient);
    }

    public List<LanguageClient> getClientList() {
        return clients;
    }
}
