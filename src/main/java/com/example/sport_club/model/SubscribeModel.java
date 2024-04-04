package com.example.sport_club.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "subscribe")
public class SubscribeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 20, message = "Длина должна быть не больше 50 символов")
    @Column(name="month")
    private String month;

    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "status")
    @Value("${some.key:open}")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id") // Указывает на столбец, который связывает сущности
    private UserModel user;

    public SubscribeModel() {
    }

    public SubscribeModel(long id, String month, String status, UserModel user) {
        this.id = id;
        this.month = month;
        this.status = status;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
