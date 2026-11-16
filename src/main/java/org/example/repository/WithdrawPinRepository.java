package org.example.repository;

import org.example.model.WithdrawPin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawPinRepository extends JpaRepository<WithdrawPin, Integer> {

    Optional<WithdrawPin> findByUserId(int userId);

}
