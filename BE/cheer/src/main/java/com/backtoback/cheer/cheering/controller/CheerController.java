package com.backtoback.cheer.cheering.controller;

import com.backtoback.cheer.cheering.dto.response.CheeringInfoRes;
import com.backtoback.cheer.cheering.service.CheerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    } // NOT YET

    @GetMapping("game/{gameSeq}/cheer")
    @ApiOperation(value = "경기에 대한 현재 응원 점수", notes = "경기에 입장하면서, 이전까지의 각 팀 응원수 조회")
    public ResponseEntity<CheeringInfoRes> getCheeringInfo(@PathVariable("gameSeq") Long gameSeq){
        CheeringInfoRes cheeringInfo = cheerService.getCheeringInfo(gameSeq);
        return ResponseEntity.status(200).body(cheeringInfo);
    } // NOT YET



    // WebSocket ///////////////////////////////////////////////////////////////////////////////////////////////////////

    // gameSeq teamSeq 가 필요한데....

    @MessageMapping("ws/game/{gameSeq}/team/{teamSeq}/cheer")
    public void SendCheering() {
        System.out.println("Backend Websocket");
        webSocket.convertAndSend("/topics/template" , "Server -> Template");
    }

//    @MessageMapping("/sendTo/{memberSeq}")
//    @SendTo("/topics/sendTo")
//    public String SendToMessage(@DestinationVariable("memberSeq") String room) throws Exception {
//        System.out.println("SendToMessage : " + room);
//        return "Server -> SendTo";
//    }
//
//    @MessageMapping("/Template")
//    public void SendTemplateMessage() {
//        System.out.println("SendTemplateMessage");
//        webSocket.convertAndSend("/topics/template" , "Server -> Template");
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
