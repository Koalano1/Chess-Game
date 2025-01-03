package com.cchess.game.room;

import com.cchess.game.model.entities.User;
import com.cchess.game.user.UserDto;
import com.cchess.game.user.UserService;
import com.cchess.game.user.mapper.UserMapper;
import com.cchess.game.ws.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final MessageService messageService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoomService roomService;

    @GetMapping(value = "/join")
    @ResponseStatus(HttpStatus.OK)
    public Room joinRoom(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        UserDto userDto = userMapper.toDto(user);

        Room room = roomService.addPlayerToRoom(userDto);
        messageService.notifyPlayerJoin(room.getId(), userDto);

        return room;
    }
}
