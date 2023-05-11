package com.backtoback.cheer.cheering.controller;

import com.backtoback.cheer.cheering.dto.response.CheeringInfoRes;
import com.backtoback.cheer.cheering.service.CheerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Api(value = "응원 API", tags = "Cheer")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class CheerController {

    private final SimpMessagingTemplate webSocket;

    private final CheerService cheerService;

    @PostMapping("cheer/start")
    @ApiOperation(value = "응원을 위한 환경 설정", notes = "응원을 시작하기 위해, Redis에 환경 설정")
    public ResponseEntity<?> readyToStartCheer(){
        cheerService.readyToStartCheer();
        return ResponseEntity.status(200).body("Success");
    } // OK

    @GetMapping("game/{gameSeq}/cheer")
    @ApiOperation(value = "경기에 대한 현재 응원 점수", notes = "경기에 입장하면서, 이전까지의 각 팀 응원수 조회")
    public ResponseEntity<CheeringInfoRes> getCheeringInfo(@PathVariable("gameSeq") Long gameSeq){
        CheeringInfoRes cheeringInfo = cheerService.getCheeringInfo(gameSeq);
        return ResponseEntity.status(200).body(cheeringInfo);
    } // OK

    @MessageMapping("game/{gameSeq}/team/{teamSeq}/cheer")
    public void SendCheering(@DestinationVariable("gameSeq") Long gameSeq,
                             @DestinationVariable("teamSeq") Long teamSeq) {
        CheeringInfoRes cheeringInfo = cheerService.startCheering(gameSeq, teamSeq);
        webSocket.convertAndSend("/topics/game/" + gameSeq, cheeringInfo);
    } // OK

}
