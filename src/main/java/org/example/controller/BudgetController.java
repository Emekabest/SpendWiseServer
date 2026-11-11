package org.example.controller;

import org.example.dto.BudgetRequest;
import org.example.model.Budget;
import org.example.service.BudgetService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BudgetController{

    @Autowired
    private final BudgetService budgetService;

    @Autowired
    private UserService userService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;

    }


    @PostMapping("/api/v1/budget/create")
    public ResponseEntity<?> createBudget(@RequestBody BudgetRequest request, Authentication authentication){
        // Get the authenticated user's ID here

        String userEmail = authentication.getName();
        int userId = userService.getUser(userEmail).getId();

        Budget budget = budgetService.createBudget(userId, request);

        return ResponseEntity.ok(budget);
    }


}