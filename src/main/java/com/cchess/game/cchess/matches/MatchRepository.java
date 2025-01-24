package com.cchess.game.cchess.matches;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, String> {
}
