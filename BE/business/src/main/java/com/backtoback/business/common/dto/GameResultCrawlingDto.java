package com.backtoback.business.common.dto;

import com.backtoback.business.team.domain.TeamCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameResultCrawlingDto {

	private Long gameSeq;
	private String date;
	private TeamCode homeTeamCode;
	private TeamCode awayTeamCode;

}
