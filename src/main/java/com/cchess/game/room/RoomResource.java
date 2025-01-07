package com.cchess.game.room;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/room")
public interface RoomResource {

    @GetMapping(value = "/join")
    @ResponseStatus(HttpStatus.OK)
    RoomDto joinRoom();

}
