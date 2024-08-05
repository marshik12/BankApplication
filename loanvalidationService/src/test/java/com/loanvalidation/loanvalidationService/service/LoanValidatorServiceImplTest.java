package com.loanvalidation.loanvalidationService.service;

import com.loanvalidation.loanvalidationService.model.CreditScore;
import com.loanvalidation.loanvalidationService.repository.CreditScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoanValidatorServiceImplTest {

    @InjectMocks
    private LoanValidatorService loanValidatorService;

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateLoan_Success() {
        CreditScore creditScore = new CreditScore();
        creditScore.setScore(750);
        creditScore.setPaymentHistoryGood(true);

        when(creditScoreRepository.findByPanNumber("PAN123")).thenReturn(Optional.of(creditScore));

        boolean result = loanValidatorService.validateLoan("PAN123");

        assertTrue(result);
    }

    @Test
    public void testValidateLoan_Failure() {
        CreditScore creditScore = new CreditScore();
        creditScore.setScore(650);
        creditScore.setPaymentHistoryGood(false);

        when(creditScoreRepository.findByPanNumber("PAN123")).thenReturn(Optional.of(creditScore));

        boolean result = loanValidatorService.validateLoan("PAN123");

        assertFalse(result);
    }

    @Test
    public void testValidateLoan_NotFound() {
        when(creditScoreRepository.findByPanNumber("PAN123")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            loanValidatorService.validateLoan("PAN123");
        });

        assertEquals("Credit score not found", thrown.getMessage());
    }

    @Test
    public void testCreateCreditScore() {
        CreditScore creditScore = new CreditScore();
        creditScore.setPanNumber("PAN123");
        creditScore.setScore(750);
        creditScore.setPaymentHistoryGood(true);

        loanValidatorService.createCreditScore(creditScore);

        verify(creditScoreRepository, times(1)).save(creditScore);
    }
}