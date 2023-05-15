//package com.backtoback.point.photocard.repository;
//
//import com.backtoback.point.photocard.domain.PhotoCard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.backtoback.point.photocard.domain.QGame.game;
//
//
//@RequiredArgsConstructor
//@Repository
//public class PhotocardCustomRepositoryImpl implements PhotocardCustomRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public List<Game> getAllPhotocard(Long gameSeq) {
//        return jpaQueryFactory
//                .selectFrom(game)
//                .where(game.gameDatetime.stringValue().contains(date))
//                .fetch();
//    }
//}
