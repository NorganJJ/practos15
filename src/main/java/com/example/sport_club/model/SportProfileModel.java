package com.example.sport_club.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "SportProfile")
public class SportProfileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Длина должна быть не больше 50 символов")
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "sportProfile")
    private Collection<UserModel> user;

    public SportProfileModel() {
    }

    public SportProfileModel(long id, String name, Collection<UserModel> user) {
        this.id = id;
        this.name = name;
        this.user = user;
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

    public Collection<UserModel> getUser() {
        return user;
    }

    public void setUser(Collection<UserModel> user) {
        this.user = user;
    }
}
