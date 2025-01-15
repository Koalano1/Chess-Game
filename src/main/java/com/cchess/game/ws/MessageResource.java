package com.cchess.game.ws;

import com.cchess.game.user.UserDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(allowedHeaders = "*", origins = "*")
public interface MessageResource {

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/room/{roomId}/join")
    void join(@DestinationVariable String roomId,
              UserDto user
    );

}
