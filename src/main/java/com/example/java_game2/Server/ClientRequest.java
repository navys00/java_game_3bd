package com.example.java_game2.Server;

public class ClientRequest {

    final ClientActions clientActions;
    public ClientRequest(ClientActions clientActions) {
        this.clientActions = clientActions;
    }
    public ClientActions getClientActions() {
        return clientActions;
    }
}
