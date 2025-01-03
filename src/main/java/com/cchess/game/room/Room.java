package com.cchess.game.room;

import com.alibaba.fastjson2.annotation.JSONField;
import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Room {

    private String id;

    private String name;

    @JSONField(serialize = false)
    private String password;

    private Long createdBy;

    private LocalDateTime createdAt;

    @JSONField(serialize = false)
    private LocalDateTime updatedAt;

    private RoomStatus status = RoomStatus.OPEN;

    private Player player1;
    private Player player2;

    private Player currentPlayer;

    private Board board = new Board();

    public boolean isFull() {
        return player1 != null && player2 != null;
    }

    public Player getCurrentPlayer() {
        return (status == RoomStatus.PLAYING && player1 != null && player1.getIsRed() ? player1 : player2);
    }

}
