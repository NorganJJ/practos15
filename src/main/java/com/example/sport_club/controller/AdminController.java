package com.example.sport_club.controller;

import com.example.sport_club.model.*;
import com.example.sport_club.repo.*;
import com.example.sport_club.service.CsvUtilsSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping()
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

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

    //HOME

    @GetMapping("/admin")
    public String home() {return "/admin/home";}

    //INFOTABLES

    @GetMapping("/admin/infotables")
    public String listhome(Model model) {
        Iterable<SportProfileModel> sportProfileModels = spRepo.findAll();
        model.addAttribute("profiles", sportProfileModels);

        Iterable<TrainTypeModel> trainTypeModels = typeRepo.findAll();
        model.addAttribute("types", trainTypeModels);
        return "admin/infotables/all";
    }

    @GetMapping("admin/infotables/uploadSP")
    public String returnSP() {
        return "redirect:/admin/infotables";
    }

    @GetMapping("admin/infotables/uploadTT")
    public String returnTT() {
        return "redirect:/admin/infotables";
    }

    @PostMapping(value = "admin/infotables/uploadSP", consumes = "text/csv")
    public String uploadSimpleSP(@RequestBody InputStream body) throws IOException {
        spRepo.saveAll(CsvUtilsSP.read(SportProfileModel.class, body));
        return "redirect:/admin/infotables";
    }

    @PostMapping(value = "admin/infotables/uploadSP", consumes = "multipart/form-data")
    public String uploadMultipartSP(@RequestParam("file") MultipartFile file) throws IOException {
        spRepo.saveAll(CsvUtilsSP.read(SportProfileModel.class, file.getInputStream()));
        return "redirect:/admin/infotables";
    }

    @PostMapping(value = "admin/infotables/uploadTT", consumes = "text/csv")
    public String uploadSimpleTT(@RequestBody InputStream body) throws IOException {
        typeRepo.saveAll(CsvUtilsSP.read(TrainTypeModel.class, body));
        return "redirect:/admin/infotables";
    }

    @PostMapping(value = "admin/infotables/uploadTT", consumes = "multipart/form-data")
    public String uploadMultipartTT(@RequestParam("file1") MultipartFile file) throws IOException {
        typeRepo.saveAll(CsvUtilsSP.read(TrainTypeModel.class, file.getInputStream()));
        return "redirect:/admin/infotables";
    }

    //SPORTPROFILE

    @GetMapping("/admin/infotables/sportprofile/add")
    public String showAddSP(Model model) {
        SportProfileModel sportProfileModel = new SportProfileModel();
        model.addAttribute("profile", sportProfileModel);
        return "/admin/infotables/sportprofile/add";
    }

    @PostMapping("/admin/infotables/sportprofile/add")
    public String add1(
            @ModelAttribute("sport_profile") SportProfileModel sportProfileModel,
            BindingResult bindingResult) {
        try {
            spRepo.save(sportProfileModel);
        }
        catch (NullPointerException e){
            return "redirect:/admin/infotables";
        }
        return "redirect:/admin/infotables";
    }

    @GetMapping("/admin/infotables/sportprofile/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        SportProfileModel sportProfileModel = spRepo.findById(id).orElse(null);
        if (sportProfileModel == null) {
            return "redirect:/admin/infotables";
        }

        model.addAttribute("profile", sportProfileModel);
        return "/admin/infotables/sportprofile/update";
    }

    @PostMapping("/admin/infotables/sportprofile/edit/{id}")
    public String edit(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("sport_profile") SportProfileModel sportProfileModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {

            return "/admin/infotables/sportprofile/update";
        }
        sportProfileModel.setId(id);
        try {
            spRepo.save(sportProfileModel);
        }
        catch (NullPointerException e){
            return "redirect:/admin/infotables";
        }
        return "redirect:/admin/infotables";
    }

    @GetMapping("/admin/infotables/sportprofile/delete/{id}")
    public String deletesp(@PathVariable("id") long id) {
        try {
            spRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/admin/infotables";
        }
        return "redirect:/admin/infotables";
    }

    //TRAINTYPE

    @GetMapping("/admin/infotables/traintype/add")
    public String showAddTT(Model model) {
        TrainTypeModel trainTypeModel = new TrainTypeModel();
        model.addAttribute("type", trainTypeModel);
        return "/admin/infotables/traintype/add";
    }

    @PostMapping("/admin/infotables/traintype/add")
    public String add2(
            @ModelAttribute("traintype") TrainTypeModel trainTypeModel,
            BindingResult bindingResult) {
        try {
            typeRepo.save(trainTypeModel);
        }
        catch (NullPointerException e){
            return "redirect:/admin/infotables";
        }
        return "redirect:/admin/infotables";
    }

    @GetMapping("/admin/infotables/traintype/edit/{id}")
    public String showEditForm1(@PathVariable("id") long id, Model model) {
        TrainTypeModel trainTypeModel = typeRepo.findById(id).orElse(null);
        if (trainTypeModel == null) {
            return "redirect:/admin/infotables";
        }

        model.addAttribute("type", trainTypeModel);
        return "/admin/infotables/traintype/update";
    }

    @PostMapping("/admin/infotables/traintype/edit/{id}")
    public String edit1(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("traintype") TrainTypeModel trainTypeModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {

            return "/admin/infotables/traintype/update";
        }
        trainTypeModel.setId(id);

        try {
            typeRepo.save(trainTypeModel);
        }
        catch (NullPointerException e){
            return "redirect:/admin/infotables";
        }
        return "redirect:/admin/infotables";
    }

    @GetMapping("/admin/infotables/traintype/delete/{id}")
    public String deletetraining(@PathVariable("id") long id) {
        try {
            typeRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/admin/infotables";
        }
        return "redirect:/admin/infotables";
    }

    //EMPLOYEES

    @GetMapping("/admin/employees")
    public String listempl(Model model) {
        Iterable<UserModel> userModels = userRepo.findAll();
        model.addAttribute("users", userModels);
        return "admin/employees/all";
    }

    @GetMapping("/admin/employees/search")
    public String searchProducts(@RequestParam(name = "email") String email, Model model) {
        Iterable<UserModel> userModels = userRepo.findByEmailContainingIgnoreCase(email);
        model.addAttribute("users", userModels);
        return "admin/employees/all";
    }

    @GetMapping("/admin/employees/edit/{id}")
    public String showEditForm10(@PathVariable("id") long id, Model model) {
        UserModel userModel = userRepo.findById(id);

        Iterable<SportProfileModel> sportProfileModels = spRepo.findAll();
        model.addAttribute("profiles", sportProfileModels);

        model.addAttribute("roles", RoleEnum.values());

        model.addAttribute("user", userModel);
        return "/admin/employees/update";
    }

    @PostMapping("/admin/employees/edit/{id}")
    public String edit10(
            @RequestParam(name="roles[]", required = false) String[] roles,
            @PathVariable("id") long id,
            @Valid @ModelAttribute("users") UserModel userModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {

            return "/admin/employees/all";
        }

        userModel.getRoles().clear();
        if(roles != null)
        {
            for(String role: roles)
            {
                userModel.getRoles().add(RoleEnum.valueOf(role));
            }
        }

        String a = userModel.getRoles().toString();
        System.out.println(a);

        if(a.equals("[COACH]")){
            UserModel userModel1 = userRepo.findById(id);
            CoachStatsModel coachStatsModel = coachStatsRepo.findByUser(userModel1);
            if(coachStatsModel == null){
                CoachStatsModel coachStatsModel1 = new CoachStatsModel();
                coachStatsModel1.setUser(userModel1);
                coachStatsModel1.setKolvo(0);
                coachStatsModel1.setMidrate(0.00);
                coachStatsRepo.save(coachStatsModel1);
            }
        }

        userModel.setActive(true);
        userModel.setId(id);
        try {
            userRepo.save(userModel);
        }
        catch (NullPointerException e){
            return "redirect:/admin/employees";
        }
        return "redirect:/admin/employees";
    }

    @GetMapping("/admin/employees/delete/{id}")
    public String deleteemp(@PathVariable("id") long id) {
        try {
            userRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/admin/employees";
        }
        return "redirect:/admin/employees";
    }
}
