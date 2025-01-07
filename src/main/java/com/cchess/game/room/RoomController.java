package com.cchess.game.room;

import com.cchess.game.model.entities.User;
import com.cchess.game.user.UserDto;
import com.cchess.game.user.UserMapper;
import com.cchess.game.user.UserService;
import com.cchess.game.ws.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomResource {

    private final MessageService messageService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoomService roomService;

    @Override
    public RoomDto joinRoom() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserDto userDto = userMapper.toDto(user);

        RoomDto roomDto = roomService.addPlayerToRoom(userDto);
        messageService.notifyPlayerJoin(roomDto.getRoomId(), userDto);

        return roomDto;
    }

}
