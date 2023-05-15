package com.backtoback.point.photocard.service;

import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.game.domain.Game;
import java.util.List;

public interface PhotocardService {

    //  0. DB에서 포토카드 받아오기
//    PhotoCard getPhotocard(Long gameSeq);

    //  0. DB에서 포토카드 리스트 받아오기
//    List<PhotoCard> getPhotocardList(Long gameSeq);

    //  1. 경기 목록 받아오기
    List<GameTeamListResultRes> getGames();

    //  2. 경기 결과 받아오기
    Game getGameResult(Long gameSeq);

    //  3. 하이라이트 받아오기
//    List<String> getHL(Long gameSeq);

    // 4. 카드 구매
//    PhotocardResultRes getPhotocard(Long gameSeq, PhotocardInfoReq photocardInfoReq);
}
