package com.example.sport_club.controller;

import com.example.sport_club.model.*;
import com.example.sport_club.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@Controller
@RequestMapping()
@PreAuthorize("hasAnyAuthority('COACH')")
public class CoachController {

    @Autowired
    public CoachStatsRepo Repo;
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
    @PersistenceContext // Внедрение EntityManager
    private EntityManager entityManager;

//HOME
    @GetMapping("/coaches")
    public String listhome(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        model.addAttribute("user", userModel);
        CoachStatsModel coachStatsModels = Repo.findByUser(userModel);
        model.addAttribute("stat", coachStatsModels);
        return "coach/home";
    }

//ALTRAININGS
    @GetMapping("coach/alltrainings")
    public String listallTrainings(Model model) {
        Iterable<TrainingModel> trainingModels = trainingRepo.findByStatusContainingIgnoreCase("Планируется");
        model.addAttribute("trainings", trainingModels);

        Iterable<OnceTrainModel> onceTrainModels = onceRepo.findByStatusContainingIgnoreCase("Планируется");
        model.addAttribute("oncetrainings", onceTrainModels);
        return "coach/alltrainings/all";
    }
//EVENT
    @GetMapping("coach/event")
    public String listallEvent(Model model) {
        Iterable<EventModel> eventModels = eventRepo.findAll();
        model.addAttribute("events", eventModels);
        return "coach/event/all";
    }
//TRAINING

    @GetMapping("coach/training")
    public String listempty(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        Iterable<TrainingModel> trainingModels = trainingRepo.findByStatusAndTrainplanCoachplan("Планируется", userModel);
        model.addAttribute("trainings", trainingModels);
        return "coach/training/all";
    }

    @GetMapping("coach/training/add")
    public String showAddForm(Model model) {
        TrainingModel trainingModel = new TrainingModel();
        model.addAttribute("training", trainingModel);

        Iterable<TrainTypeModel> trainTypeModels = typeRepo.findAll();
        model.addAttribute("types", trainTypeModels);

        return "coach/training/add";
    }

    @PostMapping("coach/training/add")
    public String add(
            @ModelAttribute("training") TrainingModel trainingModel,
            BindingResult bindingResult) {
        trainingModel.setStatus("Планируется");
        try{
            trainingRepo.save(trainingModel);
        }
        catch (NullPointerException e){
            return "redirect:/coach/training";
        }
        return "redirect:/coach/training";
    }

    @GetMapping("coach/training/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        TrainingModel trainingModel = trainingRepo.findById(id).orElse(null);
        if (trainingModel == null) {
            return "redirect:/coach/training";
        }
        Iterable<TrainTypeModel> trainTypeModels = typeRepo.findAll();
        model.addAttribute("types", trainTypeModels);

        model.addAttribute("training", trainingModel);
        return "coach/training/update";
    }

    @PostMapping("coach/training/edit/{id}")
    public String edit(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("training") TrainingModel trainingModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            Iterable<TrainTypeModel> trainTypeModels = typeRepo.findAll();
            model.addAttribute("types", trainTypeModels);

            return "coach/training/update";
        }
        trainingModel.setId(id);
        trainingModel.setStatus("Проведена");

        try {
            trainingRepo.save(trainingModel);
        }
        catch (NullPointerException e){
            return "redirect:/coach/training";
        }
        return "redirect:/coach/training";
    }

    @GetMapping("coach/training/delete/{id}")
    public String deletetraining(@PathVariable("id") long id) {
        try {
            trainingRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/coach/training";
        }
        return "redirect:/coach/training";
    }

    //ONCETRAINING

    @GetMapping("coach/oncetraining")
    public String listempty2(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        Iterable<OnceTrainModel> onceTrainModels = onceRepo.findByStatusAndCoachonce("Планируется", userModel);
        model.addAttribute("trainings", onceTrainModels);
        return "coach/oncetraining/all";
    }

    @GetMapping("coach/oncetraining/add")
    public String showAddForm2(Model model) {
        OnceTrainModel onceTrainModel = new OnceTrainModel();
        model.addAttribute("training", onceTrainModel);

        Iterable<TrainTypeModel> trainTypeModels = typeRepo.findAll();
        model.addAttribute("types", trainTypeModels);

        Iterable<UserModel> userModels = userRepo.findAll();
        model.addAttribute("users", userModels);

        return "coach/oncetraining/add";
    }

    @PostMapping("coach/oncetraining/add")
    public String add2(
            @ModelAttribute("oncetraining") OnceTrainModel onceTrainModel,
            BindingResult bindingResult) {
        onceTrainModel.setStatus("Планируется");

        try {
            onceRepo.save(onceTrainModel);
        }
        catch (NullPointerException e){
            return "redirect:/coach/training";
        }
        return "redirect:/coach/training";
    }

    @GetMapping("coach/oncetraining/close/{id}")
    public String close2(@PathVariable("id") long id) {
        OnceTrainModel onceTrainModel = onceRepo.findById(id).orElse(null);
        onceTrainModel.setStatus("Проведена");
        try {
            onceRepo.save(onceTrainModel);
        }
        catch (NullPointerException e){
            return "redirect:/coach/training";
        }
        return "redirect:/coach/oncetraining";
    }

    @GetMapping("coach/oncetraining/delete/{id}")
    public String deleteOnceTraining(@PathVariable("id") long id) {
        try {
            onceRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/coach/oncetraining";
        }
        return "redirect:/coach/oncetraining";
    }

    //TASK

    @GetMapping("coach/task")
    public String listtasks(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        Iterable<TaskModel> taskModels = taskRepo.findByCoachtask(userModel);
        model.addAttribute("tasks", taskModels);
        return "coach/task/all";
    }

    @GetMapping("coach/task/info/{id}")
    public String showEditForm3(@PathVariable("id") long id, Model model) {
        TaskModel taskModel = taskRepo.findById(id).orElse(null);
        if (taskModel == null) {
            return "redirect:/coach/task";
        }

        model.addAttribute("task", taskModel);
        return "coach/task/info";
    }

    @GetMapping("coach/task/add")
    public String showAddForm5(Model model) {
        TrainPlanModel trainPlanModel = new TrainPlanModel();
        model.addAttribute("trainplan", trainPlanModel);

        Iterable<UserModel> userModels = userRepo.findAll();
        model.addAttribute("users", userModels);

        return "coach/task/add";
    }

    @PostMapping("coach/task/add")
    public String add4(
            @ModelAttribute("trainplan") TrainPlanModel trainPlanModel,
            BindingResult bindingResult) {
        try {
            planRepo.save(trainPlanModel);
        }
        catch (NullPointerException e){
            return "redirect:/coach/task";
        }
        return "redirect:/coach/task";
    }

    @GetMapping("coach/task/delete/{id}")
    public String deleteTask(@PathVariable("id") long id) {
        try {
            taskRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/coach/task";
        }
        return "redirect:/coach/task";
    }

    //TRAINPLAN

    @GetMapping("coach/trainplan")
    public String listplans(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "null";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserModel userModel = userRepo.findByEmail(username);
        Iterable<TrainPlanModel> trainPlanModels = planRepo.findByCoachplan(userModel);
        model.addAttribute("plans", trainPlanModels);
        return "coach/trainplan/all";
    }
}
