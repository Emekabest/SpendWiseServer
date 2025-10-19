package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import org.example.controller.PingController;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Import({ PingController.class })
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}