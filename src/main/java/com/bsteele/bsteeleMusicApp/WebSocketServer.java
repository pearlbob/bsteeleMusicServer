package com.bsteele.bsteeleMusicApp;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright 2018 Robert Steele at bsteele.com
 */

/**
 * @author bob
 */
@ServerEndpoint(value = "/bsteeleMusic")
public class WebSocketServer {

    public WebSocketServer() {
        logger.log(Level.INFO, "WebSocketServer()");
        System.out.println("WebSocketServer()");
        logger.info("logger info()");

        logger.log(Level.SEVERE, "WebSocketServer on thread: " + Thread.currentThread().getId());
    }

    @OnOpen
    public void onOpen(final Session session) {
        logger.log(Level.INFO, "onOpen({0}) of {1}", new Object[]{peers.size(), session.getId()});
        peers.add(session);

        //  alert the newbie as to where everyone else is
        //  gives confirmation of the connection to the app
        if (lastMessage != null)
            session.getAsyncRemote().sendText(lastMessage);
    }

    @OnMessage
    public void onMessage(final String message, final Session session) {
        if (message == null || message.length() <= 0)
            return;
        lastMessage = message;

        //   fixme:  flip any message back to all registered peers
        for (final Session peer : peers) {
            if (peer != session)
                peer.getAsyncRemote().sendText(message);
        }

        logger.log(Level.FINER, "onMessage(\"{0}...\") to {1} by {2}", new Object[]{
                message.substring(0, Math.min(message.length(), 40)).replaceAll("\n", " "),
                peers.size(),
                Thread.currentThread().getId()});
    }

    @OnClose
    public void onClose(final Session session) {
        peers.remove(session);
        logger.log(Level.INFO, "onClose(" + session.getId() + "): of " + peers.size());
    }

    @OnError
    public void onError(final Session session, Throwable t) {
        logger.log(Level.SEVERE, "onError({0}) from {1}", new Object[]{t.getMessage(), session.getId()});
        //t.printStackTrace();
    }

    private static final Logger logger = Logger.getLogger(WebSocketServer.class.getName());
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<>());
    private String lastMessage = null;

    static {
        logger.setLevel(Level.FINE);
    }
}
