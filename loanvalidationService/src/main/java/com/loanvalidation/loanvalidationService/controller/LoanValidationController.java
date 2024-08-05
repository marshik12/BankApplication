package com.loanvalidation.loanvalidationService.controller;

import com.loanvalidation.loanvalidationService.model.CreditScore;
import com.loanvalidation.loanvalidationService.service.LoanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-validation")
public class LoanValidationController {

    @Autowired
    private LoanValidatorService loanValidatorService;

    @GetMapping("/validate/{panNumber}")
    public ResponseEntity<String> validateLoan(@PathVariable String panNumber){
        boolean isValid = loanValidatorService.validateLoan(panNumber);
        return ResponseEntity.ok(isValid ? "Validation successful" : "Validation failed");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCreditScore(@RequestBody CreditScore creditScore){
        loanValidatorService.createCreditScore(creditScore);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }
}
