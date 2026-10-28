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
    private BigDecimal totalBalance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal accessBalance;

    public Wallet(){


    }

    public Wallet (int userId, BigDecimal totalBalance, BigDecimal accessBalance){

        this.userId = userId;
        this.totalBalance=totalBalance;
        this.accessBalance=accessBalance;

    }


    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setTotalBalance(BigDecimal totalBalance){
        this.totalBalance = totalBalance;
    }
    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setAccessBalance(BigDecimal accessBalance) {
        this.accessBalance = accessBalance;
    }
    public BigDecimal getAccessBalance() {
        return accessBalance;
    }
}
