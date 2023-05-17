package com.backtoback.point.photocard.service;

import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.photocard.dto.response.PhotocardResultRes;

import java.util.List;

public interface PhotocardService {

    //  0. DB에서 포토카드 받아오기
//    PhotoCard getPhotocard(Long gameSeq);

    //  0. DB에서 포토카드 리스트 받아오기
//    List<PhotoCard> getPhotocardList(Long gameSeq);

    //  1. 경기 목록 조회
    List<GameTeamListResultRes> getGames();

    //  2. 멤버 포인트 조회
    Integer getPoint(Long memeberSeq);

    //  3. 유저 포인트 차감
    void updatePoint(Long memeberSeq);

    //  4. 포토카드 수량 차감
    void updatePhotocardQuantity(Long photoCardSeq);

    //  5. 포토카드 gameSeq 로 조회
    List<PhotocardResultRes> getHL(Long gameSeq);

    //  5.  포토카드 지급
    void updateMyPhotocard(Long memberSeq);

    //  2. 경기 결과 받아오기
//    Game getGameResult(Long gameSeq);

    //  3. 하이라이트 받아오기
//    List<String> getHL(Long gameSeq);

    // 4. 카드 구매
    void buyPhotocard(Long memberSeq, Long gameSeq);
}
