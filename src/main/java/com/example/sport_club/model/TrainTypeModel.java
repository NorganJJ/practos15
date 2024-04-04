package com.example.sport_club.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "traintype")
public class TrainTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Длина должна быть не больше 50 символов")
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "traintypeonce")
    private Collection<OnceTrainModel> onceTrain;

    @OneToMany(mappedBy = "traintype")
    private Collection<TrainingModel> training;

    public TrainTypeModel() {
    }

    public TrainTypeModel(long id, String name, Collection<OnceTrainModel> onceTrain, Collection<TrainingModel> training) {
        this.id = id;
        this.name = name;
        this.onceTrain = onceTrain;
        this.training = training;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<OnceTrainModel> getOnceTrain() {
        return onceTrain;
    }

    public void setOnceTrain(Collection<OnceTrainModel> onceTrain) {
        this.onceTrain = onceTrain;
    }

    public Collection<TrainingModel> getTraining() {
        return training;
    }

    public void setTraining(Collection<TrainingModel> training) {
        this.training = training;
    }
}
