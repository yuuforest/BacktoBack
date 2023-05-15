package com.backtoback.point.betting.service;

import com.backtoback.point.betting.dto.request.GameInfoReq;
import com.backtoback.point.betting.dto.request.GameResultReq;
import com.backtoback.point.betting.dto.request.GameSimpleInfoReq;
import com.backtoback.point.betting.dto.request.TeamInfoReq;

import java.util.List;


public interface FeignService {

    List<GameSimpleInfoReq> getGameSimpleInfo();
    GameInfoReq getGameInfo(Long gameSeq);
    TeamInfoReq getTeamInfo(Long teamSeq);
    GameResultReq getGameResult(Long gameSeq);


}
