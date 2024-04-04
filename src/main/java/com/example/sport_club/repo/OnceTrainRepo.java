package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.OnceTrainModel;
import com.example.sport_club.model.TrainingModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnceTrainRepo extends JpaRepository<OnceTrainModel, Long> {

    java.lang.Iterable<OnceTrainModel> findByStatusContainingIgnoreCase(String status);

    java.lang.Iterable<OnceTrainModel> findByStatusAndCoachonce(String status, UserModel userModel);

}
