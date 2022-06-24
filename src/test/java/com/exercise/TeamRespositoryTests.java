package com.exercise;

import com.exercise.entity.Team;
import com.exercise.repository.TeamRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamRespositoryTests {
    @Autowired
    TeamRespository repo;

    @Test
    public void addTeam() {
        Team team = new Team();
        team.setName("QA");
        
        repo.save(team);
    }
}
