package com.backtoback.point.betting.service;


import com.backtoback.point.betting.domain.Betting;
import com.backtoback.point.betting.dto.request.BettingInfoReq;
import com.backtoback.point.betting.dto.response.BettingInfoRes;
import com.backtoback.point.betting.dto.response.BettingResultRes;
import com.backtoback.point.betting.dto.request.KafkaReq;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.team.domain.Team;

public interface BettingService {

    // [베팅 시작 전]
    void readyToStartBetting();
    BettingInfoRes getBettingInfo(Long memberSeq, Long gameSeq);

    // [베팅 시작]
    void startBetting(Long memberSeq, BettingInfoReq bettingInfoReq);
    void createBettingLog(Member member, BettingInfoReq bettingInfoReq);
    void updateRedisLog(BettingInfoReq bettingInfoReq);

    // [베팅 종료]
    BettingResultRes anticipateBettingResult(Long memberSeq, Long gameSeq);
    Long calculateHomeRate(Long homeSeq, Long awaySeq, String key);
    Long calculateDivdends(Betting betting, Long homeSeq, Long awaySeq, String key);

    // [베팅 결과]
    void getBettingResult(KafkaReq kafkaRes);

    // [Feign Client]
    Game getGame(Long gameSeq);
    Team getTeam(Long teamSeq);
}
