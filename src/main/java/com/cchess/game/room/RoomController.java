package com.cchess.game.room;

import com.cchess.game.cchess.GameManager;
import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Board;
import com.cchess.game.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomResource {

    private final Map<String, GameManager> game = new ConcurrentHashMap<>();
    private final RoomService roomService;

    @Override
    public Room join(String roomId, Player player) {
        Room room = roomService.findRoomById(roomId);

        if (room == null || room.isFull()) {
            throw new BadRequestException("Room is full");
        }

        if (room.getPlayer1() == null) {
            room.setPlayer1(player);
        } else {
            room.setPlayer2(player);
            room.setStatus(RoomStatus.PLAYING);
            game.put(roomId, new GameManager(room));
        }
        return room;
    }

    @Override
    public Board makeMove(String roomId, MoveRequest moveRequest) {
        GameManager gameManager = game.get(roomId);
        if (gameManager == null) {
            throw new BadRequestException("Room not found");
        }

        boolean success = gameManager.makeMove(moveRequest.getPlayer(), moveRequest.getFrom(), moveRequest.getTo());
        if (!success) {
            throw new BadRequestException("Invalid move");
        }

        return gameManager.room().getBoard();
    }

    @Override
    public Room createRoom(String name, String password, Long createdBy) {
        Room room = new Room();
        room.setId(String.valueOf(System.currentTimeMillis()));
        room.setName(name);
        room.setPassword(password);

        room.setCreatedBy(createdBy);
        room.setCreatedAt(LocalDateTime.now());

        roomService.addRoom(room);
        return room;
    }

}
