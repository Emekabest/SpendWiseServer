package org.example.controller;

import org.example.model.Opay;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpayController {


    @PostMapping({"/addfunds-opay"})
    public String Request(@RequestBody Opay opay){

        try {

            System.out.println(opay.getTransactionRef());
            System.out.println(opay.getAmount());
            System.out.println(opay.getUserEmail());

            return "";
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

}
