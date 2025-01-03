package com.cchess.game.room;

import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Board;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/room")
public interface RoomResource {

    @MessageMapping("/join")
    @SendTo("/topic/room/{roomId}")
    Room join(@DestinationVariable String roomId,
              Player player
    );

    @MessageMapping("/move")
    @SendTo("/topic/room/{roomId}")
    Board makeMove(@DestinationVariable String roomId,
                          @Payload MoveRequest moveRequest

    );

    @PostMapping("/create")
    Room createRoom(String name,
                    String password,
                    Long createdBy
    );

}
