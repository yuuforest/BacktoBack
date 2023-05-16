package com.backtoback.point.betting.controller;

import com.backtoback.point.betting.dto.request.GameInfoReq;
import com.backtoback.point.betting.dto.request.GameResultReq;
import com.backtoback.point.betting.dto.request.GameSimpleInfoReq;
import com.backtoback.point.betting.dto.request.TeamInfoReq;
import com.backtoback.point.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//@FeignClient(name = "BusinessFeign", url="http://localhost:8086/business", configuration = FeignConfig.class)
@FeignClient(name = "BusinessFeign", url="http://k8a708.p.ssafy.io/api/business", configuration = FeignConfig.class)
public interface BusinessFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/betting/game/info")
    List<GameSimpleInfoReq> getGameSimpleInfo();

    @RequestMapping(method = RequestMethod.GET, value = "/betting/game/{gameSeq}/info")
    GameInfoReq getGameInfo(@PathVariable("gameSeq") Long gameSeq);

    @RequestMapping(method = RequestMethod.GET, value = "/betting/team/{teamSeq}/info")
    TeamInfoReq getTeamInfo(@PathVariable("teamSeq") Long teamSeq);

    @RequestMapping(method = RequestMethod.GET, value = "/betting/game/{gameSeq}/result")
    GameResultReq getGameResult(@PathVariable("gameSeq") Long gameSeq);


}
