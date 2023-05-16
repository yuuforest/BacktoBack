package com.backtoback.point.betting.service;

import com.backtoback.point.betting.controller.BusinessFeignClient;
import com.backtoback.point.betting.dto.request.GameInfoReq;
import com.backtoback.point.betting.dto.request.GameResultReq;
import com.backtoback.point.betting.dto.request.GameSimpleInfoReq;
import com.backtoback.point.betting.dto.request.TeamInfoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeignServiceImpl implements FeignService{

    private final BusinessFeignClient feignClient;

    @Override
    public List<GameSimpleInfoReq> getGameSimpleInfo() {
        return feignClient.getGameSimpleInfo();
    }

    @Override
    public GameInfoReq getGameInfo(Long gameSeq) {
        return feignClient.getGameInfo(gameSeq);
    }

    @Override
    public TeamInfoReq getTeamInfo(Long teamSeq) {
        return feignClient.getTeamInfo(teamSeq);
    }

    @Override
    public GameResultReq getGameResult(Long gameSeq) {
        return feignClient.getGameResult(gameSeq);
    }

}
