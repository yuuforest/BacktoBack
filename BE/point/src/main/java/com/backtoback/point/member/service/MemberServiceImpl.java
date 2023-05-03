package com.backtoback.point.member.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.backtoback.point.common.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateByBetting(Long memberSeq, Integer point) {
//        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
        Member member = getMember(memberSeq);
        member.setPoint(member.getPoint() - point);
        member.setBettingTotal(member.getBettingTotal() + 1);
    }

    @Override
    @Transactional
    public void updateByBettingResult(Long memberSeq, Integer point) {
        Member member = getMember(memberSeq);
        member.setBettingWin(member.getBettingWin() + 1);
        member.setPoint(member.getPoint() + point);
    }

}
