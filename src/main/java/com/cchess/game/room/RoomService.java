package com.cchess.game.room;

import com.cchess.game.cchess.Player;
import com.cchess.game.exception.BadRequestException;
import com.cchess.game.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RoomService {

    private final ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();
    
    public Room findRoomById(String roomId) {
        synchronized (rooms) {
            Room room = rooms.get(roomId);
            if (room == null) throw new BadRequestException("Room not found");

            return room;
        }
    }

    public Room addPlayerToRoom(UserDto userDto) {
        synchronized (rooms) {
            for (Room room : rooms.values()) {
                Set<UserDto> players = room.getPlayers();

                List<UserDto> playerList = new ArrayList<>(players);

                for(UserDto user : playerList) {
                    if (user.getUsername().equals(userDto.getUsername())) {
                        playerList.remove(user);
                        playerList.add(userDto);
                        return room;
                    }
                }
                if (players.size() < 2) {
                    players.add(userDto);
                    return room;
                }
            }
        }

        Room room = createRoom();
        room.getPlayers().add(userDto);

        synchronized (rooms) {
            rooms.put(room.getId(), room);
        }
        return room;
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    private Room createRoom() {
        String roomId = UUID.randomUUID().toString();

        return Room.builder()
                .id(roomId)
                .build();
    }

}
