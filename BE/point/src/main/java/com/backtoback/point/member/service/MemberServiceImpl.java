package com.backtoback.point.member.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.backtoback.point.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 회원 ID 정보가 존재하지 않습니다.", ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateByBetting(Long memberSeq, Integer point) {
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

    @Override
    @Transactional
    public Integer getPoint(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 회원 ID 정보가 존재하지 않습니다.", ENTITY_NOT_FOUND));
        return member.getPoint();
    }

    @Override
    @Transactional
    public void updatePoint(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 회원 ID 정보가 존재하지 않습니다.", ENTITY_NOT_FOUND));
        member.setPoint(member.getPoint() - 100);
    }
}
