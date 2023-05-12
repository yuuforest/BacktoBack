package com.backtoback.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backtoback.team.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
