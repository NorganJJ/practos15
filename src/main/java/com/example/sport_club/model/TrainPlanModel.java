package com.example.sport_club.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "trainplan")
public class TrainPlanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(5)
    @Max(30)
    @Column(name = "kolvo")
    private int kolvo;

    @ManyToOne
    @JoinColumn(name = "user_id") // Указывает на столбец, который связывает сущности
    private UserModel userplan;

    @ManyToOne
    @JoinColumn(name = "coach_id") // Указывает на столбец, который связывает сущности
    private UserModel coachplan;

    @OneToMany(mappedBy = "trainplan")
    private Collection<TrainingModel> training;

    public TrainPlanModel() {
    }

    public TrainPlanModel(long id, int kolvo, UserModel userplan, UserModel coachplan, Collection<TrainingModel> training) {
        this.id = id;
        this.kolvo = kolvo;
        this.userplan = userplan;
        this.coachplan = coachplan;
        this.training = training;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getKolvo() {
        return kolvo;
    }

    public void setKolvo(int kolvo) {
        this.kolvo = kolvo;
    }

    public UserModel getUserplan() {
        return userplan;
    }

    public void setUserplan(UserModel userplan) {
        this.userplan = userplan;
    }

    public UserModel getCoachplan() {
        return coachplan;
    }

    public void setCoachplan(UserModel coachplan) {
        this.coachplan = coachplan;
    }

    public Collection<TrainingModel> getTraining() {
        return training;
    }

    public void setTraining(Collection<TrainingModel> training) {
        this.training = training;
    }
}
