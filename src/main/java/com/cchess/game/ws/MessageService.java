package com.cchess.game.ws;

import com.cchess.game.cchess.base.Position;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyPlayerJoin(String roomId, UserDto userDto) {
        sendMessage("/room/" + roomId, userDto.getUsername() + " joined the room.");
    }

    public void notifyPlayerReady(String roomId, String username, Boolean newStatus) {
        sendMessage("/room/" + roomId, username + " is " + (newStatus ? "ready" : "not ready"));
    }

    public void notifyCountdown(String roomId, int timeLeft) {
        sendMessage("/room/" + roomId, timeLeft);
    }

    public void notifyCountdownStopped(String roomId) {
        sendMessage("/room/" + roomId, "Countdown stopped.");
    }

    public void notifyGameStarted(String roomId) {
        sendMessage("/room/" + roomId, "GAME STARTED.");
    }

    public void notifyNewMove(String roomId, Position from, Position to) {
        String oldPosition = from.toString();
        String newPosition = to.toString();
        String message = "New move from " + oldPosition + " to " + newPosition;

        sendMessage("/room/" + roomId, message);
    }

    public void notifyPlayerLeave(String roomId, UserDto userDto) {
        sendMessage("/room/" + roomId, userDto.getUsername() + " left the room.");
    }

    private void sendMessage(String destination, Object payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload);
            log.debug("Message sent to {}", destination);
        } catch (Exception e) {
            log.error("Failed to send message to {}. Exception: {}", destination, e.getMessage(), e);
            throw new RuntimeException("Error sending message to " + destination, e);
        }
    }
}
