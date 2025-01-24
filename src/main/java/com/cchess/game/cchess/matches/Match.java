package com.cchess.game.cchess.matches;

import com.cchess.game.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matches")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    @ManyToOne
    @JoinColumn(name = "loser_id")
    private User loser;

    private String firstPlayerUsername;

    private String secondPlayerUsername;

    @Column(name = "game_over_reason")
    @Enumerated(EnumType.STRING)
    private GameOverReason gameOverReason;

    private Boolean isDraw;
}
