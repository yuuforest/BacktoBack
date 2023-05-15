package com.backtoback.cheer.team.service;

import com.backtoback.cheer.common.exception.business.EntityNotFoundException;
import com.backtoback.cheer.team.domain.Team;
import com.backtoback.cheer.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.backtoback.cheer.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team getTeam(Long teamSeq) {
        return teamRepository.findById(teamSeq).orElseThrow(() -> new EntityNotFoundException(
                "해당하는 팀 ID 정보가 존재하지 않습니다. ", ENTITY_NOT_FOUND));
    }
}
