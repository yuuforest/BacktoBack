package com.backtoback.media.game.service;


import com.backtoback.media.game.domain.Game;
import com.backtoback.media.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;

    public List<Game> getAllTodayGame() {
        return gameRepository.getAllTodayGame();
    }
}
