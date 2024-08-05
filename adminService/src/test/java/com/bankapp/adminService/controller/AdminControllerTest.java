package com.bankapp.adminService.controller;

import com.bankapp.adminService.model.Admin;
import com.bankapp.adminService.model.LoanRequest;
import com.bankapp.adminService.service.AdminService;
import com.bankapp.adminService.service.LoanApproverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private LoanApproverService loanApproverService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPendingLoans() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        List<LoanRequest> loanRequests = Collections.singletonList(loanRequest);

        when(loanApproverService.getPendingLoans()).thenReturn(loanRequests);

        mockMvc.perform(get("/admin/pendingLoans"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(loanRequests)));
    }

    @Test
    public void testGetLoansByUser() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        List<LoanRequest> loanRequests = Collections.singletonList(loanRequest);

        when(loanApproverService.getLoansByUser("PAN123")).thenReturn(loanRequests);

        mockMvc.perform(get("/admin/loansByUser/PAN123"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(loanRequests)));
    }

    @Test
    public void testValidateLoan() throws Exception {
        String validationStatus = "Valid";

        when(loanApproverService.validationLoan("PAN123")).thenReturn(validationStatus);

        mockMvc.perform(get("/admin/validation/PAN123"))
                .andExpect(status().isOk())
                .andExpect(content().string(validationStatus));
    }

    @Test
    public void testApproveLoan() throws Exception {
        mockMvc.perform(post("/admin/approveLoan/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan approved successfully"));

        verify(loanApproverService, times(1)).approveLoan(1L);
    }

    @Test
    public void testRejectLoan() throws Exception {
        mockMvc.perform(post("/admin/rejectLoan/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan rejected successfully"));

        verify(loanApproverService, times(1)).rejectLoan(1L);
    }

    @Test
    public void testCreateAdmin() throws Exception {
        Admin admin = new Admin();
        admin.setAdminId(1L);

        mockMvc.perform(post("/admin/createAdmin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin created successfully"));

        verify(adminService, times(1)).createAdmin(admin);
    }

    @Test
    public void testResetPassword() throws Exception {
        mockMvc.perform(post("/admin/resetPassword")
                        .param("adminId", "1")
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset successfully"));

        verify(adminService, times(1)).resetPassword(1L, "newPassword");
    }
}