package com.cchess.game.room;

import com.cchess.game.cchess.GameState;
import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Board;
import com.cchess.game.exception.BadRequestException;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final RoomMapper roomMapper;

    public RoomDto addPlayerToRoom(UserDto userDto) {
        synchronized (rooms) {
            for (Room room : rooms.values()) {
                Set<UserDto> players = room.getPlayers();
                List<UserDto> playerList = new ArrayList<>(players);

                for (UserDto user : playerList) {
                    if (user.getUsername().equals(userDto.getUsername())) {
                        playerList.remove(user);
                        playerList.add(userDto);
                        return roomMapper.toDto(room);
                    }
                }
                if (players.size() < 2) {
                    players.add(userDto);
                    return roomMapper.toDto(room);
                }
            }
        }

        Room room = createRoom(userDto);
        room.getPlayers().add(userDto);

        synchronized (rooms) {
            rooms.put(room.getId(), room);
        }
        return roomMapper.toDto(room);
    }

    public void start(String roomId, String name) {
        Room room = findRoomById(roomId);

        Set<UserDto> players = room.getPlayers();
        Pair<UserDto, UserDto> playersPair = Pair.of(players.iterator().next(), players.iterator().next());

        Random random = new Random();
        if (random.nextInt(10) % 2 == 0) {
            Player redPlayer = Player.builder()
                    .username(playersPair.getFirst().getUsername())
                    .isRed(true)
                    .build();
            Player blackPlayer = Player.builder()
                    .username(playersPair.getSecond().getUsername())
                    .isRed(false)
                    .build();

            GameState initialGameState = new GameState();
            initialGameState.setCurrentPlayer(redPlayer);
            initialGameState.setOtherPlayer(blackPlayer);

            log.info("Game started!");
        }

    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    private Room createRoom(UserDto userDto) {
        return Room.builder()
                .id(UUID.randomUUID().toString())
                .name("Room " + rooms.size())
                .createdBy(userDto.getUsername())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(RoomStatus.OPEN)
                .gameState(null)
                .players(new HashSet<>())
                .build();
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public Room findRoomById(String roomId) {
        synchronized (rooms) {
            Room room = rooms.get(roomId);
            if (room == null) throw new BadRequestException("Room not found");

            return room;
        }
    }
}
