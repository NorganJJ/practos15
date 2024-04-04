package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.TaskModel;
import com.example.sport_club.model.TrainingModel;
import com.example.sport_club.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<TaskModel, Long> {
    java.lang.Iterable<TaskModel> findByUserMarkContainingIgnoreCase(String mark);

    java.lang.Iterable<TaskModel> findByCoachtask(UserModel userModel);
}
