package com.backtoback.point.game.dto.response;
import com.backtoback.point.game.domain.GameActiveType;
import com.backtoback.point.team.domain.Team;
import com.backtoback.point.team.dto.response.TeamInfoRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
public class GameTeamListResultRes implements Serializable {

    @ApiModelProperty(value = "game_seq")
    private Long gameSeq;

    @ApiModelProperty(value = "is_active")
    private GameActiveType gameActive;

    @ApiModelProperty(value = "game_datetime")
    private LocalDateTime gameDateTime;

    @ApiModelProperty(value = "place")
    private String gamePlace;

    @ApiModelProperty(value = "Home팀 이름")
    private TeamInfoRes homeTeam;

    @ApiModelProperty(value = "Away팀 팀")
    private TeamInfoRes awayTeam;


    @ApiModelProperty(value = "이긴 팀")
    private TeamInfoRes winTeam;

    @ApiModelProperty(value = "진 팀")
    private TeamInfoRes loseTeam;

}
