package org.example.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal availableBalance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal savings;


    public Wallet(){


    }

    public Wallet (int userId, BigDecimal availableBalance, BigDecimal savings){

        this.userId = userId;
        this.availableBalance = availableBalance;
        this.savings = savings;

    }


    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setAvailableBalance(BigDecimal availableBalance){
        this.availableBalance = availableBalance;
    }
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setSavings(BigDecimal savings){
        this.savings = savings;
    }
    public BigDecimal getSavings(){

        return this.savings;
    }

}
