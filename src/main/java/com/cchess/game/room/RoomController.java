package com.cchess.game.room;

import com.cchess.game.cchess.matches.GameService;
import com.cchess.game.user.User;
import com.cchess.game.user.UserDto;
import com.cchess.game.user.UserMapper;
import com.cchess.game.user.UserService;
import com.cchess.game.cchess.matches.DrawRequest;
import com.cchess.game.cchess.matches.DrawResponse;
import com.cchess.game.ws.MessageService;
import com.cchess.game.ws.TimeOverMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomResource {

    private final MessageService messageService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoomService roomService;
    private final GameService gameService;

    @Override
    public RoomDto joinRoom() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserDto userDto = userMapper.toDto(user);

        RoomDto roomDto = roomService.addPlayerToRoom(userDto);
        messageService.notifyPlayerJoin(roomDto.getRoomId(), userDto);

        return roomDto;
    }

    @Override
    public Boolean leave(String roomId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserDto userDto = userMapper.toDto(user);

        boolean isLeave = roomService.removePlayerFromRoom(userDto, roomId);
        if (isLeave) {
            messageService.notifyPlayerLeave(roomId, userDto);
        }

        return isLeave;
    }

    @Override
    public RoomDto joinSpecificRoom(String roomId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserDto userDto = userMapper.toDto(user);

        RoomDto roomDto = roomService.playerJoinRoom(userDto, roomId);
        messageService.notifyPlayerJoin(roomId, userDto);

        return roomDto;
    }

    @Override
    public List<RoomDto> availableRooms() {
        return roomService.getAvailableRooms();
    }

    @Override
    public void ready(String roomId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        roomService.updatePlayerReadyStatus(username, roomId);
    }

    @Override
    public String makeMove(String roomId, MoveRequest moveRequest) {
        return gameService.makeMove(roomId, moveRequest);
    }

    @Override
    public DrawRequest handleDrawRequest(String roomId) {
        DrawRequest drawRequest = new DrawRequest();
        drawRequest.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return drawRequest;
    }

    @Override
    public void handleDrawResponse(String roomId, DrawResponse drawResponse) {
        boolean isAccept = drawResponse.getIsAgree();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        roomService.handleDrawResponse(roomId, isAccept, username);
    }

    @Override
    public void handlerSurrenderRequest(String roomId) {
        String loserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        roomService.handleSurrenderRequest(roomId, loserUsername);
    }

    @Override
    public void handleTimeOver(String roomId, TimeOverMessage timeOverMessage) {
        roomService.handleTimeOver(roomId, timeOverMessage.getUsername());
    }

}
