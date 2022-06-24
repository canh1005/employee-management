package com.exercise.services;

import com.exercise.dto.TeamDTO;
import com.exercise.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDTO> findAllTeam();

    Page<Team> findAllTeamWithPage(Integer page);

    Optional<Team> findById(Integer integer);

    void deleteById(Integer integer);

    TeamDTO addTeam(TeamDTO teamDTO) throws Exception;
}
