package com.backtoback.point.game.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.backtoback.point.common.exception.ErrorCode.GAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public Game getGame(Long gameSeq) {
        return gameRepository.findById(gameSeq).orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND));
    }

    @Override
    public List<Game> getListByGames(String date) {
        return gameRepository.getAllGameByDate(date);
    }
}
