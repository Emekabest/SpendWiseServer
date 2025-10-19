package org.example.controller;


import org.example.model.DateTime;
import org.example.model.Notification;
import org.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping({"/set-notification"})
    public String notification(@RequestBody Notification notification){

        try{

            notification.setTime(DateTime.getLocalDateTime());

            notificationService.add(notification);

            return "Successful";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
