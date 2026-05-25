package org.example.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.example.model.MyEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import java.util.Map;

import java.io.IOException;


@Service
public class EmailService {

    @Value("${resend.apikey}")
    private String resendApiKey;

    private String emailSender = "";




    public String sendEmail(MyEmail myEmail) throws IOException {

    try{
        WebClient client = WebClient.create("https://api.resend.com");

        client.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + resendApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "from", myEmail.getFrom(),
                        "to", myEmail.getTo(),
                        "subject", myEmail.getSubject(),
                        "html", "<p>" + myEmail.getMessage() + "</p>"
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();


    } catch (java.lang.Exception e) {
        throw new RuntimeException(e);
    }

        return "";
    }
}