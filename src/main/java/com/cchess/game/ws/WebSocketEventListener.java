package com.cchess.game.ws;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.Objects;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void onApplicationEvent(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.debug("Received a new web socket connection form userId: {}", userPrincipal.getName());
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String destination = headerAccessor.getDestination();
        String username = Objects.requireNonNull(event.getUser()).getName();

        logger.debug("Username: {} have just subscribe to {}", username, destination);
    }

    @EventListener
    public void handleUnSubscribe(SessionUnsubscribeEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.debug("UserId {} have just unsubscribe", userPrincipal.getName());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.debug("UserId {} had disconnected!", userPrincipal.getName());
    }
}
