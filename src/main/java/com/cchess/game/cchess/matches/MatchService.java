package com.cchess.game.cchess.matches;

import com.cchess.game.user.User;
import com.cchess.game.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final GameHistoryCache gameHistoryCache;

    public void createAndSaveMatch(String roomId) {
        GameHistory gameHistory = gameHistoryCache.getGameHistory(roomId);
        Set<Player> players = gameHistory.getPlayers();
        List<Player> playerList = new ArrayList<>(players);

        if (gameHistory.getIsDraw()) {
            matchRepository.save(Match.builder()
                    .roomId(roomId)
                    .firstPlayerUsername(playerList.get(0).getUsername())
                    .secondPlayerUsername(playerList.get(1).getUsername())
                    .gameOverReason(gameHistory.getGameOverReason())
                    .isDraw(true)
                    .build());
            return;
        }

        User winner = userRepository.findByUsername(gameHistory.getWinnerUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User loser = userRepository.findByUsername(gameHistory.getLoserUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Match match = Match.builder()
                .roomId(roomId)
                .winner(winner)
                .loser(loser)
                .firstPlayerUsername(playerList.get(0).getUsername())
                .secondPlayerUsername(playerList.get(1).getUsername())
                .gameOverReason(gameHistory.getGameOverReason())
                .isDraw(gameHistory.getIsDraw())
                .build();
        matchRepository.save(match);
    }

}
