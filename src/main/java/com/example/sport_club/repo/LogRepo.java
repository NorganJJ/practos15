package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.Log;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepo extends JpaRepository<Log, Long> {

}
