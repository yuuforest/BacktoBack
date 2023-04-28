package com.backtoback.point.betting.service;

import com.backtoback.point.betting.dto.request.StreamingRoomInfoReq;
import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.repository.GameRepository;
import com.backtoback.point.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.backtoback.point.common.exception.ErrorCode.GAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BettingServiceImpl implements BettingService{

    final GameRepository gameRepository;
    final TeamRepository teamRepository;

    @Override
    public void readyToStartBetting(StreamingRoomInfoReq roomInfoReq) {
        // game과 team이 존재하는지 확인
        Optional<Game> game = gameRepository.findByGameSeq(roomInfoReq.getGameSeq());
        if(game.isEmpty()) throw new EntityNotFoundException("해당하는 경기가 존재하지 않습니다.", GAME_NOT_FOUND);
        else {

        }

        // [Redis] 각 팀의 베팅수 0 저장

        // [Redis] 각 팀의 총 베팅 포인트 0 저장

    }
}
