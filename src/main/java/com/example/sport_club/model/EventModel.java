package com.example.sport_club.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event")
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name="date_time",columnDefinition="TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Min(0)
    @Column(name = "kolvo")
    private int kolvo;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Длина должна быть не больше 50 символов")
    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "coach_id") // Указывает на столбец, который связывает сущности
    private UserModel coachevent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name="eventmark",
            joinColumns=@JoinColumn (name="event_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private List<UserModel> userevent;

    public EventModel() {
    }

    public EventModel(long id, LocalDateTime dateTime, int kolvo, String name, UserModel coachevent, List<UserModel> userevent) {
        this.id = id;
        this.dateTime = dateTime;
        this.kolvo = kolvo;
        this.name = name;
        this.coachevent = coachevent;
        this.userevent = userevent;
    }

    public UserModel getCoachevent() {
        return coachevent;
    }

    public void setCoachevent(UserModel coachevent) {
        this.coachevent = coachevent;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserModel> getUserevent() {
        return userevent;
    }

    public void setUserevent(List<UserModel> userevent) {
        this.userevent = userevent;
    }
}
