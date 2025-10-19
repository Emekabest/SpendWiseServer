package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "withdraws")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;

    private String accountNumber;
    private String accountName;
    private String bankName;
    private int amount;

    private boolean settled;
}