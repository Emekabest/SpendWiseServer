
package org.example.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "withdraw_pin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
public class WithdrawPin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "pin_hash", nullable = false)
    private String pinHash;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }
    public String getPinHash() {
        return pinHash;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
}
