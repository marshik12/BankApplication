package com.bankapp.adminService.controller;

import com.bankapp.adminService.model.Admin;
import com.bankapp.adminService.model.LoanRequest;
import com.bankapp.adminService.service.AdminService;
import com.bankapp.adminService.service.LoanApproverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LoanApproverService loanApproverService;

    @GetMapping("/pendingLoans")
    public ResponseEntity<List<LoanRequest>> getPendingLoans(){
        List<LoanRequest> pendingLoans = loanApproverService.getPendingLoans();
        return ResponseEntity.ok(pendingLoans);
    }

    @GetMapping("/loansByUser/{panNumber}")
    public ResponseEntity<List<LoanRequest>> getLoansByUser(@PathVariable String panNumber){
        List<LoanRequest> loans = loanApproverService.getLoansByUser(panNumber);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/validation/{panNumber}")
    public ResponseEntity<String> validateLoan(@PathVariable String panNumber){
        String validationStatus = loanApproverService.validationLoan(panNumber);
        return ResponseEntity.ok(validationStatus);
    }

    @PostMapping("/approveLoan/{requestId}")
    public ResponseEntity<String> approveLoan(@PathVariable Long requestId){
        loanApproverService.approveLoan(requestId);
        return ResponseEntity.ok("Loan approved successfully");
    }

    @PostMapping("/rejectLoan/{requestId}")
    public ResponseEntity<String> rejectLoan(@PathVariable Long requestId){
        loanApproverService.rejectLoan(requestId);
        return ResponseEntity.ok("Loan rejected successfully");
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody Admin admin){
        adminService.createAdmin(admin);
        return ResponseEntity.ok("Admin created successfully");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam Long adminId, @RequestParam String newPassword){
        adminService.resetPassword(adminId,  newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }


}
