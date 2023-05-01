package com.backtoback.point.pointlog.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.member.repository.MemberRepository;
import com.backtoback.point.pointlog.domain.PointDetailType;
import com.backtoback.point.pointlog.domain.PointLog;
import com.backtoback.point.pointlog.repository.PointLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.backtoback.point.common.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PointLogServiceImpl implements PointLogService {

    final MemberRepository memberRepository;
    final PointLogRepository pointLogRepository;

    @Override
    @Transactional
    public void createPointLog(Integer point, Long memberSeq) {
        Member member = getMemberByMembers(memberSeq);
        PointLog log = PointLog.builder()
                .point(-point)
                .pointDetail(PointDetailType.BET)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .member(member)
                .build();
        pointLogRepository.save(log);
    }

    // *Question* Rest API로 호출해서 다시 구성해야 함 //////////////////////////////////////////////////////////////////////

    public Member getMemberByMembers(Long memberSeq) {
        return memberRepository.findById(memberSeq).orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }
}
