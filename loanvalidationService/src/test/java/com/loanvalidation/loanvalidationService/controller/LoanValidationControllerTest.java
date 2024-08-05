package com.loanvalidation.loanvalidationService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loanvalidation.loanvalidationService.model.CreditScore;
import com.loanvalidation.loanvalidationService.service.LoanValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class LoanValidationControllerTest {

    @InjectMocks
    private LoanValidationController loanValidationController;

    @Mock
    private LoanValidatorService loanValidatorService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanValidationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testValidateLoan_Success() throws Exception {
        when(loanValidatorService.validateLoan("PAN123")).thenReturn(true);

        mockMvc.perform(get("/loan-validation/validate/PAN123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Validation successful"));
    }

    @Test
    public void testValidateLoan_Failure() throws Exception {
        when(loanValidatorService.validateLoan("PAN123")).thenReturn(false);

        mockMvc.perform(get("/loan-validation/validate/PAN123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Validation failed"));
    }

    @Test
    public void testCreateCreditScore() throws Exception {
        CreditScore creditScore = new CreditScore();
        creditScore.setPanNumber("PAN123");
        creditScore.setScore(750);
        creditScore.setPaymentHistoryGood(true);

        mockMvc.perform(post("/loan-validation/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(creditScore)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Customer created successfully"));

        verify(loanValidatorService, times(1)).createCreditScore(creditScore);
    }
}
