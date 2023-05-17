package com.backtoback.point.photocard.service;

import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.photocard.domain.PhotoCard;
import com.backtoback.point.photocard.dto.response.PhotocardResultRes;

import java.util.List;

public interface PhotocardService {

    //  0. DB에서 포토카드 받아오기
    PhotoCard getPhotocard(Long gameSeq);

    //  1. 경기 목록 조회
    List<GameTeamListResultRes> getGames();

    //  2. 멤버 포인트 조회
    Integer getPoint(Long memeberSeq);

    //  3. 포토카드 gameSeq 로 조회
    List<PhotocardResultRes> getHL(Long gameSeq);

    //  4. 유저 포인트 차감
    void updatePoint(Long memeberSeq);

    //  5. 포토카드 수량 차감
    void updatePhotocardQuantity(Long photocardSeq);

    //  6. 유저 - 포토카드 등록
    void updateMyPhotocard(Long memberSeq, Long photocardSeq);

}
