package com.cchess.game.cchess.matches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameHistory {

    private String gameId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<Player> players;

    private String winnerUsername;

    private String loserUsername;

    private String firstPlayerUsername;

    private String secondPlayerUsername;

    private GameOverReason gameOverReason;

    private Boolean isDraw;

    public void setIdRandomly() {
        this.gameId = UUID.randomUUID().toString();
    }
}
