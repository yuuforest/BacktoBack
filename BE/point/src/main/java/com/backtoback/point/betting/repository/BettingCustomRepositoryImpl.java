package com.backtoback.point.betting.repository;

import com.backtoback.point.betting.domain.Betting;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backtoback.point.betting.domain.QBetting.betting;

@RequiredArgsConstructor
@Repository
public class BettingCustomRepositoryImpl implements BettingCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Betting getByMemberSeqAndGameSeq(Long memberSeq, Long gameSeq) {
        return jpaQueryFactory
                .selectFrom(betting)
                .where(betting.game.gameSeq.eq(gameSeq), betting.member.memberSeq.eq(memberSeq))
                .fetchOne();
    }

    @Override
    public List<Betting> getByGameSeq(Long gameSeq) {
        return jpaQueryFactory
                .selectFrom(betting)
                .where(betting.game.gameSeq.eq(gameSeq))
                .fetch();
    }
}
