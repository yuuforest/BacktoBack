package com.backtoback.business.game.dto;

import java.time.LocalDateTime;

import com.backtoback.business.game.dto.betting.GameSimpleInfoRes;
import com.backtoback.business.team.domain.TeamCode;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GameInfoWithTeamCode extends GameSimpleInfoRes {

	private TeamCode homeTeamCode;
	private TeamCode awayTeamCode;
	private LocalDateTime gameDatetime;

}
