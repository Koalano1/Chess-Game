package com.cchess.game.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/room")
public interface RoomResource {

    @GetMapping(value = "/join")
    @ResponseStatus(HttpStatus.OK)
    RoomDto joinRoom();

    @GetMapping(value = "/{roomId}/leave")
    @ResponseStatus(HttpStatus.OK)
    Boolean leave(@PathVariable String roomId);

    @GetMapping(value = "/available")
    @ResponseStatus(HttpStatus.OK)
    List<RoomDto> availableRooms();

}
