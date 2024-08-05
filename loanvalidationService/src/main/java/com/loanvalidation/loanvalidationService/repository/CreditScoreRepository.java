package com.loanvalidation.loanvalidationService.repository;

import com.loanvalidation.loanvalidationService.model.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    Optional<CreditScore> findByPanNumber(String panNumber);
}
