package com.example.sport_club.config;

import com.example.sport_club.model.Log;
import com.example.sport_club.repo.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    public LogRepo logRepo;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        Log log = new Log();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currnetDateTime = dateFormat.format(new Date());
        log.setDate(currnetDateTime);
        log.setLevel("INFO");
        log.setMessage("Application Started");
        logRepo.save(log);
    }

}
