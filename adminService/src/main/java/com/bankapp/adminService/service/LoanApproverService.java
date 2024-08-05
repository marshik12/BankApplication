package com.bankapp.adminService.service;

import com.bankapp.adminService.model.LoanRequest;

import java.util.List;

public interface LoanApproverService {
    List<LoanRequest> getPendingLoans();
    List<LoanRequest> getLoansByUser(String panNumber);
    String validationLoan(String panNumber);
    String fallbackValidationLoan(String panNumber);
    void approveLoan(Long requestId);
    void rejectLoan(Long requestId);
}
