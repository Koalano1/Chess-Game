package com.cchess.game.room;

import com.alibaba.fastjson2.annotation.JSONField;
import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Board;
import com.cchess.game.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private String id;

    private String name;

//    @JSONField(serialize = false)
//    private String password;

    private String createdBy;

    private LocalDateTime createdAt;

    @JSONField(serialize = false)
    private LocalDateTime updatedAt;

    private RoomStatus status = RoomStatus.OPEN;

    private Set<UserDto> players;

    private Player currentPlayer;

    private Board board = new Board();

    public boolean isFull() {
        return players.size() == 2;
    }

}
