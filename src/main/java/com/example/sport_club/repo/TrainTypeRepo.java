package com.example.sport_club.repo;

import com.example.sport_club.model.TrainPlanModel;
import com.example.sport_club.model.TrainTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainTypeRepo extends JpaRepository<TrainTypeModel, Long> {

}
