package org.example.controller;


import org.example.model.Budget;
import org.example.model.DateTime;
import org.example.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BudgetController {

    @Autowired
    BudgetService budgetService;




    @PostMapping({"/setbudget"})
    public String setBudget(@RequestBody Budget budget){

        try{

            budget.setAccessAmount(budget.getLimitAmount());
            budget.setAccessAmountLastRestoredDate(DateTime.getLocalDate());

            if ("daily".equals(budget.getType())){

                budget.setAccessAmountNextRestoredDate(1);

            } else if ("weekly".equals(budget.getType())){

                budget.setAccessAmountNextRestoredDate(7);

            } else if ("monthly".equals(budget.getType())){

                budget.setAccessAmountNextRestoredDate(30);
            }


            budgetService.add(budget);


            return "Successful";

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

}
