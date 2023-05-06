package com.backtoback.media.game.repository;


import com.backtoback.media.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, GameCustomRepository{

}
