package com.backtoback.point.pointlog.controller;

import com.backtoback.point.betting.dto.response.BettingInfoRes;
import com.backtoback.point.pointlog.dto.PointLogRes;
import com.backtoback.point.pointlog.service.PointLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "포인트 로그 API", tags = "point-log")
@RestController
@RequiredArgsConstructor
@RequestMapping("/point-log")
public class PointLogController {

    private final PointLogService pointLogService;

    @GetMapping("/member/{memberSeq}")
    @ApiOperation(value = "유저의 포인트 로그 조회", notes = "")
    public ResponseEntity<List<PointLogRes>> getPointLogs(@PathVariable("memberSeq") Long memberSeq) {
        List<PointLogRes> response = pointLogService.getPointLogs(memberSeq);
        return ResponseEntity.status(200).body(response);
    } // Not Yet


}
