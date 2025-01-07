package com.cchess.game.cchess;

import com.cchess.game.cchess.base.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState {

    private Board board = new Board();

    private Player currentPlayer;

    private Player otherPlayer;

}
