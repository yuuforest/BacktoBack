package com.backtoback.business.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backtoback.business.team.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
