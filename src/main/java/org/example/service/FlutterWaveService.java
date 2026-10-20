package org.example.service;

import org.example.model.Opay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;


@Service
public class FlutterWaveService {

    @Value("${flw.secret.key}")
    private String FLW_SECRETKEY;


    public String opayPayment(Opay opay){
        RestClient restClient = RestClient.create();

        String response = restClient.post().uri("https://api.flutterwave.com/v3/charges?type=opay")
                .header("Authorization", "Bearer " + FLW_SECRETKEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "amount", opay.getAmount(),
                        "email", opay.getUserEmail(),
                        "tx_ref", opay.getTransactionRef(),
                        "currency", "NGN"
                ))
                .retrieve()
                .body(String.class);


        System.out.println("This is response::"+response);

        return "Successful";
    }



}
