package com.example.sport_club.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class ReviewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="date_time",columnDefinition="TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @NotBlank(message = "Name is required")
    @Size(max = 1000, message = "Длина должна быть не больше 1000 символов")
    @Column(name = "text")
    private String text;


    @Min(1)
    @Max(5)
    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id") // Указывает на столбец, который связывает сущности
    private UserModel user;


    @ManyToOne
    @JoinColumn(name = "cs_id") // Указывает на столбец, который связывает сущности
    private CoachStatsModel coachstats;

    public ReviewModel() {
    }

    public ReviewModel(long id, LocalDateTime dateTime, String text, int rating, UserModel user, CoachStatsModel coachstats) {
        this.id = id;
        this.dateTime = dateTime;
        this.text = text;
        this.rating = rating;
        this.user = user;
        this.coachstats = coachstats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CoachStatsModel getCoachstats() {
        return coachstats;
    }

    public void setCoachstats(CoachStatsModel coachstats) {
        this.coachstats = coachstats;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
