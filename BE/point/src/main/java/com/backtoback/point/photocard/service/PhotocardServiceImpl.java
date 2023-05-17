package com.backtoback.point.photocard.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.member.domain.Member;
import com.backtoback.point.myphotocard.domain.MyPhotoCard;
import com.backtoback.point.photocard.domain.PhotoCard;
import com.backtoback.point.photocard.dto.response.PhotocardResultRes;
import com.backtoback.point.photocard.repository.PhotocardRepository;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.game.service.GameService;
import com.backtoback.point.member.service.MemberService;
import com.backtoback.point.myphotocard.service.MyPhotoCardService;
import com.backtoback.point.pointlog.service.PointLogService;
import com.backtoback.point.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.backtoback.point.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PhotocardServiceImpl implements PhotocardService{

    private final MemberService memberService;
    private final GameService gameService;
    private final MyPhotoCardService myphotocardService;

    private final PhotocardRepository photocardRepository;

    //  0. DB에서 포토카드 받아오기
    @Override
    public PhotoCard getPhotocard(Long gameSeq){
        return photocardRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 경기 ID 정보가 존재하지 않습니다. ", ENTITY_NOT_FOUND));
    }

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

    //  3. 포토카드 gameSeq 로 조회
    @Override
    public List<PhotocardResultRes> getHL(Long gameSeq) {
        Game game = gameService.getGame(gameSeq);
        List<PhotoCard> photocards = photocardRepository.findByGame(game);
        List<PhotocardResultRes> PhotocardResultResList = new ArrayList<>();

        for (PhotoCard photocard : photocards) {
            PhotocardResultResList.add(PhotocardResultRes.builder()
                    .photocardSeq(photocard.getPhotoCardSeq())
                    .photocardUrl(photocard.getPhotoCardUrl())
                    .photocardQuantity(photocard.getQuantity())
                    .build());
        }
        return PhotocardResultResList;
    }

    //  4. 유저 포인트 차감
    @Override
    public void updatePoint(Long memberSeq) {
        memberService.updatePoint(memberSeq);
    }

    //  5. 포토카드 수량 차감
    @Override
    @Transactional
    public void updatePhotocardQuantity(Long photocardSeq) {
        PhotoCard photocard = photocardRepository.findById(photocardSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 카드가 존재하지 않습니다.", ENTITY_NOT_FOUND));
        System.out.println(photocard.getPhotoCardSeq());
        System.out.println(photocard.getQuantity());
        photocard.setQuantity(photocard.getQuantity() - 1);
        System.out.println(photocard.getQuantity());
    }

    //  6. 유저 - 포토카드 등록
    @Override
    @Transactional
    public void updateMyPhotocard(Long memberSeq, Long photocardSeq) {

        Member member = memberService.getMember(memberSeq);

        PhotoCard photoCard = photocardRepository.findById(photocardSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 카드가 존재하지 않습니다.", ENTITY_NOT_FOUND));

        MyPhotoCard myPhotoCard = new MyPhotoCard();
        myPhotoCard.setMember(member);
        myPhotoCard.setPhotoCard(photoCard);

        myphotocardService.createMyPhotocard(myPhotoCard);
    }
}
