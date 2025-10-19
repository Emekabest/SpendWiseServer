package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "userotp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String otp;
    private LocalDateTime createdTime;
    private LocalDateTime expiryTime;

}
