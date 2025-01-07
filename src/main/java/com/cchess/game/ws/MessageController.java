package com.cchess.game.ws;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.room.MoveRequest;
import com.cchess.game.room.RoomManager;
import com.cchess.game.room.RoomService;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageResource {

    private final Map<String, RoomManager> game = new ConcurrentHashMap<>();
    private final RoomService roomService;

    private final MessageService messageService;

    @Override
    public void join(String roomId, UserDto userDto) {
        messageService.notifyPlayerJoin(roomId, userDto);
    }

    @Override
    public Board makeMove(String roomId, MoveRequest moveRequest, Principal principal) {

        return null;
    }

    @Override
    public void start(String roomId, Principal principal) {
        roomService.start(roomId, principal.getName());
    }

}
