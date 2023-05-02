package com.backtoback.point.betting.service;


import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.dto.response.BettingResultRes;
import com.backtoback.point.member.domain.Member;

public interface BettingService {

    // [베팅 시작 전]
    void readyToStartBetting();

    // [베팅 시작]
    void startBetting(Long memberSeq, BettingInfoReq bettingInfoReq);
    void createBettingLog(Member member, BettingInfoReq bettingInfoReq);
    void updateRedisLog(BettingInfoReq bettingInfoReq);

    // [베팅 종료]
    BettingResultRes anticipateBettingResult(Long memberSeq, Long gameSeq);
    double calculateHomeRate(Long homeSeq, Long awaySeq, String key);
    Integer calculateDivdends(Long memberSeq, Long gameSeq, Long homeSeq, Long awaySeq, String key);
}
