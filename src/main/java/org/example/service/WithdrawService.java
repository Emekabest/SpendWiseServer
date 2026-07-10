package org.example.service;

import org.example.event.WithdrawEvent;
import org.example.model.Budget;
import org.example.model.MyEmail;
import org.example.model.User;
import org.example.model.Withdraw;
import org.example.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawService {

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    private EmailService emailService;


    public WithdrawService(ApplicationEventPublisher publisher){
        this.publisher = publisher;

    }


    public void add(Withdraw withdraw){

        withdrawRepository.save(withdraw);

        publisher.publishEvent(new WithdrawEvent(withdraw));
    }


    public List<Withdraw> get(){

      return  withdrawRepository.findAll();
    }

    public void update(Withdraw withdraw){

        withdrawRepository.save(withdraw);
    }


    public void request(Withdraw withdraw){

        try {
            MyEmail myEmail = new MyEmail();
            myEmail.setFrom("dimmaoguike@gmail.com");
            myEmail.setSubject("Request To Withdraw");
            myEmail.setMessage("A withdrawal request from " + withdraw.getEmail() +"\n" +
                    "Account Number: " + withdraw.getAccountNumber() + "\n" +
                    "Bank Name: " + withdraw.getBankName() + "\n" +
                    "Account Name: " + withdraw.getAccountName() + "\n" +
                    "Amount: " + withdraw.getAmount() + "\n" +
                    "ID: " + withdraw.getId()
            );
            myEmail.setTo("josephemekabest2611@gmail.com");

            emailService.sendEmail(myEmail);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }



    public void updateAllBalance(Withdraw withdraw){
        User user = userService.getUser(withdraw.getEmail());
        Budget budget = budgetService.get(withdraw.getEmail());

        user.setBalance(user.getBalance() - withdraw.getAmount());
        budget.setAccessAmount(budget.getAccessAmount() - withdraw.getAmount());


        userService.update(user);
        budgetService.add(budget);
    }

}
