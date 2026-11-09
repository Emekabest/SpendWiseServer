package org.example.service;

import org.example.model.*;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;


    public HomeService(){

    }

    public void add(){

    }

    public Home get(String email){
        Home home = new Home();

        Optional<User> userOptional = userRepository.findByEmail(email);
        List<Notification> notifications = notificationService.get(email);

        if (userOptional.isPresent()){
            User user = new User();
            user.setFirstname(userOptional.get().getFirstname());
            user.setLastname(userOptional.get().getLastname());


            home.setUser(user);
        }

        return home;
    }

}
