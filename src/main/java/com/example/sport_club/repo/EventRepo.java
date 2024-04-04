package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<EventModel, Long> {
    java.lang.Iterable<EventModel> findByNameContainingIgnoreCase(String name);

    java.lang.Iterable<EventModel> findByUsereventContainingIgnoreCase(UserModel userModel);

    EventModel findById(long id);
}
