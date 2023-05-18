package com.backtoback.business.game.dto;

import com.backtoback.business.team.domain.TeamCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameResultWithTeamCode {

	private Long gameSeq;
	private TeamCode winTeamCode;
	private TeamCode loseTeamCode;

}
