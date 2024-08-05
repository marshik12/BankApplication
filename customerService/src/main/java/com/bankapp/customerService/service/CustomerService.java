package com.bankapp.customerService.service;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;

public interface CustomerService {
    void createCustomer(Customer customer);
    void applyForLoan(LoanRequest loanRequest, String customer_pan_number);
}
