package com.example.sport_club.repo;

import com.example.sport_club.model.SportProfileModel;
import com.example.sport_club.model.TrainTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportProfileRepo extends JpaRepository<SportProfileModel, Long> {

}
