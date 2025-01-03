package com.cchess.game.room;

import lombok.Getter;

@Getter
public enum RoomStatus {
    OPEN(0),
    BEGINNING(1),
    PLAYING(2),
    END(3);

    final int code;

    RoomStatus(int code) {
        this.code = code;
    }

}
