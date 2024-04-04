package com.example.sport_club.repo;

import com.example.sport_club.model.CoachStatsModel;
import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    java.lang.Iterable<UserModel> findByPositionContainingIgnoreCase(String position);

    java.lang.Iterable<UserModel> findByEmailContainingIgnoreCase(String email);

    java.lang.Iterable<UserModel> findByPositionNotNull();

    UserModel findByEmail(String email);

    UserModel findById(long id);
}