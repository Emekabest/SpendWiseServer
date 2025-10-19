package org.example.controller;


import org.example.model.Budget;
import org.example.model.Home;
import org.example.model.User;
import org.example.service.BudgetService;
import org.example.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class HomeController {

    public HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService){
        this.homeService = homeService;
    }


    @GetMapping({"/home"})
    public Home home(@RequestParam("email") String email){
        try {

            return homeService.get(email);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
