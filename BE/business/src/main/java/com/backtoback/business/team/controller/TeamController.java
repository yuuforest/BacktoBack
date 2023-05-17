package com.backtoback.business.team.controller;

import com.backtoback.business.team.dto.TeamInfoRes;
import com.backtoback.business.team.service.TeamService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "팀 API")
@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    // Betting ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("betting/team/{teamSeq}/info")
    public ResponseEntity<TeamInfoRes> getTeamInfo(@PathVariable("teamSeq") Long teamSeq) {
        TeamInfoRes response = teamService.getTeamInfo(teamSeq);
        return ResponseEntity.status(200).body(response);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
