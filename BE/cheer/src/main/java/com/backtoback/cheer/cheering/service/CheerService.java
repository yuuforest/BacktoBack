package com.backtoback.cheer.cheering.service;

import com.backtoback.cheer.cheering.dto.response.CheeringInfoRes;

public interface CheerService {

    void readyToStartCheer();
    CheeringInfoRes getCheeringInfo(Long gameSeq);
    CheeringInfoRes startCheering(Long gameSeq, Long teamSeq);

}
