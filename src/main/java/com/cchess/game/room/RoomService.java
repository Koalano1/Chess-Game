package com.cchess.game.room;

import com.cchess.game.cchess.matches.*;
import com.cchess.game.exception.BadRequestException;
import com.cchess.game.exception.NotFoundException;
import com.cchess.game.user.UserDto;
import com.cchess.game.ws.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();
    private final Map<String, RoomManager> games = new ConcurrentHashMap<>();

    private final RoomMapper roomMapper;
    private final MessageService messageService;
    private final MatchService matchService;
    private final GameHistoryCache gameHistoryCache;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<String, Runnable> countdownTasks = new ConcurrentHashMap<>();

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
                    room.setPlayers(players);

                    GameState currentGameState = room.getGameState();
                    currentGameState.setOtherPlayer(
                            Player.builder()
                                    .username(userDto.getUsername())
                                    .build());

                    room.setGameState(currentGameState);
                    roomMap.put(room.getId(), room);

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

    public void updatePlayerReadyStatus(String username, String roomId) {
        Room room = roomMap.get(roomId);
        GameState gameState = room.getGameState();

        Boolean newStatus = gameState.updatePlayerReadyStatus(username);
        messageService.notifyPlayerReady(roomId, username, newStatus);

        if (gameState.bothPlayersReady()) {
            room.setStatus(RoomStatus.BEGINNING);
            startCountdown(roomId);
        } else {
            stopCountDown(roomId);
        }
    }

    private void startCountdown(String roomId) {
        stopCountDown(roomId);

        Runnable countdownTask = new Runnable() {
            private int timeLeft = 10;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    messageService.notifyCountdown(roomId, timeLeft);
                    timeLeft--;
                } else {
                    stopCountDown(roomId);
                    start(roomId);
                }
            }
        };

//        scheduler.scheduleAtFixedRate(countdownTask, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
//        countdownTasks.put(roomId, countdownTask);
        ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(countdownTask, 0, 1, TimeUnit.SECONDS);
        countdownTasks.put(roomId, () -> scheduledTask.cancel(false));
    }

    private void stopCountDown(String roomId) {
        Runnable task = countdownTasks.get(roomId);
        if (task != null) {
//            scheduler.shutdownNow();
            task.run();
            countdownTasks.remove(roomId);
//            messageService.notifyCountdownStopped(roomId);
        }
    }

    public void start(String roomId) {
        Room room = findRoomById(roomId);

        GameState gameState = room.getGameState();

        Random random = new Random();
        if (random.nextInt(10) % 2 == 0) {

            Player currentPlayer = gameState.getCurrentPlayer();
            currentPlayer.setIsRed(true);
            Player otherPlayer = gameState.getOtherPlayer();
            otherPlayer.setIsRed(false);
        } else {
            Player otherPlayer = gameState.getOtherPlayer();
            otherPlayer.setIsRed(true);
            Player oldCurrentPlayer = gameState.getCurrentPlayer();
            oldCurrentPlayer.setIsRed(false);

            gameState.setCurrentPlayer(otherPlayer);
            gameState.setOtherPlayer(oldCurrentPlayer);
        }

        messageService.notifyGameStarted(roomId);

        // Create new gamer history
        gameHistoryCache.addNewGameHistoryPreMatch(roomId, gameState.getCurrentPlayer(), gameState.getOtherPlayer());

        room.setStatus(RoomStatus.PLAYING);
    }

    private Room createRoom(UserDto userDto) {
        Player player = Player.builder().username(userDto.getUsername()).build();
        GameState initialGameState = GameState.builder()
                .currentPlayer(player)
                .otherPlayer(null)
                .build();
        return Room.builder()
                .id(UUID.randomUUID().toString())
                .name("Room " + roomMap.size())
                .createdBy(userDto.getUsername())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(RoomStatus.OPEN)
                .gameState(initialGameState)
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

        if (currentUsersInRoom.isEmpty()) {
            roomMap.remove(roomId);
            return true;
        }

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

    public synchronized void handleDrawResponse(String roomId, Boolean isAccept, String username) {
        DrawResponse drawResponse = DrawResponse.builder()
                .username(username)
                .isAgree(isAccept)
                .build();
        messageService.notifyDrawResponse(roomId, drawResponse);

        // Update game history
        gameHistoryCache.updateGameHistoryPostMatch(roomId, null, null, GameOverReason.DRAW, true);
        matchService.createAndSaveMatch(roomId);

        if (isAccept) {
            Room roomReset = roomMap.get(roomId);
            roomReset.setUpdatedAt(LocalDateTime.now());
            roomReset.setStatus(RoomStatus.OPEN);
            roomReset.setPlayers(new HashSet<>());
            roomReset.setGameState(
                    GameState.builder()
                            .currentPlayer(null)
                            .otherPlayer(null)
                            .build()
            );

            roomMap.put(roomId, roomReset);
        }
    }

    public void handleSurrenderRequest(String roomId, String loserUsername) {
        Room room = roomMap.get(roomId);
        room.setPlayers(new HashSet<>());

    }

    public void handleTimeOver(String roomId, String loserUsername) {
    }
}
