package com.backtoback.point.betting.repository;

import com.backtoback.point.betting.domain.Betting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BettingRepository extends JpaRepository<Betting, Long> {
}
