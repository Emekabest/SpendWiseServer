package org.example.listener;


import org.example.event.WithdrawEvent;
import org.example.model.MyEmail;
import org.example.model.Withdraw;
import org.example.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    private final EmailService emailService;

    public EmailListener(EmailService emailService){
        this.emailService = emailService;

    }

    @EventListener
    public void sendWithdrawRequest(WithdrawEvent event){

        Withdraw withdraw = event.getWithdraw();

        try {
            MyEmail myEmail = new MyEmail();
            myEmail.setFrom("onboarding@resend.dev");
            myEmail.setSubject("Request To Withdraw");
            myEmail.setMessage("A withdrawal request from " + withdraw.getEmail() +"\n" +
                    "Account Number: " + withdraw.getAccountNumber() + "\n" +
                    "Bank Name: " + withdraw.getBankName() + "\n" +
                    "Account Name: " + withdraw.getAccountName() + "\n" +
                    "Amount: " + withdraw.getAmount() + "\n" +
                    "ID: " + withdraw.getId()
            );
            myEmail.setTo("oguikejosephchukwuemeka@gmail.com");

            emailService.sendEmail(myEmail);

            System.out.println("Withdrawal request email sent successfully...");

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

}
