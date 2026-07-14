package org.example.service;

import org.example.model.MyEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.Map;


@Service
public class EmailService {

    @Value("${resend.apikey}")
    private String resendApiKey;

    private String emailSender = "";




    public String sendEmail(MyEmail myEmail) throws IOException {

    try{
        WebClient client = WebClient.create("https://api.resend.com");

        System.out.println("Resend Api Key::"+resendApiKey);

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


    }catch (WebClientResponseException e){

        System.out.println("StatusCode::"+e.getStatusCode());
        System.out.println("ResponseBody::"+e.getResponseBodyAsString());

    }catch (java.lang.Exception e) {

        System.out.println("An error occurred::"+e.getMessage());

        throw new RuntimeException(e);
    }

        return "";
    }
}