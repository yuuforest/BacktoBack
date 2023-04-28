package com.backtoback.point.betting.service;


import com.backtoback.point.betting.dto.request.StreamingRoomInfoReq;

public interface BettingService {

    void readyToStartBetting(StreamingRoomInfoReq roomInfoReq);
}
