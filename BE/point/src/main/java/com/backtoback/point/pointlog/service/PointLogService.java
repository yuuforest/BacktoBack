package com.backtoback.point.pointlog.service;

public interface PointLogService {

    void createMinusPointLog(Long memberSeq, Integer point);
    void createPlusPointLog(Long memberSeq, Integer point);
}
