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

    final MemberRepository memberRepository;

    @Override
    @Transactional
    public void updateByBetting(Long memberSeq, Integer point) {
        Member member = getMemberByMembers(memberSeq);
        member.setPoint(member.getPoint() - point);
        member.setBettingTotal(member.getBettingTotal() + 1);
    }

    // *Question* Rest API로 호출해서 다시 구성해야 함 //////////////////////////////////////////////////////////////////////

    public Member getMemberByMembers(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }
}
