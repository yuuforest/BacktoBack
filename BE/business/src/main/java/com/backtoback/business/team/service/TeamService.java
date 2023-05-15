package com.backtoback.business.team.service;

import com.backtoback.business.team.dto.TeamInfoRes;

public interface TeamService {

    TeamInfoRes getTeamInfo(Long teamSeq);
}
