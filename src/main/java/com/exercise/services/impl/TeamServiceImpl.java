package com.exercise.services.impl;

import com.exercise.dto.TeamDTO;
import com.exercise.entity.Team;
import com.exercise.repository.TeamRespository;
import com.exercise.services.TeamService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    @Autowired
    private TeamRespository teamRespository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TeamDTO> findAllTeam() {
        List<TeamDTO> listOfTeamDTO = new ArrayList<>();
        for (Team teamEntity : teamRespository.findAll()
        ) {
            TeamDTO teamDTO = mapper.map(teamEntity, TeamDTO.class);
            listOfTeamDTO.add(teamDTO);
        }
        return listOfTeamDTO;
    }

    @Override
    public Page<Team> findAllTeamWithPage(Integer page) {
        Integer pageSize = 5;
        Page<Team> listOfTeam = teamRespository.findAll(PageRequest.of(page, pageSize));
        logger.info("listOfTeam" + listOfTeam);
        return listOfTeam;
    }

    @Override
    public Optional<Team> findById(Integer integer) {
        return teamRespository.findById(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        teamRespository.deleteById(integer);
    }

    @Override
    public TeamDTO addTeam(TeamDTO teamDTO) throws Exception {
        if (teamRespository.existsByName(teamDTO.getName())) throw new Exception("Team already existed!");
        Team teamEntity = mapper.map(teamDTO, Team.class);
        teamRespository.save(teamEntity);
        return teamDTO;
    }

}
