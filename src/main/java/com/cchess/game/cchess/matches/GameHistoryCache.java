package com.cchess.game.cchess.matches;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GameHistoryCache {

    private final Map<String, GameHistory> gameHistories = new ConcurrentHashMap<>();

    public synchronized GameHistory getGameHistory(String roomId) {
        return gameHistories.get(roomId);
    }

    public synchronized void addNewGameHistoryPreMatch(String roomId, Player firstPlayer, Player secondPlayer) {
        GameHistory gameHistory = new GameHistory();
        gameHistory.setIdRandomly();

        gameHistory.setStartTime(LocalDateTime.now());
        gameHistory.setPlayers(Set.of(firstPlayer, secondPlayer));
        gameHistory.setFirstPlayerUsername(firstPlayer.getUsername());
        gameHistory.setSecondPlayerUsername(secondPlayer.getUsername());

        gameHistories.put(roomId, gameHistory);
    }

    public synchronized void updateGameHistoryPostMatch(String roomId, Player winner, Player loser, GameOverReason gameOverReason, Boolean isDraw) {

        GameHistory existingGameHistory = gameHistories.get(roomId);

        if (existingGameHistory == null) {
            existingGameHistory = new GameHistory();
            existingGameHistory.setGameId(roomId);
            existingGameHistory.setStartTime(LocalDateTime.now());
            gameHistories.put(roomId, existingGameHistory);
        }

        existingGameHistory.setEndTime(LocalDateTime.now());
        if (winner != null && loser != null) {
            existingGameHistory.setPlayers(new HashSet<>(Set.of(winner, loser)));
            if (Boolean.FALSE.equals(isDraw)) {
                existingGameHistory.setWinnerUsername(winner.getUsername());
                existingGameHistory.setLoserUsername(loser.getUsername());
            }
        }

        existingGameHistory.setGameOverReason(gameOverReason);
        existingGameHistory.setIsDraw(isDraw);

        gameHistories.put(roomId, existingGameHistory);
    }
}