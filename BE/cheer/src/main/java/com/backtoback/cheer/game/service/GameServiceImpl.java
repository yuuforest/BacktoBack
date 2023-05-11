package com.backtoback.cheer.game.service;

import com.backtoback.cheer.common.exception.business.EntityNotFoundException;
import com.backtoback.cheer.game.domain.Game;
import com.backtoback.cheer.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.backtoback.cheer.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public Game getGame(Long gameSeq) {
        return gameRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 경기 ID 정보가 존재하지 않습니다. ", ENTITY_NOT_FOUND));
    }

    @Override
    public List<Game> getListByGames(String date) {
        return gameRepository.getAllGameByDate(date);
    }
}
