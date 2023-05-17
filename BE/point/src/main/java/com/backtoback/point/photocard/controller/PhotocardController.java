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
    @GetMapping("photocard/getGames")
    @ApiOperation(value = "경기", notes = "경기 결과 조회")
    public ResponseEntity<?> getGames() {
        List<GameTeamListResultRes> response = photocardService.getGames();
        return ResponseEntity.status(200).body(response);
    }

    //  2. 유저 포인트 조회
    @GetMapping("photocard/getPoint/{memberSeq}")
    @ApiOperation(value = "멤버", notes = "멤버 포인트 조회")
    public ResponseEntity<?> getPoint(@PathVariable("memberSeq") Long memberSeq) {
        Integer response = photocardService.getPoint(memberSeq);
        return ResponseEntity.status(200).body(response);
    }

    //  3. 포토카드 gameSeq 로 조회
    @GetMapping("photocard/getHL/{gameSeq}")
    @ApiOperation(value = "포토카드", notes = "포토카드 조회")
    public ResponseEntity<?> getHL(@PathVariable("gameSeq") Long gameSeq) {
        List<PhotocardResultRes> response = photocardService.getHL(gameSeq);
        return ResponseEntity.status(200).body(response);
    }

    //  4. 유저 포인트 차감
    @PostMapping("photocard/updatePoint/{memberSeq}")
    @ApiOperation(value = "멤버", notes = "멤버 포인트 조회")
    public ResponseEntity<?> updatePoint(@PathVariable("memberSeq") Long memberSeq) {
        photocardService.updatePoint(memberSeq);
        return ResponseEntity.status(200).body("Success");
    }

    //  5. 포토카드 수량 차감
    @PostMapping("photocard/updatePhotocard/{photocardSeq}")
    @ApiOperation(value = "포토카드", notes = "포토카드 지급")
    public ResponseEntity<?> updatePhotocardQuantity(@PathVariable("photocardSeq") Long photocardSeq) {
        photocardService.updatePhotocardQuantity(photocardSeq);
        return ResponseEntity.status(200).body("Success");
    }

    //  6. 유저 - 포토카드 등록
    @PostMapping("photocard/updateMyPhotocard/{memberSeq}/{photocardSeq}")
    @ApiOperation(value = "포토카드", notes = "포토카드 ")
    public ResponseEntity<?> updateMyPhotocard(@PathVariable("memberSeq") Long memberSeq, @PathVariable("photocardSeq") Long photocardSeq) {
        photocardService.updateMyPhotocard(memberSeq, photocardSeq);
        return ResponseEntity.status(200).body("Success");
    }
}
