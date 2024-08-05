package com.bankapp.adminService.service;

import com.bankapp.adminService.model.Admin;
import com.bankapp.adminService.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAdmin() {
        Admin admin = new Admin();
        admin.setAdminId(1L);

        adminService.createAdmin(admin);

        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testResetPassword() {
        Admin admin = new Admin();
        admin.setAdminId(1L);
        admin.setPassword("oldPassword");

        when(adminRepository.findById(1L)).thenReturn(java.util.Optional.of(admin));

        adminService.resetPassword(1L, "newPassword");

        verify(adminRepository, times(1)).save(admin);
        assert "newPassword".equals(admin.getPassword());
    }
}