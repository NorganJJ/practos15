package com.example.sport_club.repo;

import com.example.sport_club.model.SportProfileModel;
import com.example.sport_club.model.SubscribeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SPRepo extends JpaRepository<SportProfileModel, Long> {

}
