package com.cchess.game.ws;

import com.cchess.game.cchess.GameState;
import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.exception.InvalidMoveException;
import com.cchess.game.exception.NotFoundException;
import com.cchess.game.room.MoveRequest;
import com.cchess.game.room.RoomManager;
import com.cchess.game.room.RoomService;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageResource {

    private final RoomService roomService;
    private final MessageService messageService;

    @Override
    public void join(String roomId, UserDto userDto) {
        messageService.notifyPlayerJoin(roomId, userDto);
    }

//    public Board makeMove(String roomId, MoveRequest moveRequest, Principal principal) {
//        RoomManager roomManager = roomService.findRoomManagerByRoomId(roomId);
//        if (roomManager == null || roomManager.room() == null) {
//            throw new NotFoundException("Room not found");
//        }
//
//        UserDto userDto = roomManager.room().getPlayers().stream()
//                .filter(p -> p.getUsername().equals(principal.getName()))
//                .findFirst()
//                .orElseThrow(() -> new NotFoundException("Player not found"));
//
//        GameState gameState = roomManager.room().getGameState();
//        Player player = gameState.findCurrentPlayerByUsername(userDto.getUsername());
//
//        Position from = Position.builder()
//                .row(moveRequest.getFrom().getRow())
//                .col(moveRequest.getFrom().getCol())
//                .build();
//        Position to = Position.builder()
//                .row(moveRequest.getTo().getRow())
//                .col(moveRequest.getTo().getCol())
//                .build();
//
//        boolean moveMade = roomManager.makeMove(player, from, to);
//
//        if (!moveMade) {
//            throw new InvalidMoveException("Invalid move");
//        }
//
//        return roomManager.room().getGameState().getBoard();
//    }

//    @Override
//    public void start(String roomId, Principal principal) {
//        roomService.start(roomId, principal.getName());
//    }

//    @Override
//    public void timeOver(String roomId, TimeOverMessage timeOverMessage) {
//        roomManager.handlerTimeOver(roomId, timeOverMessage.getUsername());
//    }

}
