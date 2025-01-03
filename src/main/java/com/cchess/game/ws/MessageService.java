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
        sendMessage("/topic/room/" + roomId, user);
    }

    private void sendMessage(String destination, Object payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload);
            log.info("Message sent to {}", destination);
        } catch (Exception e) {
            log.error("Failed to send message to {}", destination);
        }
    }

}
