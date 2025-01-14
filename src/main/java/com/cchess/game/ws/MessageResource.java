package com.cchess.game.ws;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.room.MoveRequest;
import com.cchess.game.user.UserDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;

@CrossOrigin(allowedHeaders = "*", origins = "*")
public interface MessageResource {

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/room/{roomId}/join")
    void join(@DestinationVariable String roomId,
              UserDto user
    );

    @MessageMapping("/room/{roomId}/move")
    Board makeMove(@DestinationVariable String roomId,
                   @Payload MoveRequest moveRequest,
                   @AuthenticationPrincipal Principal principal
    );

//    @MessageMapping("/room/{roomId}/start")
//    void start(@DestinationVariable String roomId,
//               @AuthenticationPrincipal Principal principal
//    );

//    @MessageMapping("/room/{roomId}/game/time-over")
//    void timeOver(@DestinationVariable String roomId,
//                  @Payload TimeOverMessage timeOverMessage
//    );

}
