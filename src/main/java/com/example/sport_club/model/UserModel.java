package com.example.sport_club.model;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "surname")
    private String surname;

    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "secondname")
    private String secondname;

    @Size(max = 30, message = "Длина должна быть не больше 30 символов")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Name is required")
    @Size(max = 40, message = "Длина должна быть не больше 40 символов")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Длина должна быть не больше 255 символов")
    @Column(name = "password")
    private String password;


    @Column(name = "date_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateBirth;

    @Column(name = "active")
    private boolean active;

    @Column(name = "image")
    private String image;

    @Min(0)
    @Max(300)
    @Column(name = "height")
    private double height;

    @Min(0)
    @Max(250)
    @Column(name = "weight")
    private double weight;

    @Size(max = 50, message = "Длина должна быть не больше 50 символов")
    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "sp_id") // Указывает на столбец, который связывает сущности
    private SportProfileModel sportProfile;

    @OneToMany(mappedBy = "coachevent")
    private Collection<EventModel> event;

    @ManyToMany
    @JoinTable (name="eventmark",
            joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="event_id"))
    private List<EventModel> eventmark;

    @OneToMany(mappedBy = "useronce")
    private Collection<OnceTrainModel> onceTrainuser;

    @OneToMany(mappedBy = "coachonce")
    private Collection<OnceTrainModel> onceTraincoach;

    @OneToMany(mappedBy = "userplan")
    private Collection<TrainPlanModel> trainPlanuser;

    @OneToMany(mappedBy = "coachplan")
    private Collection<TrainPlanModel> trainPlancoach;

    @OneToMany(mappedBy = "usertask")
    private Collection<TaskModel> taskuser;

    @OneToMany(mappedBy = "coachtask")
    private Collection<TaskModel> taskcoach;

    @OneToMany(mappedBy = "user")
    private Collection<ReviewModel> review;

    @OneToMany(mappedBy = "user")
    private Collection<SubscribeModel> subscribe;

    @OneToMany(mappedBy = "user")
    private Collection<CoachStatsModel> coachStats;

    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roles;

    public UserModel() {
    }

    public UserModel(long id, String surname, String secondname, String name, String email, String password, Date dateBirth, boolean active, String image, double height, double weight, String position, SportProfileModel sportProfile, Collection<EventModel> event, List<EventModel> eventmark, Collection<OnceTrainModel> onceTrainuser, Collection<OnceTrainModel> onceTraincoach, Collection<TrainPlanModel> trainPlanuser, Collection<TrainPlanModel> trainPlancoach, Collection<TaskModel> taskuser, Collection<TaskModel> taskcoach, Collection<ReviewModel> review, Collection<SubscribeModel> subscribe, Collection<CoachStatsModel> coachStats, Set<RoleEnum> roles) {
        this.id = id;
        this.surname = surname;
        this.secondname = secondname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateBirth = dateBirth;
        this.active = active;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.position = position;
        this.sportProfile = sportProfile;
        this.event = event;
        this.eventmark = eventmark;
        this.onceTrainuser = onceTrainuser;
        this.onceTraincoach = onceTraincoach;
        this.trainPlanuser = trainPlanuser;
        this.trainPlancoach = trainPlancoach;
        this.taskuser = taskuser;
        this.taskcoach = taskcoach;
        this.review = review;
        this.subscribe = subscribe;
        this.coachStats = coachStats;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public SportProfileModel getSportProfile() {
        return sportProfile;
    }

    public void setSportProfile(SportProfileModel sportProfile) {
        this.sportProfile = sportProfile;
    }

    public Collection<EventModel> getEvent() {
        return event;
    }

    public void setEvent(Collection<EventModel> event) {
        this.event = event;
    }

    public List<EventModel> getEventmark() {
        return eventmark;
    }

    public void setEventmark(List<EventModel> eventmark) {
        this.eventmark = eventmark;
    }

    public Collection<OnceTrainModel> getOnceTrainuser() {
        return onceTrainuser;
    }

    public void setOnceTrainuser(Collection<OnceTrainModel> onceTrainuser) {
        this.onceTrainuser = onceTrainuser;
    }

    public Collection<OnceTrainModel> getOnceTraincoach() {
        return onceTraincoach;
    }

    public void setOnceTraincoach(Collection<OnceTrainModel> onceTraincoach) {
        this.onceTraincoach = onceTraincoach;
    }

    public Collection<TrainPlanModel> getTrainPlanuser() {
        return trainPlanuser;
    }

    public void setTrainPlanuser(Collection<TrainPlanModel> trainPlanuser) {
        this.trainPlanuser = trainPlanuser;
    }

    public Collection<TrainPlanModel> getTrainPlancoach() {
        return trainPlancoach;
    }

    public void setTrainPlancoach(Collection<TrainPlanModel> trainPlancoach) {
        this.trainPlancoach = trainPlancoach;
    }

    public Collection<TaskModel> getTaskuser() {
        return taskuser;
    }

    public void setTaskuser(Collection<TaskModel> taskuser) {
        this.taskuser = taskuser;
    }

    public Collection<TaskModel> getTaskcoach() {
        return taskcoach;
    }

    public void setTaskcoach(Collection<TaskModel> taskcoach) {
        this.taskcoach = taskcoach;
    }

    public Collection<ReviewModel> getReview() {
        return review;
    }

    public void setReview(Collection<ReviewModel> review) {
        this.review = review;
    }

    public Collection<SubscribeModel> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Collection<SubscribeModel> subscribe) {
        this.subscribe = subscribe;
    }

    public Collection<CoachStatsModel> getCoachStats() {
        return coachStats;
    }

    public void setCoachStats(Collection<CoachStatsModel> coachStats) {
        this.coachStats = coachStats;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }
}
