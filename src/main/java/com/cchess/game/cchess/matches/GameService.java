package com.cchess.game.cchess.matches;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.exception.InvalidMoveException;
import com.cchess.game.exception.NotFoundException;
import com.cchess.game.room.MoveRequest;
import com.cchess.game.room.RoomManager;
import com.cchess.game.room.RoomService;
import com.cchess.game.user.UserDto;
import com.cchess.game.ws.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final MessageService messageService;
    private final RoomService roomService;

    public String makeMove(String roomId, MoveRequest moveRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        RoomManager roomManager = roomService.findRoomManagerByRoomId(roomId);
        if (roomManager == null || roomManager.room() == null) {
            throw new NotFoundException("Room not found");
        }

        UserDto userDto = roomManager.room().getPlayers().stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Player not found"));

        GameState gameState = roomManager.room().getGameState();
        Player player = gameState.findCurrentPlayerByUsername(userDto.getUsername());
        Player nextPlayer = gameState.getOtherPlayer();

        Position from = moveRequest.getFrom();
        Position to = moveRequest.getTo();

        boolean moveMade = roomManager.makeMove(player, from, to);

        if (!moveMade) {
            throw new InvalidMoveException("Invalid move");
        }

        messageService.notifyNewMove(roomId, from, to);

        roomService.startAndStopTimer(player.getUsername(), nextPlayer.getUsername(), roomId);

        Board board = roomManager.room().getGameState().getBoard();
        messageService.notifyNewBoard(roomId, board);
        return board.toString();
    }
}
