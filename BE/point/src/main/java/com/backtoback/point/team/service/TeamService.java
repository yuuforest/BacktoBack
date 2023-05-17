package com.backtoback.point.team.service;


import com.backtoback.point.team.domain.Team;
import com.backtoback.point.team.dto.response.TeamInfoRes;

public interface TeamService {
    Team getTeam(Long teamSeq);

    TeamInfoRes getTeamListResult(Long teamSeq);
}
