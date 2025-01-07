package com.cchess.game.ws;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.room.MoveRequest;
import com.cchess.game.room.Room;
import com.cchess.game.user.UserDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

public interface MessageResource {

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/room/{roomId}/join")
    void join(@DestinationVariable String roomId,
              UserDto user
    );

    @MessageMapping("/room/{roomId}/move")
    Board makeMove(@DestinationVariable String roomId,
                   @Payload MoveRequest moveRequest,
                   Principal principal
    );

}
