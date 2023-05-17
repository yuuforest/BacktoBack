package com.backtoback.business.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backtoback.business.game.domain.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, GameCustomRepository {

	Game findByGameSeq(Long gameSeq);

}
