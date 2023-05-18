package com.backtoback.business.team.service;

import org.springframework.stereotype.Service;

import com.backtoback.business.team.domain.Team;
import com.backtoback.business.team.dto.TeamInfoRes;
import com.backtoback.business.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

	private final TeamRepository teamRepository;

	@Override
	public TeamInfoRes getTeamInfo(Long teamSeq) {
		Team team = teamRepository.findByTeamSeq(teamSeq);
		return TeamInfoRes.builder()
			.teamSeq(team.getTeamSeq())
			.teamName(team.getTeamName())
			.build();
	}

}
