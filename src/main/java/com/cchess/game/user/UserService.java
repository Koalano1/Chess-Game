package com.cchess.game.user;

import com.cchess.game.exception.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Transactional
    public void adjustEloRating(String winnerUsername, String loserUsername) {
        User winnerElo = getUserByUsername(winnerUsername);
        User loserElo = getUserByUsername(loserUsername);

        int kFactor = 24;
        double expectedWinnerScore = 1.0 / (1 + Math.pow(10, (loserElo.getElo() - winnerElo.getElo()) / 400.0));
        double expectedLoserScore = 1.0 / (1 + Math.pow(10, (winnerElo.getElo() - loserElo.getElo()) / 400.0));

        int winnerScore = 1;
        int loserScore = 0;

        int eloDeltaWinner = (int) Math.round(kFactor * (winnerScore - expectedWinnerScore));
        int eloDeltaLoser = (int) Math.round(kFactor * (loserScore - expectedLoserScore));

        winnerElo.setElo(winnerElo.getElo() + eloDeltaWinner);
        loserElo.setElo(loserElo.getElo() + eloDeltaLoser);
    }
}
