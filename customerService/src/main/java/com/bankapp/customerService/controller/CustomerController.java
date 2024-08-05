package com.bankapp.customerService.controller;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;

import com.bankapp.customerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

//    @Autowired
//    private CustomerProducer customerProducer;

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
        //customerProducer.sendCustomerCreationMessage(customer.getPanNumber());
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/applyLoan/{customer_pan_number}")
    public ResponseEntity<String> applyForLoan(@RequestBody LoanRequest loanRequest, @PathVariable String customer_pan_number){
        customerService.applyForLoan(loanRequest,customer_pan_number);
        //customerProducer.sendLoanApplicationMessage(loanRequest.toString());
        return ResponseEntity.ok("Loan application submitted");
    }
}
