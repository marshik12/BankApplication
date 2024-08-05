package com.bankapp.adminService.service.impl;

import com.bankapp.adminService.model.Customer;
import com.bankapp.adminService.model.LoanRequest;
import com.bankapp.adminService.service.AdminService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JsonAdminKafkaConsumer {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JsonAdminKafkaConsumer.class);

    @Autowired
    private AdminService  adminService;

    @KafkaListener(topics = "customer_creation", groupId = "admin-group")
    public void consumeCustomerCreationMessage(Customer customer){
       // System.out.println("Received customer creation message: " + message);
        LOGGER.info(String.format("Json message received -> %s", customer.toString()));
    }

    @KafkaListener(topics = "loan_application", groupId = "admin-group")
    public void listenLoanApplication(LoanRequest loanRequest){
        //System.out.println("Received loan application message: " + loanRequest);
        LOGGER.info(String.format("Json message received -> %s", loanRequest.toString()));
    }
}
