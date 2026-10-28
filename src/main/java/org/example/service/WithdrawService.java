package org.example.service;

import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;
import org.example.event.WithdrawEvent;
import org.example.model.DateTime;
import org.example.model.*;
import org.example.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WithdrawService {

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionService transactionService;

    private final ApplicationEventPublisher publisher;



    public WithdrawService(ApplicationEventPublisher publisher){
        this.publisher = publisher;

    }


    @Transactional
    public void add(Withdraw withdraw){

        withdrawRepository.save(withdraw);

        User user = userService.getUser(withdraw.getEmail());

        Transaction transaction = new Transaction();
        transaction.setTransactionRef(withdraw.getWithdrawTxnId());
        transaction.setUserId(user.getId());
        transaction.setType(TransactionType.withdrawal);
        transaction.setAmount(withdraw.getAmount());
        transaction.setTime(DateTime.getLocalDateTime());
        transaction.setStatus(TransactionStatus.pending);

        transactionService.add(transaction);


        publisher.publishEvent(new WithdrawEvent(withdraw));
    }


    public List<Withdraw> get(){

      return  withdrawRepository.findAll();
    }

    public void update(Withdraw withdraw){

        withdrawRepository.save(withdraw);
    }


}
