package com.cchess.game.room;

import com.cchess.game.cchess.GameState;
import com.cchess.game.cchess.Player;
import com.cchess.game.exception.BadRequestException;
import com.cchess.game.exception.NotFoundException;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();
    private final Map<String, RoomManager> games = new ConcurrentHashMap<>();
    private final RoomMapper roomMapper;

    public RoomDto addPlayerToRoom(UserDto userDto) {
        synchronized (roomMap) {
            for (Room room : roomMap.values()) {
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

        synchronized (roomMap) {
            roomMap.put(room.getId(), room);
        }

        synchronized (games) {
            games.put(room.getId(), new RoomManager(room));
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

    private Room createRoom(UserDto userDto) {
        return Room.builder()
                .id(UUID.randomUUID().toString())
                .name("Room " + roomMap.size())
                .createdBy(userDto.getUsername())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(RoomStatus.OPEN)
                .gameState(null)
                .players(new HashSet<>())
                .build();
    }

    public Room findRoomById(String roomId) {
        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            if (room == null) throw new NotFoundException("Room not found");

            return room;
        }
    }

    public RoomManager findRoomManagerByRoomId(String roomId) {
        synchronized (games) {
            RoomManager roomManager = games.get(roomId);
            if (roomManager == null) throw new NotFoundException("Room not found");

            return roomManager;
        }
    }

    public synchronized boolean removePlayerFromRoom(UserDto userDto, String roomId) {
        Room room = roomMap.get(roomId);

        GameState currentGameState = room.getGameState();
        if (currentGameState != null) {
            if (currentGameState.getCurrentPlayer().getUsername().equals(userDto.getUsername())) {
                currentGameState.setCurrentPlayer(null);
            } else if (currentGameState.getOtherPlayer().getUsername().equals(userDto.getUsername())) {
                currentGameState.setOtherPlayer(null);
            }
        }

        Set<UserDto> currentUsersInRoom = room.getPlayers();
        currentUsersInRoom.remove(userDto);

        room.setGameState(currentGameState);
        roomMap.put(roomId, room);
        return true;
    }

    public List<RoomDto> getAvailableRooms() {
        synchronized (roomMap) {
            List<RoomDto> availableRooms = new ArrayList<>();
            for (Room room : roomMap.values())
                availableRooms.add(roomMapper.toDto(room));
            return availableRooms;
        }
    }

    public synchronized RoomDto playerJoinRoom(UserDto userDto, String roomId) {
        Room room = roomMap.get(roomId);
        if (room == null) throw new NotFoundException("Room not found");

        Set<UserDto> players = room.getPlayers();
        if (players.size() == 2) throw new BadRequestException("Room is full");

        players.add(userDto);
        return roomMapper.toDto(room);
    }
}
