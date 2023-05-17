package com.backtoback.point.photocard.controller;

import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.photocard.dto.response.PhotocardResultRes;
import com.backtoback.point.photocard.service.PhotocardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "포토카드 API", tags = "photocard")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class PhotocardController {

    private final PhotocardService photocardService;

    //  1. 경기 목록 받아오기
    @PostMapping("photocard/getGames")
    @ApiOperation(value = "경기", notes = "경기 결과 조회")
    public ResponseEntity<?> getGames() {
        List<GameTeamListResultRes> response = photocardService.getGames();
        return ResponseEntity.status(200).body(response);
    }

    //  2. 유저 포인트 조회
    @PostMapping("photocard/getPoint")
    @ApiOperation(value = "멤버", notes = "멤버 포인트 조회")
    public ResponseEntity<?> getPoint() {
        // memeberSeq 임시 설정 ~~~~~~~~~~~~~~~~~~~~~~~
        Long memberSeq = 1L;
        Integer response = photocardService.getPoint(memberSeq);
        return ResponseEntity.status(200).body(response);
    }

    //  3. 유저 포인트 차감
    @PostMapping("photocard/updatePoint")
    @ApiOperation(value = "멤버", notes = "멤버 포인트 조회")
    public ResponseEntity<?> updatePoint() {
        // memeberSeq 임시 설정 ~~~~~~~~~~~~~~~~~~~~~~~
        Long memberSeq = 1L;
        photocardService.updatePoint(memberSeq);
        return ResponseEntity.status(200).body("Success");
    }

    //  4. 포토카드 수량 차감
    @PostMapping("photocard/updatePhotocard/{photoCardSeq}")
    @ApiOperation(value = "포토카드", notes = "포토카드 지급")
    public ResponseEntity<?> updatePhotocardQuantity(@PathVariable("photoCardSeq") Long photoCardSeq) {
        photocardService.updatePhotocardQuantity(photoCardSeq);
        return ResponseEntity.status(200).body("Success");
    }

    //  5. 포토카드 gameSeq 로 조회
    @GetMapping("photocard/getHL/{gameSeq}")
    @ApiOperation(value = "포토카드", notes = "포토카드 지급")
    public ResponseEntity<?> getHL(@PathVariable("gameSeq") Long gameSeq) {
        List<PhotocardResultRes> response = photocardService.getHL(gameSeq);
        return ResponseEntity.status(200).body(response);
    }

    //  4. 카드 URL 지급
//    @PostMapping("photocard/updatePhotocard")
//    @ApiOperation(value = "포토카드", notes = "포토카드 지급")
//    public ResponseEntity<?> updateMyPhotocard() {
//        // memeberSeq 임시 설정 ~~~~~~~~~~~~~~~~~~~~~~~
//        Long memberSeq = 1L;
//        photocardService.updateMyPhotocard(memberSeq);
//        return ResponseEntity.status(200).body("Success");
//    }

    //  2. 경기 결과 받아오기
//    @PostMapping("photocard/{gameSeq}/getGameResult")
//    @ApiOperation(value = "경기", notes = "경기 전체 내용")
//    public ResponseEntity<?> getGameResult(@PathVariable("gameSeq") Long gameSeq) {
//        Game response = photocardService.getGameResult(gameSeq);
//        return ResponseEntity.status(200).body(response);
//    }

    //  3. 하이라이트 받아오기
//    @PostMapping("photocard/{gameSeq}/getHL")
//    @ApiOperation(value = "영상", notes = "해당 경기 HL")
//    public ResponseEntity<?> getHL(@PathVariable("gameSeq") Long gameSeq) {
//        List<String> response = photocardService.getHL(gameSeq);
//        return ResponseEntity.status(200).body(response);
//    }s

    //  4. 카드 구매
    @PostMapping("photocard/{gameSeq}/getPhotocard")
    @ApiOperation(value = "포토카드", notes = "포토카드 구매")
    public ResponseEntity<?> buyPhotocard(@PathVariable("gameSeq") Long gameSeq) {
        // memeberSeq 임시 설정 ~~~~~~~~~~~~~~~~~~~~~~~
        Long memberSeq = 1L;
        photocardService.buyPhotocard(memberSeq, gameSeq);
        return ResponseEntity.status(200).body("Success");
    }
}
