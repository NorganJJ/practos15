package com.example.sport_club.repo;

import com.example.sport_club.model.EventModel;
import com.example.sport_club.model.SubscribeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepo extends JpaRepository<SubscribeModel, Long> {

    java.lang.Iterable<SubscribeModel> findByMonthContainingIgnoreCase(String month);

}
