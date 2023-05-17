package com.backtoback.point.photocard.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.photocard.domain.PhotoCard;
import com.backtoback.point.photocard.dto.response.PhotocardResultRes;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.backtoback.point.common.exception.ErrorCode.ENTITY_NOT_FOUND;

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

    //  1. 경기 목록 조회
    @Override
    public List<GameTeamListResultRes> getGames(){
        return gameService.getTeamListResult("2023-05-17 00:00:00");
    }

    //  2. 멤버 포인트 조회
    @Override
    public Integer getPoint(Long memberSeq){
        return memberService.getPoint(memberSeq);
    }

    //  3. 유저 포인트 차감
    @Override
    public void updatePoint(Long memberSeq) {
        memberService.updatePoint(memberSeq);
    }

    //  4. 포토카드 수량 차감
    @Override
    public void updatePhotocardQuantity(Long photoCardSeq) {
        PhotoCard photocard = photocardRepository.findById(photoCardSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 카드가 존재하지 않습니다.", ENTITY_NOT_FOUND));
        photocard.setQuantity(photocard.getQuantity() - 1);
    }

    //  5. 포토카드 gameSeq 로 조회
    @Override
    public List<PhotocardResultRes> getHL(Long gameSeq) {
        List<PhotoCard> photoCards = photocardRepository.findByGameGameSeq(gameSeq);
        return photoCards.stream()
                .map(photoCard -> PhotocardResultRes.builder()
                        .photocardSeq(photoCard.getPhotoCardSeq())
                        .photoCardUrl(photoCard.getPhotoCardUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateMyPhotocard(Long memberSeq) {

    }

    //  2. 경기 결과 받아오기
//    @Override
//    public Game getGameResult(Long gameSeq){
//        return gameService.getGame(gameSeq);
//    }

    //  3. 하이라이트 받아오기
//    @Override
//    public List<String> getHL(Long gameSeq) {
//        return gameService.getGame(gameSeq);
//    }

    // 4. 카드 구매
    public void buyPhotocard(Long memeberSeq, Long gameSeq) {
    }
}
