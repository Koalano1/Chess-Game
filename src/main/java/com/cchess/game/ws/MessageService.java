package com.cchess.game.ws;

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

    public void notifyPlayerJoin(String roomId, UserDto user) {
        sendMessage("/room/" + roomId, user);
    }

    public void notifyPlayerLeave(String roomId, UserDto userDto) {
        sendMessage("/room/" + roomId + "/leave", userDto);
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
