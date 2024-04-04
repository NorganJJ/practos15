package com.example.sport_club.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "coachstats")
public class CoachStatsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(0)
    @Max(5)
    @Column(name = "midrate")
    private double midrate;

    @Min(0)
    @Column(name = "kolvo")
    private int kolvo;

    @OneToMany(mappedBy = "coachstats")
    private Collection<ReviewModel> coachstats;

    @ManyToOne
    @JoinColumn(name = "user_id") // Указывает на столбец, который связывает сущности
    private UserModel user;

    public CoachStatsModel() {
    }

    public CoachStatsModel(long id, double midrate, int kolvo, Collection<ReviewModel> coachstats, UserModel user) {
        this.id = id;
        this.midrate = midrate;
        this.kolvo = kolvo;
        this.coachstats = coachstats;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMidrate() {
        return midrate;
    }

    public void setMidrate(double midrate) {
        this.midrate = midrate;
    }

    public int getKolvo() {
        return kolvo;
    }

    public void setKolvo(int kolvo) {
        this.kolvo = kolvo;
    }

    public Collection<ReviewModel> getCoachstats() {
        return coachstats;
    }

    public void setCoachstats(Collection<ReviewModel> coachstats) {
        this.coachstats = coachstats;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
