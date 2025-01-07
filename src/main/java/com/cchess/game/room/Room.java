package com.cchess.game.room;

import com.alibaba.fastjson2.annotation.JSONField;
import com.cchess.game.cchess.GameState;
import com.cchess.game.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private GameState gameState;

    public boolean isFull() {
        return players.size() == 2;
    }

}
