package com.denghb.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class MyWebSocketHandler implements WebSocketHandler {

    private Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);

    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("afterConnectionEstablished");

        log.debug("\nsession:{}\nHandshakeHeaders:{}\nRemoteAddress:{}", session, session.getHandshakeHeaders(), session.getRemoteAddress());

        sessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("handleMessage\nmessage:{}", message);

        log.debug("\nPayload:{}", message.getPayload());

        session.sendMessage(new TextMessage("Hello WebSocket Client!"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.debug("handleTransportError");

        sessions.remove(session.getId());

        log.error(exception.getMessage(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("afterConnectionClosed");

        sessions.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        log.debug("supportsPartialMessages");
        return false;
    }
}
