package com.cchess.game.room;

import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveRequest {

    private Player player;

    private Position from;

    private Position to;

}