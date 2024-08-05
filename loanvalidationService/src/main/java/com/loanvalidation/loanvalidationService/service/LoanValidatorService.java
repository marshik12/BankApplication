package com.loanvalidation.loanvalidationService.service;

import com.loanvalidation.loanvalidationService.model.CreditScore;

public interface LoanValidatorService {
    boolean validateLoan(String panNumber);
    void createCreditScore(CreditScore creditScore);
}
