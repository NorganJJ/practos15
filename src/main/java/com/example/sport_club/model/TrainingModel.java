package com.example.sport_club.model;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "training")
public class TrainingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="date_time",columnDefinition="TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "status")
    @Value("${some.key:open}")
    private String status;

    @Size(max = 255, message = "Длина должна быть не больше 255 символов")
    @Column(name = "coachMark")
    private String coachMark;

    @ManyToOne
    @JoinColumn(name = "traintype_id") // Указывает на столбец, который связывает сущности
    private TrainTypeModel traintype;

    @ManyToOne
    @JoinColumn(name = "trainplan_id") // Указывает на столбец, который связывает сущности
    private TrainPlanModel trainplan;

    public TrainingModel() {
    }

    public TrainingModel(long id, LocalDateTime dateTime, String status, String coachMark, TrainTypeModel traintype, TrainPlanModel trainplan) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.coachMark = coachMark;
        this.traintype = traintype;
        this.trainplan = trainplan;
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

    public TrainTypeModel getTraintype() {
        return traintype;
    }

    public void setTraintype(TrainTypeModel traintype) {
        this.traintype = traintype;
    }

    public TrainPlanModel getTrainplan() {
        return trainplan;
    }

    public void setTrainplan(TrainPlanModel trainplan) {
        this.trainplan = trainplan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoachMark() {
        return coachMark;
    }

    public void setCoachMark(String coachMark) {
        this.coachMark = coachMark;
    }
}
