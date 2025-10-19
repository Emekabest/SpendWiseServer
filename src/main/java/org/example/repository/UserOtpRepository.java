package org.example.repository;

import org.example.model.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Integer> {

    Optional<UserOtp> findTopByEmailOrderByCreatedTimeDesc(String email);

}
