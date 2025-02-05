package com.cchess.game.cchess.matches;

import com.cchess.game.cchess.base.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameState {

    @Builder.Default
    private Board board = new Board();

    private Player currentPlayer;

    private Player otherPlayer;

    public Player findCurrentPlayerByUsername(String username) {
        if (currentPlayer.getUsername().equals(username)) {
            return currentPlayer;
        }
        return null;
    }

    public Boolean updatePlayerReadyStatus(String username) {
        if (currentPlayer.getUsername().equals(username)) {
            Player currentPlayer = getCurrentPlayer();
            currentPlayer.setIsReady(!currentPlayer.getIsReady());
            return currentPlayer.getIsReady();
        } else {
            Player otherPlayer = getOtherPlayer();
            otherPlayer.setIsReady(!otherPlayer.getIsReady());
            return otherPlayer.getIsReady();
        }
    }

    public Boolean bothPlayersReady() {
        return currentPlayer.getIsReady() && otherPlayer.getIsReady();
    }
}
