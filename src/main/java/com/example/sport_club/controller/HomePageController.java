package com.example.sport_club.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homepage")
@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'COACH')")
public class HomePageController {

    @GetMapping()
    public String login() {return "/homepage";}

}
