package com.denghb.demo.client;

import javax.websocket.*;

@ClientEndpoint()
public class Handler {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen:" + session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Client onMessage: " + message);
    }

    @OnClose
    public void onClose() {
        System.out.println("onClose");
    }
}