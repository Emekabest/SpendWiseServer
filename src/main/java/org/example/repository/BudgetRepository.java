package org.example.repository;

import org.example.model.Budget;
import org.example.model.User;
import org.example.model.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    Optional<Budget> findByEmail(String email);

}
