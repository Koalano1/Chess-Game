package com.cchess.game.room;

import com.cchess.game.exception.BadRequestException;
import com.cchess.game.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final RoomMapper roomMapper;

    public Room findRoomById(String roomId) {
        synchronized (rooms) {
            Room room = rooms.get(roomId);
            if (room == null) throw new BadRequestException("Room not found");

            return room;
        }
    }

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
                .currentPlayer(null)
                .players(new HashSet<>())
                .build();
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

}
