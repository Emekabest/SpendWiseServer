package org.example.repository;

import org.example.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {

    Optional<Withdraw> findById(int id);

}
