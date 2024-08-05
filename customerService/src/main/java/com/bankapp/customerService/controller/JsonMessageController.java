package com.bankapp.customerService.controller;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;
import com.bankapp.customerService.service.JsonCustomerKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class JsonMessageController {

    @Autowired
    private JsonCustomerKafkaProducer customerKafkaProducer;

    @PostMapping("/publishCustomer")
    public ResponseEntity<String> publishCustomer(@RequestBody Customer customer){
        customerKafkaProducer.sendCustomerCreationMessage(customer);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }

    @PostMapping("/publishLoanRequest")
    public ResponseEntity<String> publishLoanRequest(@RequestBody LoanRequest loanRequest){
        customerKafkaProducer.sendLoanApplicationMessage(loanRequest);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}
