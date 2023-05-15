package com.backtoback.point.betting.repository;

import com.backtoback.point.betting.domain.Betting;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BettingRepository extends JpaRepository<Betting, Long>, BettingCustomRepository{

    Optional<Betting> findByMemberAndGame(Member member, Game game);
    List<Betting> findByGame(Game game);
}
