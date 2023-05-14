package com.backtoback.member.dto.response;

import com.backtoback.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoResp {
    private Long memberSeq;
    private Integer betting_total;
    private String memberId;
    private String nickname;
    private Integer point;
    private Integer betting_win;
    private Long teamSeq;

    public static InfoResp fromEntity(Member member){
        return InfoResp.builder()
                .memberSeq(member.getMemberSeq())
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .teamSeq(member.getMemberSeq())
                .betting_win(member.getBettingWin())
                .betting_total(member.getBettingTotal())
                .build();
    }
}
