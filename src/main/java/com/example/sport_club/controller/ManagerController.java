package com.example.sport_club.controller;

import com.example.sport_club.model.*;
import com.example.sport_club.repo.*;
import com.example.sport_club.service.CsvExportService;
import com.example.sport_club.service.CsvExportService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
public class ManagerController {
    @Autowired
    public SubscribeRepo subscribeRepo;
    @Autowired
    public EventRepo eventRepo;
    @Autowired
    public UserRepo userRepo;
    @Autowired
    public SPRepo spRepo;
    @Autowired
    public TrainingRepo trainingRepo;
    @Autowired
    public OnceTrainRepo onceRepo;
    @Autowired
    public TrainPlanRepo planRepo;
    @GetMapping()
    public String login() {return "/manager/home";}

    @Autowired
    private CsvExportService csvExportService;

    @Autowired
    private CsvExportService1 csvExportService1;

    //HISTORY

    @GetMapping("/trainhistory")
    public String listallTrainings(Model model) {
        Iterable<TrainingModel> trainingModels = trainingRepo.findByStatusContainingIgnoreCase("close");
        model.addAttribute("trainings", trainingModels);

        Iterable<OnceTrainModel> onceTrainModels = onceRepo.findByStatusContainingIgnoreCase("close");
        model.addAttribute("oncetrainings", onceTrainModels);
        return "/manager/trainingsHistory/all";
    }

    @GetMapping( "/exportTrains")
    public void getAllTrainingsInCsv(HttpServletResponse servletResponse) throws IOException {

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"trainings.csv\"");
        csvExportService.writeTrainingsToCsv(servletResponse.getWriter());
    }

    @GetMapping( "/exportOnes")
    public void getAllOncesInCsv(HttpServletResponse servletResponse) throws IOException {

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"onces.csv\"");
        csvExportService1.writeOncesToCsv(servletResponse.getWriter());
    }

    @GetMapping("/clearOnes")
    public String listones(Model model) {
        Iterable<OnceTrainModel> onceTrainModels = onceRepo.findByStatusContainingIgnoreCase("close");
        onceRepo.deleteAll(onceTrainModels);
        return "/manager/trainingsHistory/all";
    }

    @GetMapping("/clearTrainings")
    public String listtrains(Model model) {
        Iterable<TrainingModel> trainingModels = trainingRepo.findByStatusContainingIgnoreCase("close");
        trainingRepo.deleteAll(trainingModels);
        return "/manager/trainingsHistory/all";
    }

///EMPLOYEES

    @GetMapping("/employees")
    public String listTickets(Model model) {
        Iterable<UserModel> userModels = userRepo.findByPositionNotNull();
        model.addAttribute("users", userModels);
        return "manager/employees/all";
    }


    @GetMapping("/employees/search")
    public String searchProducts(@RequestParam(name = "email") String email, Model model) {
        Iterable<UserModel> userModels = userRepo.findByEmailContainingIgnoreCase(email);
        model.addAttribute("users", userModels);
        return "manager/employees/all";
    }

    ///EVENT

    @GetMapping("event")
    public String listTicket(Model model) {
        Iterable<EventModel> eventModels = eventRepo.findAll();
        model.addAttribute("events", eventModels);
        return "manager/event/all";
    }

    @GetMapping("event/add")
    public String showAddForm(Model model) {
        EventModel eventModel = new EventModel();
        model.addAttribute("event", eventModel);

        // Получите список классов из репозитория и добавьте его в модель
        Iterable<UserModel> userModels = userRepo.findByPositionContainingIgnoreCase("Тренер");
        model.addAttribute("users", userModels);

        return "manager/event/add";
    }


    @PostMapping("event/add")
    public String add(
            @ModelAttribute("event") EventModel eventModel,
            BindingResult bindingResult) {
        try {
            eventRepo.save(eventModel);
        }
        catch (NullPointerException e){
            return "redirect:/manager/event";
        }

        return "redirect:/manager/event";
    }


    @GetMapping("event/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        EventModel eventModel = eventRepo.findById(id);
        if (eventModel == null) {
            return "redirect:/manager/event";
        }
        Iterable<UserModel> userModels = userRepo.findByPositionContainingIgnoreCase("Тренер");
        model.addAttribute("users", userModels);

        model.addAttribute("event", eventModel);
        return "manager/event/update";
    }

    @PostMapping("event/edit/{id}")
    public String edit(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("event") EventModel eventModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            Iterable<UserModel> userModels = userRepo.findByPositionContainingIgnoreCase("Тренер");
            model.addAttribute("users", userModels);

            return "manager/event/update";
        }
        eventModel.setId(id);
        try {
            eventRepo.save(eventModel);
        }
        catch (NullPointerException e){
            return "redirect:/manager/event";
        }
        return "redirect:/manager/event";
    }


    @GetMapping("event/delete/{id}")
    public String deleteHero(@PathVariable("id") long id) {
        try {
            eventRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/manager/event";
        }
        return "redirect:/manager/event";
    }

    @GetMapping("event/search")
    public String searchPro(@RequestParam(name = "name") String name, Model model) {
        Iterable<EventModel> eventModels = eventRepo.findByNameContainingIgnoreCase(name);
        model.addAttribute("events", eventModels);
        return "manager/event/all";
    }

    ///Subscribe

    @GetMapping("subscribe")
    public String listSubscribe(Model model) {
        Iterable<SubscribeModel> subscribeModels = subscribeRepo.findAll();
        model.addAttribute("subscribes", subscribeModels);
        Map<String, Integer> graphData = new TreeMap<>();

        Iterable<SubscribeModel> subscribeModelsjanuary = subscribeRepo.findByMonthContainingIgnoreCase("Январь");
        Iterator<SubscribeModel> itjan = subscribeModelsjanuary.iterator();
        int sumjan = 0;
        while (itjan.hasNext()) {
            itjan.next();
            sumjan++;
        }
        graphData.put("Январь", sumjan);

        Iterable<SubscribeModel> subscribeModelsfebruary = subscribeRepo.findByMonthContainingIgnoreCase("Февраль");
        Iterator<SubscribeModel> itfeb = subscribeModelsfebruary.iterator();
        int sumfeb = 0;
        while (itfeb.hasNext()) {
            itfeb.next();
            sumfeb++;
        }
        graphData.put("Февраль", sumfeb);

        Iterable<SubscribeModel> subscribeModelsmarch = subscribeRepo.findByMonthContainingIgnoreCase("Март");
        Iterator<SubscribeModel> itmarch = subscribeModelsmarch.iterator();
        int summarch = 0;
        while (itmarch.hasNext()) {
            itmarch.next();
            summarch++;
        }
        graphData.put("Март", summarch);

        Iterable<SubscribeModel> subscribeModelsapril = subscribeRepo.findByMonthContainingIgnoreCase("Апрель");
        Iterator<SubscribeModel> itapril = subscribeModelsapril.iterator();
        int sumapril = 0;
        while (itapril.hasNext()) {
            itapril.next();
            sumapril++;
        }
        graphData.put("Апрель", sumapril);

        Iterable<SubscribeModel> subscribeModelsmay = subscribeRepo.findByMonthContainingIgnoreCase("Май");
        Iterator<SubscribeModel> itmay = subscribeModelsapril.iterator();
        int summay = 0;
        while (itmay.hasNext()) {
            itmay.next();
            summay++;
        }
        graphData.put("Май", summay);

        Iterable<SubscribeModel> subscribeModelsjune = subscribeRepo.findByMonthContainingIgnoreCase("Июнь");
        Iterator<SubscribeModel> itjune = subscribeModelsjune.iterator();
        int sumjun = 0;
        while (itjune.hasNext()) {
            itjune.next();
            sumjun++;
        }
        graphData.put("Июнь", sumjun);

        Iterable<SubscribeModel> subscribeModelsjuly = subscribeRepo.findByMonthContainingIgnoreCase("Июль");
        Iterator<SubscribeModel> itjuly = subscribeModelsjuly.iterator();
        int sumjul = 0;
        while (itjuly.hasNext()) {
            itjuly.next();
            sumjul++;
        }
        graphData.put("Июль", sumjul);

        Iterable<SubscribeModel> subscribeModelsaugust = subscribeRepo.findByMonthContainingIgnoreCase("Август");
        Iterator<SubscribeModel> itaugust = subscribeModelsaugust.iterator();
        int sumaugust = 0;
        while (itaugust.hasNext()) {
            itaugust.next();
            sumaugust++;
        }
        graphData.put("Август", sumaugust);

        Iterable<SubscribeModel> subscribeModelssept = subscribeRepo.findByMonthContainingIgnoreCase("Сентябрь");
        Iterator<SubscribeModel> itsept = subscribeModelssept.iterator();
        int sumsept = 0;
        while (itsept.hasNext()) {
            itsept.next();
            sumsept++;
        }
        graphData.put("Сентябрь", sumsept);

        Iterable<SubscribeModel> subscribeModelsoct = subscribeRepo.findByMonthContainingIgnoreCase("Октябрь");
        Iterator<SubscribeModel> itoct = subscribeModelsoct.iterator();
        int sumoct = 0;
        while (itoct.hasNext()) {
            itoct.next();
            sumoct++;
        }
        graphData.put("Октябрь", sumoct);

        Iterable<SubscribeModel> subscribeModelnov = subscribeRepo.findByMonthContainingIgnoreCase("Ноябрь");
        Iterator<SubscribeModel> itnov = subscribeModelnov.iterator();
        int sumnov = 0;
        while (itnov.hasNext()) {
            itnov.next();
            sumnov++;
        }
        graphData.put("Ноябрь", sumnov);

        Iterable<SubscribeModel> subscribeModeldec = subscribeRepo.findByMonthContainingIgnoreCase("Декабрь");
        Iterator<SubscribeModel> itdec = subscribeModeldec.iterator();
        int sumdec = 0;
        while (itdec.hasNext()) {
            itdec.next();
            sumdec++;
        }
        graphData.put("Декабрь", sumdec);

        model.addAttribute("chartData", graphData);
        return "manager/subscribe/all";
    }

    @GetMapping("subscribe/add")
    public String showAdd(Model model) {
        SubscribeModel subscribeModel = new SubscribeModel();
        model.addAttribute("subscribe", subscribeModel);

        // Получите список классов из репозитория и добавьте его в модель
        Iterable<UserModel> userModels = userRepo.findAll();
        model.addAttribute("users", userModels);

        return "manager/subscribe/add";
    }


    @PostMapping("subscribe/add")
    public String add(
            @ModelAttribute("subscribe") SubscribeModel subscribeModel,
            BindingResult bindingResult) {
        subscribeModel.setStatus("Открыт");
        try {
            subscribeRepo.save(subscribeModel);
        }
        catch (NullPointerException e){
            return "redirect:/manager/subscribe";
        }

        return "redirect:/manager/subscribe";
    }


    @GetMapping("subscribe/edit/{id}")
    public String showEdit(@PathVariable("id") long id, Model model) {
        SubscribeModel subscribeModel = subscribeRepo.findById(id).orElse(null);
        if (subscribeModel == null) {
            return "redirect:/manager/subscribe";
        }
        Iterable<UserModel> userModels = userRepo.findAll();
        model.addAttribute("users", userModels);

        model.addAttribute("subscribe", subscribeModel);
        return "manager/subscribe/update";
    }

    @PostMapping("subscribe/edit/{id}")
    public String edit(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("subscribe") SubscribeModel subscribeModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            Iterable<UserModel> userModels = userRepo.findAll();
            model.addAttribute("users", userModels);

            return "manager/subscribe/update";
        }
        subscribeModel.setId(id);
        subscribeModel.setStatus("Открыт");
        try {
            subscribeRepo.save(subscribeModel);
        }
        catch (NullPointerException e){
            return "redirect:/manager/subscribe";
        }
        return "redirect:/manager/subscribe";
    }

    @GetMapping("subscribe/close/{id}")
    public String closeSub(@PathVariable("id") long id) {
        SubscribeModel subscribeModel = subscribeRepo.findById(id).orElse(null);
        subscribeModel.setStatus("Закрыт");
        subscribeRepo.save(subscribeModel);
        return "redirect:/manager/subscribe";
    }

    @GetMapping("subscribe/delete/{id}")
    public String deleteHe(@PathVariable("id") long id) {
        try {
            subscribeRepo.deleteById(id);
        }
        catch (NullPointerException e){
            return "redirect:/manager/subscribe";
        }
        return "redirect:/manager/subscribe";
    }

    @GetMapping("subscribe/search")
    public String searchProd(@RequestParam(name = "month") String month, Model model) {
        Iterable<SubscribeModel> subscribeModels = subscribeRepo.findByMonthContainingIgnoreCase(month);
        model.addAttribute("subscribes", subscribeModels);
        return "manager/subscribe/all";
    }

    ///EVENTMARK

    @GetMapping("/eventmark")
    public String listEventMark(Model model) {
        Iterable<EventModel> eventModels = eventRepo.findAll();
        model.addAttribute("events", eventModels);
        Iterable<UserModel> userModels = userRepo.findAll();
        model.addAttribute("users", userModels);
        return "manager/eventmark/all";
    }
}
