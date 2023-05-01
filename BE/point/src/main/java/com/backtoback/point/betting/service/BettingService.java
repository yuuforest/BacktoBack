package com.backtoback.point.betting.service;


import com.backtoback.point.betting.dto.request.BettingInfoReq;

public interface BettingService {

    // [베팅 시작 전]
    void readyToStartBetting();

    // [베팅 시작]
    void startBetting(Long memberSeq, BettingInfoReq bettingInfoReq);
    void createBettingLog(Long memberSeq, BettingInfoReq bettingInfoReq);
    void updateRedisLog(BettingInfoReq bettingInfoReq);
}
