package com.loanvalidation.loanvalidationService.service.impl;

import com.loanvalidation.loanvalidationService.model.CreditScore;
import com.loanvalidation.loanvalidationService.repository.CreditScoreRepository;
import com.loanvalidation.loanvalidationService.service.LoanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanValidatorServiceImpl implements LoanValidatorService {

    @Autowired
    private CreditScoreRepository creditScoreRepository;

    @Override
    public boolean validateLoan(String panNumber) {
        CreditScore creditScore = creditScoreRepository.findByPanNumber(panNumber)
                .orElseThrow(() -> new RuntimeException("Credit score not found"));
        return creditScore.getScore() >= 700 && creditScore.isPaymentHistoryGood();
    }

    @Override
    public void createCreditScore(CreditScore creditScore) {
        creditScoreRepository.save(creditScore);
    }


}
