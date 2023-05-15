package com.backtoback.business.team.repository;

import com.backtoback.business.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByTeamSeq(Long teamSeq);
}
