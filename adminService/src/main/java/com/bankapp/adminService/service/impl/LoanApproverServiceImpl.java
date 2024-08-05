package com.bankapp.adminService.service.impl;


import com.bankapp.adminService.model.LoanRequest;
import com.bankapp.adminService.repository.LoanRequestRepository;
import com.bankapp.adminService.service.LoanApproverService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoanApproverServiceImpl implements LoanApproverService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    @Override
    public List<LoanRequest> getPendingLoans() {
        return loanRequestRepository.findByStatus("Pending");
    }


    @Transactional
    @Override
    public List<LoanRequest> getLoansByUser(String panNumber) {
        return loanRequestRepository.findByCustomer_PanNumber(panNumber);
    }

    @CircuitBreaker(name = "${spring.application.name}" ,fallbackMethod = "fallbackValidationLoan")
    @Override
    public String validationLoan(String panNumber) {
        return restTemplate.getForObject("http://localhost:8083/loan-validation/validate/" + panNumber, String.class);
    }

    @Override
    public String fallbackValidationLoan(String panNumber) {
        return "Validation service is down. Please try again later.";
    }

    @Transactional
    @Override
    public void approveLoan(Long requestId) {
        LoanRequest loanRequest = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));
        loanRequest.setStatus("Approved");
        loanRequestRepository.save(loanRequest);
    }

    @Override
    public void rejectLoan(Long requestId) {
        LoanRequest loanRequest = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));
        loanRequest.setStatus("Rejected");
        loanRequestRepository.save(loanRequest);
    }
}
