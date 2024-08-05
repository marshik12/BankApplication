package com.bankapp.customerService.controller;

import com.bankapp.customerService.model.Customer;
import com.bankapp.customerService.model.LoanRequest;
import com.bankapp.customerService.service.CustomerService;
import com.bankapp.customerService.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setPanNumber("PAN123");

        mockMvc.perform(post("/customers/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Customer created successfully"));

        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    public void testApplyForLoan() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setAmount(10000.00);
        loanRequest.setLoanType("Home Loan");

        mockMvc.perform(post("/customers/applyLoan/PAN123")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan application submitted"));

        verify(customerService, times(1)).applyForLoan(loanRequest, "PAN123");
    }
}
