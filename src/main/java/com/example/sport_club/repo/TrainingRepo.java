package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.TrainingModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepo extends JpaRepository<TrainingModel, Long> {

    java.lang.Iterable<TrainingModel> findByStatusContainingIgnoreCase(String status);

    java.lang.Iterable<TrainingModel> findByStatusAndTrainplanCoachplan(String status, UserModel userModel);

}