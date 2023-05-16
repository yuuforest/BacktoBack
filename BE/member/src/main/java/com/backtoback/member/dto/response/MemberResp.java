package com.backtoback.member.dto.response;

import com.backtoback.member.domain.Member;
import com.backtoback.member.domain.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResp {
    private Long memberSeq;
    private Integer betting_total;
    private String memberId;
    private String nickname;
    private Integer point;
    private Integer betting_win;
    private String teamName;

    public static MemberResp fromEntity(Member member){
        return MemberResp.builder()
                .memberSeq(member.getMemberSeq())
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .teamName(member.getTeam().getTeamName())
                .betting_win(member.getBettingWin())
                .betting_total(member.getBettingTotal())
                .build();
    }
}
