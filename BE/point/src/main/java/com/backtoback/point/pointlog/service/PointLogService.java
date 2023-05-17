package com.backtoback.point.pointlog.service;

import com.backtoback.point.pointlog.dto.PointLogRes;

import java.util.List;

public interface PointLogService {

    void createMinusPointLog(Long memberSeq, Integer point);
    void createPlusPointLog(Long memberSeq, Integer point);
    List<PointLogRes> getPointLogs(Long memberSeq);
}
