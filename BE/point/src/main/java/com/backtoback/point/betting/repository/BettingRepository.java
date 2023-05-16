package com.backtoback.point.betting.repository;

import com.backtoback.point.betting.domain.Betting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BettingRepository extends JpaRepository<Betting, Long>{

    Optional<Betting> findByMemberMemberSeqAndGameGameSeq(Long memberSeq, Long gameSeq);
    List<Betting> findByGameGameSeq(Long gameSeq);


}
