package org.example.service;

import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void add(Notification notification){

        notificationRepository.save(notification);
    }

    public List<Notification> get(String userEmail){

        return notificationRepository.findByUserEmail(userEmail);
    }


}
