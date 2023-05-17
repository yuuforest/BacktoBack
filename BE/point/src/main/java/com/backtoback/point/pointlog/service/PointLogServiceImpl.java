package com.backtoback.point.pointlog.service;

import com.backtoback.point.member.service.MemberService;
import com.backtoback.point.pointlog.domain.PointDetailType;
import com.backtoback.point.pointlog.domain.PointLog;
import com.backtoback.point.pointlog.dto.PointLogRes;
import com.backtoback.point.pointlog.repository.PointLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointLogServiceImpl implements PointLogService {

    private final PointLogRepository pointLogRepository;

    private final MemberService memberService;

    @Override
    public void createMinusPointLog(Long memberSeq, Integer point) {
        PointLog log = PointLog.builder()
                .point(-point)
                .pointDetail(PointDetailType.BET)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .member(memberService.getMember(memberSeq))
                .build();
        pointLogRepository.save(log);
    }

    @Override
    public void createPlusPointLog(Long memberSeq, Integer point) {
        PointLog log = PointLog.builder()
                .point(point)
                .pointDetail(PointDetailType.BET)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .member(memberService.getMember(memberSeq))
                .build();
        pointLogRepository.save(log);
    }

    @Override
    public List<PointLogRes> getPointLogs(Long memberSeq) {
        List<PointLog> logs = pointLogRepository.findByMemberMemberSeqOrderByPointLogSeqDesc(memberSeq);
        List<PointLogRes> pointLogs = new ArrayList<>();
        Long number = 0L;
        for (PointLog log : logs) {
            number++;
            PointLogRes res = PointLogRes.builder()
                    .num(number)
                    .point(log.getPoint())
                    .detail(log.getPointDetail().toString())
                    .time(log.getTime())
                    .build();
            pointLogs.add(res);
        }
        return pointLogs;
    }
}
