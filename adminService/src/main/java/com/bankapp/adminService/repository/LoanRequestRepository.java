package com.bankapp.adminService.repository;

import com.bankapp.adminService.model.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    // Custom query to find LoanRequests by customer's panNumber
    List<LoanRequest> findByCustomer_PanNumber(String panNumber);
    //List<LoanRequest> findByPanNumber(String panNumber);
    List<LoanRequest> findByStatus(String status);
}
