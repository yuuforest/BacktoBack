package com.backtoback.point.photocard.repository;

import com.backtoback.point.game.domain.Game;
import com.backtoback.point.photocard.domain.PhotoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PhotocardRepository extends JpaRepository<PhotoCard, Long>{
    Optional<PhotoCard> findById(Long photoCardSeq);
    List<PhotoCard> findByGame(Game game);
}

