package com.example.sport_club.controller;

import com.example.sport_club.model.*;
import com.example.sport_club.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Controller
@RequestMapping()
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER', 'COACH')")
public class UsersController {
    @Autowired
    public SubscribeRepo subscribeRepo;
    @Autowired
    public CoachStatsRepo coachStatsRepo;
    @Autowired
    public UserRepo userRepo;
    @Autowired
    public EventRepo eventRepo;

    @Autowired
    public TrainingRepo trainingRepo;
    @Autowired
    public OnceTrainRepo onceRepo;
    @Autowired
    public TrainPlanRepo planRepo;
    @Autowired
    public TrainTypeRepo typeRepo;
    @Autowired
    public TaskRepo taskRepo;
    @Autowired
    public SportProfileRepo spRepo;
    @Autowired
    public ReviewRepo reviewRepo;

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("/mainwindow")
    public String listhome(Model model) {
        Iterable<EventModel> eventModels = eventRepo.findAll();
        model.addAttribute("events", eventModels);
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
        return "mainwindow";
    }

    @GetMapping("/mainwindow/addmark/{id}")
    public String addmark(@PathVariable("id") long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        EventModel eventModel = eventRepo.findById(id);
        userModel.getEvent().add(eventModel);
        eventModel.getUserevent().add(userModel);
        try {
            eventRepo.save(eventModel);
            userRepo.save(userModel);
        }
        catch (NullPointerException e){
            return "redirect:/mainwindow";
        }
        return "redirect:/mainwindow";
    }

    @GetMapping("/allcoaches")
    public String listcoaches(Model model) {
        Iterable<UserModel> userModels = userRepo.findByPositionContainingIgnoreCase("Тренер");
        model.addAttribute("users", userModels);
        return "allcoaches";
    }

    @GetMapping("/review/{id}")
    public String showreview(@PathVariable("id") long id, Model model) {
        UserModel userModel = userRepo.findById(id);
        CoachStatsModel coachStatsModel = coachStatsRepo.findByUser(userModel);
        model.addAttribute("cs", coachStatsModel);
        Iterable<ReviewModel> reviewModel = reviewRepo.findByCoachstats(coachStatsModel);
        model.addAttribute("reviews", reviewModel);
        return "reviews";
    }

    @GetMapping("/task/add/{id}")
    public String writetask(@PathVariable("id") long id, Model model) {
        UserModel userModel = userRepo.findById(id);
        model.addAttribute("coach", userModel);
        TaskModel taskModel = new TaskModel();
        model.addAttribute("task", taskModel);
        return "task";
    }

    @PostMapping("/task/add/{id}")
    public String add2(
            @ModelAttribute("task") TaskModel taskModel, @PathVariable("id") long id,
            BindingResult bindingResult) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        taskModel.setUsertask(userModel);
        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        taskModel.setDateTime(localDateTime);
        UserModel coachmodel = userRepo.findById(id);
        taskModel.setCoachtask(coachmodel);
        try {
            taskRepo.save(taskModel);
        }
        catch (NullPointerException e){
            return "redirect:/allcoaches";
        }
        return "redirect:/allcoaches";
    }

    @GetMapping("/subscribeshow")
    public String subs() {
        return "subscribeshow";
    }

    @GetMapping("/profile")
    public String checkprofile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        model.addAttribute("user", userModel);
        Iterable<TrainPlanModel> trainPlanModel = planRepo.findByUserplan(userModel);
        model.addAttribute("plans", trainPlanModel);
        Iterable<EventModel> eventModels = eventRepo.findByUsereventContainingIgnoreCase(userModel);
        model.addAttribute("events", eventModels);
        return "profile";
    }

    @GetMapping("/event/deletemark/{id}")
    public String deletemark(@PathVariable("id") long id, Model model) {
        EventModel eventModel = eventRepo.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        eventModel.getUserevent().remove(userModel);
        userModel.getEvent().remove(eventModel);
        try {
            eventRepo.save(eventModel);
            userRepo.save(userModel);
        }
        catch (NullPointerException e){
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @GetMapping("/profile/update/{id}")
    public String showEditForm10(@PathVariable("id") long id, Model model) {
        UserModel userModel = userRepo.findById(id);

        model.addAttribute("user", userModel);
        return "/profileupdate";
    }

    @PostMapping("/profile/update/{id}")
    public String edit10(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("users") UserModel userModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {

            return "/profileupdate";
        }

        userModel.setActive(true);
        userModel.setId(id);
        try {
            userRepo.save(userModel);
        }
        catch (NullPointerException e){
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @GetMapping("/review/add/{id}")
    public String writereview(@PathVariable("id") long id, Model model) {
        ReviewModel reviewModel = new ReviewModel();
        UserModel coachmodel = userRepo.findById(id);
        System.out.println(id);
        System.out.println(coachmodel);
        CoachStatsModel coachStatsModel = coachStatsRepo.findByUser(coachmodel);
        System.out.println(coachStatsModel);
        reviewModel.setCoachstats(coachStatsModel);
        model.addAttribute("review", reviewModel);
        return "addreview";
    }

    @PostMapping("/review/add/{id}")
    public String addreview(
            @ModelAttribute("review") ReviewModel reviewModel, @PathVariable("id") long id,
            BindingResult bindingResult) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        reviewModel.setUser(userModel);

        LocalDateTime localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        reviewModel.setDateTime(localDateTime);

        CoachStatsModel coachStatsModel = coachStatsRepo.findById(id);
        System.out.println(coachStatsModel);
        reviewModel.setCoachstats(coachStatsModel);
        Random random = new Random();
        reviewModel.setId(random.nextInt());
        try {
            reviewRepo.save(reviewModel);
        }
        catch (NullPointerException e){
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }
}
