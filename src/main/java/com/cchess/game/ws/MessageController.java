package com.cchess.game.ws;

import com.cchess.game.room.RoomService;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageResource {

    private final RoomService roomService;
    private final MessageService messageService;

    @Override
    public void join(String roomId, UserDto userDto) {
        messageService.notifyPlayerJoin(roomId, userDto);
    }

}
