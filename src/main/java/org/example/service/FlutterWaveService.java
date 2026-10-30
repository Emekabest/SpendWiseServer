package org.example.service;

import org.example.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;


@Service
public class FlutterWaveService {

    @Value("${flw.secret.key}")
    private String FLW_SECRETKEY;


    private final RestClient transferRestClient;

    public FlutterWaveService(@Qualifier("flutterwaveRestClient") RestClient restClient ){

        this.transferRestClient = restClient;

    }



    public String opayPayment(PaymentMethod paymentMethod, String userEmail){
        RestClient restClient = RestClient.create();

        String response = restClient.post().uri("https://api.flutterwave.com/v3/charges?type=opay")
                .header("Authorization", "Bearer " + FLW_SECRETKEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "amount", paymentMethod.getAmount(),
                        "email", userEmail,
                        "tx_ref", paymentMethod.getTransactionRef(),
                        "currency", "NGN",
                        "redirect_url", "https://spendwiseserver.up.railway.app/opaypaymentcallback"
                ))
                .retrieve()
                .body(String.class);


        return response;
    }


    public String bankTransferPayment(String userEmail, int amount, String transactionRef){
        RestClient restClient = RestClient.create();

        String response = restClient.post().uri("https://api.flutterwave.com/v3/charges?type=bank_transfer")
                .header("Authorization", "Bearer " + FLW_SECRETKEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "amount", amount,
                        "email", userEmail,
                        "currency", "NGN",
                        "tx_ref", transactionRef
                ))
                .retrieve()
                .body(String.class);


        return response;
    }



    public String transfer(String accountNumber, String bank, int amount, String transactionRef){

        String response = transferRestClient.post().uri("https://api.flutterwave.com/v3/transfers")
                .header("Authorization", "Bearer " + FLW_SECRETKEY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of(
                    "account_bank", bank,
                    "account_number", accountNumber,
                    "amount", amount,
                    "currency", "NGN",
                    "debit_currency", "NGN",
                    "beneficiary_name", "SpendWise",
                    "reference", transactionRef,
                    "narration", "SpendWise Withdraw"
                ))
                .retrieve()
                .body(String.class);


        System.out.println("This is response::"+response);

        return response;
    }

    public String testFixie() {
        return transferRestClient.get()
                .uri("https://api.ipify.org")
                .retrieve()
                .body(String.class);
    }

}
