package com.bankapp.customerService.service;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;
import com.bankapp.customerService.repository.CustomerRepository;
import com.bankapp.customerService.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Transactional
    @Override
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public void applyForLoan(LoanRequest loanRequest, String customer_pan_number) {

// Associate the LoanRequest with the Customer
        Customer customer = new Customer();
        customer.setPanNumber(customer_pan_number);

        loanRequest.setCustomer(customer);

// Save the LoanRequest to the database
        loanRequestRepository.save(loanRequest);
    }
}
