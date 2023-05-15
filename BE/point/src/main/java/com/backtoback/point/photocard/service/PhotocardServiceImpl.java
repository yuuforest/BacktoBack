package com.backtoback.point.photocard.service;

import com.backtoback.point.photocard.repository.PhotocardRepository;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.game.service.GameService;
import com.backtoback.point.member.service.MemberService;
import com.backtoback.point.pointlog.service.PointLogService;
import com.backtoback.point.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PhotocardServiceImpl implements PhotocardService{

    private final MemberService memberService;
    private final GameService gameService;
    private final TeamService teamService;
    private final PointLogService pointLogService;

    private final PhotocardRepository photocardRepository;

    //  0. DB에서 포토카드 받아오기
//    @Override
//    public PhotoCard getPhotocard(Long gameSeq){
//        return photocardRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(
//                "해당하는 경기 ID 정보가 존재하지 않습니다. ", ENTITY_NOT_FOUND));
//    }

    //  0. DB에서 포토카드 리스트 받아오기
//    @Override
//    public List<PhotoCard> getPhotocardList(Long gameSeq){
//        return photocardRepository.findById(gameSeq);
//    }

    //  1. 경기 목록 받아오기
    @Override
    public List<GameTeamListResultRes> getGames(){
        return gameService.getTeamListResult("2023-05-15 00:00:00");
    }

    //  2. 경기 결과 받아오기
    @Override
    public Game getGameResult(Long gameSeq){
        return gameService.getGame(gameSeq);
    }

    //  3. 하이라이트 받아오기
//    @Override
//    public List<String> getHL(Long gameSeq) {
//        return gameService.getGame(gameSeq);
//    }

    // 4. 카드 구매
//    public PhotocardResultRes getPhotocard(Long gameSeq, PhotocardInfoReq photocardInfoReq) {
//        return
//    }
}
