package com.cchess.game.ws;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.cchess.matches.DrawResponse;
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

    public void notifyGameStarted(String roomId) {
        sendMessage("/room/" + roomId, "GAME STARTED.");
    }

    public void notifyNewMove(String roomId, Position from, Position to) {
        String oldPosition = from.toString();
        String newPosition = to.toString();
        String message = "New move from " + oldPosition + " to " + newPosition;

        sendMessage("/room/" + roomId, message);
    }

    public void notifyNewBoard(String roomId, Board board) {
        sendMessage("/room/" + roomId, board.toString());
    }

    public void notifyPlayerLeave(String roomId, UserDto userDto) {
        sendMessage("/room/" + roomId, userDto.getUsername() + " left the room.");
    }

    public void notifyDrawResponse(String roomId, DrawResponse drawResponse) {
        sendMessage("/room/" + roomId, drawResponse);
    }

    public void notifySurrender(String roomId, String surrenderUsername) {
        sendMessage("/room/" + roomId, surrenderUsername + " surrendered.");
    }

    public void notifyGameCountdown(String roomId, String formattedTime) {
        sendMessage("/room/" + roomId, formattedTime);
    }

    public void notifyGameEnded(String roomId) {
        sendMessage("/room/" + roomId, "GAME ENDED.");
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
