package com.cchess.game.room;

import com.cchess.game.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room findRoomById(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new BadRequestException("Room not found");
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

}
