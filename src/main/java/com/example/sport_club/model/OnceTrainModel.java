package com.example.sport_club.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "oncetrain")
public class OnceTrainModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="date_time",columnDefinition="TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id") // Указывает на столбец, который связывает сущности
    private UserModel useronce;

    @ManyToOne
    @JoinColumn(name = "coach_id") // Указывает на столбец, который связывает сущности
    private UserModel coachonce;

    @ManyToOne
    @JoinColumn(name = "typeTrain_id") // Указывает на столбец, который связывает сущности
    private TrainTypeModel traintypeonce;


    public OnceTrainModel() {
    }

    public OnceTrainModel(long id, LocalDateTime dateTime, String status, UserModel useronce, UserModel coachonce, TrainTypeModel traintypeonce) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.useronce = useronce;
        this.coachonce = coachonce;
        this.traintypeonce = traintypeonce;
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

    public UserModel getUseronce() {
        return useronce;
    }

    public void setUseronce(UserModel useronce) {
        this.useronce = useronce;
    }

    public UserModel getCoachonce() {
        return coachonce;
    }

    public void setCoachonce(UserModel coachonce) {
        this.coachonce = coachonce;
    }

    public TrainTypeModel getTraintypeonce() {
        return traintypeonce;
    }

    public void setTraintypeonce(TrainTypeModel traintypeonce) {
        this.traintypeonce = traintypeonce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
