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
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
        member.setPoint(member.getPoint() - point);
        member.setBettingTotal(member.getBettingTotal() + 1);
    }

}
