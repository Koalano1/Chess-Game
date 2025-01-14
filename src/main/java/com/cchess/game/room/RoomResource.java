package com.cchess.game.room;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.ws.DrawRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/room")
public interface RoomResource {

    @GetMapping(value = "/join")
    @ResponseStatus(HttpStatus.OK)
    RoomDto joinRoom();

    @GetMapping(value = "/{roomId}/join")
    @ResponseStatus(HttpStatus.OK)
    RoomDto joinSpecificRoom(@PathVariable String roomId);

    @GetMapping(value = "/{roomId}/leave")
    @ResponseStatus(HttpStatus.OK)
    Boolean leave(@PathVariable String roomId);

    @GetMapping(value = "/available")
    @ResponseStatus(HttpStatus.OK)
    List<RoomDto> availableRooms();

    @GetMapping(value = "/{roomId}/ready")
    @ResponseStatus(HttpStatus.OK)
    void ready(@PathVariable String roomId);

    @PostMapping(value = "/{roomId}/move")
    @ResponseStatus(HttpStatus.OK)
    Board makeMove(@PathVariable String roomId, @RequestBody MoveRequest moveRequest);

    @GetMapping(value = "/{roomId}/draw-request")
    @ResponseStatus(HttpStatus.OK)
    DrawRequest handleDrawRequest(@PathVariable String roomId);

//    @GetMapping(value = "/{roomId}/draw-response")
//    @ResponseStatus(HttpStatus.OK)
//    void handleDrawResponse(@PathVariable String roomId, boolean isAccept);
//
//    @GetMapping(value = "/{roomId}/surrender-request")
//    @ResponseStatus(HttpStatus.OK)
//    void handlerSurrenderRequest(@PathVariable String roomId);
//
//    @GetMapping(value = "/{roomId}/time-over")
//    @ResponseStatus(HttpStatus.OK)
//    void handleTimeOver(@PathVariable String roomId, TimeOverMessage timeOverMessage);

}
