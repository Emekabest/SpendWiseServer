package org.example.service;


import org.example.model.Budget;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    public void add(User user) {
        String hashedPin = passwordEncoder.encode(user.getPin());


        user.setPin(hashedPin);
        this.userRepository.save(user);
    }



    public Optional<User> get(String email) {

        return this.userRepository.findByEmail(email);
    }
    public User getUser(String email){
        User user = new User();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){

            user = userOptional.get();

        }



        return user;
    }



    public void update(User user){

        this.userRepository.save(user);
    }


    public boolean isExistsByEmail(String email) {

        return this.userRepository.existsByEmail(email);
    }

}