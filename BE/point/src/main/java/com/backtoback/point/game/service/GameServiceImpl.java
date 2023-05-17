package com.backtoback.point.game.service;

import com.backtoback.point.common.exception.business.EntityNotFoundException;
import com.backtoback.point.game.domain.Game;
import com.backtoback.point.game.dto.response.GameTeamListResultRes;
import com.backtoback.point.game.repository.GameRepository;
import com.backtoback.point.game.service.GameService;
import com.backtoback.point.team.domain.Team;
import com.backtoback.point.team.dto.response.TeamInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.backtoback.point.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Transactional
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

    @Override
    public List<GameTeamListResultRes> getTeamListResult(String date) {

        List<Game> games = gameRepository.getAllGameByDate(date);

        List<GameTeamListResultRes> gameTeamListResultResList = new ArrayList<>();

        Long gameSeq;
        Team homeTeam, awayTeam;

        for(Game game : games){
            gameTeamListResultResList.add(
                    GameTeamListResultRes.builder()
                            .gameSeq(game.getGameSeq())
                            .gameActive(game.getGameActiveType())
                            .gameDateTime(game.getGameDatetime())
                            .gamePlace(game.getPlace())
                            .homeTeam(TeamInfoRes.builder().teamSeq(game.getHomeTeam().getTeamSeq()).teamName(game.getHomeTeam().getTeamName()).build())
                            .awayTeam(TeamInfoRes.builder().teamSeq(game.getAwayTeam().getTeamSeq()).teamName(game.getAwayTeam().getTeamName()).build())
                            .winTeam(TeamInfoRes.builder().teamSeq(game.getWinTeam().getTeamSeq()).teamName(game.getWinTeam().getTeamName()).build())
                            .loseTeam(TeamInfoRes.builder().teamSeq(game.getLoseTeam().getTeamSeq()).teamName(game.getLoseTeam().getTeamName()).build())
                            .build()
            );
        }

        return gameTeamListResultResList;
    }
}
