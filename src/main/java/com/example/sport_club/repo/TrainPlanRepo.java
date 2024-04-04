package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.TrainPlanModel;
import com.example.sport_club.model.TrainingModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainPlanRepo extends JpaRepository<TrainPlanModel, Long> {

    TrainPlanModel findById(long id);

    java.lang.Iterable<TrainPlanModel> findByCoachplan(UserModel userModel);

    java.lang.Iterable<TrainPlanModel> findByUserplan(UserModel userModel);

    java.lang.Iterable<TrainPlanModel> findByKolvo(int kolvo);

}
