package com.example.sport_club.repo;

import com.example.sport_club.model.CoachStatsModel;
import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachStatsRepo extends JpaRepository<CoachStatsModel, Long> {
    CoachStatsModel findByUser(UserModel userModel);

    CoachStatsModel findById(long id);
}
