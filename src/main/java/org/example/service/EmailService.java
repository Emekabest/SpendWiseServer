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

import java.io.IOException;


@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;



    public String sendEmail(MyEmail myEmail) throws IOException {

        Email from = new Email(myEmail.getFrom()); // use verified domain or Gmail for testing
        String subject = myEmail.getSubject();
        Email to = new Email(myEmail.getTo());

        Content content = new Content("text/plain", myEmail.getMessage());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();


        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            return "Status Code: " + response.getStatusCode() +
                    " | Body: " + response.getBody() +
                    " | Headers: " + response.getHeaders();

        } catch (IOException ex) {
            throw ex;
        }
    }
}