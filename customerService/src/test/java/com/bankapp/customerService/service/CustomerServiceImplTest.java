package com.bankapp.customerService.service;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;
import com.bankapp.customerService.repository.CustomerRepository;
import com.bankapp.customerService.repository.LoanRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRequestRepository loanRequestRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setPanNumber("PAN123");

        customerService.createCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    @Transactional
    public void testApplyForLoan() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setAmount(10000.00);
        loanRequest.setLoanType("Home Loan");

        Customer customer = new Customer();
        customer.setPanNumber("PAN123");

        customerService.applyForLoan(loanRequest, "PAN123");

        verify(loanRequestRepository, times(1)).save(loanRequest);
    }
}
