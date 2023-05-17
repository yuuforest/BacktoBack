package com.backtoback.point.member.service;

import com.backtoback.point.member.domain.Member;

public interface MemberService {

    Member getMember(Long memberSeq);
    void updateByBetting(Long memberSeq, Integer point);
    void updateByBettingResult(Long memberSeq, Integer point);

    Integer getPoint(Long memberSeq);

    void updatePoint(Long memberSeq);
}
