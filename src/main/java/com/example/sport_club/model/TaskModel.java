package com.example.sport_club.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="date_time",columnDefinition="TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Size(max = 1000, message = "Длина должна быть не больше 255 символов")
    @Column(name = "userMark")
    private String userMark;

    @ManyToOne
    @JoinColumn(name = "user_id") // Указывает на столбец, который связывает сущности
    private UserModel usertask;

    @ManyToOne
    @JoinColumn(name = "coach_id") // Указывает на столбец, который связывает сущности
    private UserModel coachtask;

    public TaskModel() {
    }

    public TaskModel(long id, LocalDateTime dateTime, String userMark, UserModel usertask, UserModel coachtask) {
        this.id = id;
        this.dateTime = dateTime;
        this.userMark = userMark;
        this.usertask = usertask;
        this.coachtask = coachtask;
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

    public UserModel getUsertask() {
        return usertask;
    }

    public void setUsertask(UserModel usertask) {
        this.usertask = usertask;
    }

    public UserModel getCoachtask() {
        return coachtask;
    }

    public void setCoachtask(UserModel coachtask) {
        this.coachtask = coachtask;
    }

    public String getUserMark() {
        return userMark;
    }

    public void setUserMark(String userMark) {
        this.userMark = userMark;
    }
}
