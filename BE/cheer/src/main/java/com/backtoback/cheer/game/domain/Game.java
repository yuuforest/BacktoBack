package com.backtoback.cheer.game.domain;

import com.backtoback.cheer.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "GAMES")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Identity로 하면 디비엔진에 따라 오토 인크리먼트가 안먹는다.
    @Column(name = "game_seq")
    private Long gameSeq;

    // @Column(name = "game_datetime", nullable = false)
    // // @Temporal(TemporalType.TIMESTAMP)
    // private Timestamp gameDatetime;

    @Column(name = "game_datetime", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime gameDatetime;

    @Column(name = "place", nullable = false, length = 50)
    private String place;

    @Column(name = "is_active", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameActiveType gameActiveType;

    // @Column(name = "betting_rate", nullable = false)
    // @ColumnDefault(value = "0")
    // private Double bettingRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_seq", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_seq", nullable = false)
    private Team awayTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "win_team_seq", nullable = true)
    private Team winTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lose_team_seq", nullable = true)
    private Team loseTeam;

}
