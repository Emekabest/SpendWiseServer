package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.*;
import org.example.repository.BudgetRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private NotificationService notificationService;


    public HomeService(){

    }

    public void add(){

    }

    public Home get(String email){
        Home home = new Home();

        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<Budget> budgetOptional = budgetRepository.findByEmail(email);
        List<Notification> notifications = notificationService.get(email);

        if (userOptional.isPresent()){
            User user = new User();
            user.setFirstname(userOptional.get().getFirstname());
            user.setLastname(userOptional.get().getLastname());

            user.setBalance(userOptional.get().getBalance());

            home.setUser(user);
        }


        if (budgetOptional.isPresent()){
            Budget budget = budgetOptional.get();

            if ("daily".equals(budget.getType())){

                budget.setAccessAmountRestoration(1);

            } else if ("weekly".equals(budget.getType())){

                budget.setAccessAmountRestoration(7);

            } else if ("monthly".equals(budget.getType())){

                budget.setAccessAmountRestoration(30);
            }

            budgetService.add(budget);

            home.setBudget(budget);
        }


        home.setNotifications(notifications);

        return home;
    }

}
