package com.bankapp.adminService.service;

import com.bankapp.adminService.model.LoanRequest;
import com.bankapp.adminService.repository.LoanRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class LoanApproverServiceImplTest {

    @InjectMocks
    private LoanApproverService loanApproverService;

    @Mock
    private LoanRequestRepository loanRequestRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPendingLoans() {
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestRepository.findByStatus("Pending")).thenReturn(Collections.singletonList(loanRequest));

        loanApproverService.getPendingLoans();

        verify(loanRequestRepository, times(1)).findByStatus("Pending");
    }

    @Test
    public void testGetLoansByUser() {
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestRepository.findByCustomer_PanNumber("PAN123")).thenReturn(Collections.singletonList(loanRequest));

        loanApproverService.getLoansByUser("PAN123");

        verify(loanRequestRepository, times(1)).findByCustomer_PanNumber("PAN123");
    }

    @Test
    public void testValidationLoan() {
        when(restTemplate.getForObject("http://localhost:8083/loan-validation/validate/PAN123", String.class))
                .thenReturn("Valid");

        String result = loanApproverService.validationLoan("PAN123");

        verify(restTemplate, times(1)).getForObject("http://localhost:8083/loan-validation/validate/PAN123", String.class);
        assert "Valid".equals(result);
    }

    @Test
    public void testApproveLoan() {
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestRepository.findById(1L)).thenReturn(Optional.of(loanRequest));

        loanApproverService.approveLoan(1L);

        verify(loanRequestRepository, times(1)).save(loanRequest);
    }

    @Test
    public void testRejectLoan() {
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestRepository.findById(1L)).thenReturn(Optional.of(loanRequest));

        loanApproverService.rejectLoan(1L);

        verify(loanRequestRepository, times(1)).save(loanRequest);
    }
}
