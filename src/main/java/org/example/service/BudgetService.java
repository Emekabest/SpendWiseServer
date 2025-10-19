package org.example.service;

import org.example.model.Budget;
import org.example.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;


    public void add(Budget budget){

        budgetRepository.save(budget);
        budgetRepository.flush();
    }

    public void update(Budget budget){

        budgetRepository.save(budget);
        budgetRepository.flush();
    }

    public Budget get(String email){
        Budget budget = new Budget();

        Optional<Budget> budgetOptional = budgetRepository.findByEmail(email);

        if (budgetOptional.isPresent()){
            budget = budgetOptional.get();

        }


        return budget;
    }


}
