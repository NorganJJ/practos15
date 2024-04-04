package com.example.sport_club.repo;

import com.example.sport_club.model.CoachStatsModel;
import com.example.sport_club.model.OnceTrainModel;
import com.example.sport_club.model.ReviewModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<ReviewModel, Long> {

    java.lang.Iterable<ReviewModel> findByCoachstats(CoachStatsModel coachStatsModel);

}
