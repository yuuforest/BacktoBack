package com.backtoback.point.betting.repository;

import com.backtoback.point.betting.domain.Betting;

import java.util.List;

public interface BettingCustomRepository {

    Betting getByMemberSeqAndGameSeq(Long memberSeq, Long gameSeq);
    List<Betting> getByGameSeq(Long gameSeq);
}

