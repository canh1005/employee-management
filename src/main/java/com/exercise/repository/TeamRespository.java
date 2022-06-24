package com.exercise.repository;

import com.exercise.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRespository extends JpaRepository<Team, Integer> {
    boolean existsByName(String name);
}
