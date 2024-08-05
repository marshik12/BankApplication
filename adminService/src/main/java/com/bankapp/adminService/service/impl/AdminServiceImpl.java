package com.bankapp.adminService.service.impl;

import com.bankapp.adminService.model.Admin;
import com.bankapp.adminService.repository.AdminRepository;
import com.bankapp.adminService.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    @Override
    public void createAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Transactional
    @Override
    public void resetPassword(Long adminId, String newPassword) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setPassword(newPassword);
        adminRepository.save(admin);
    }


}
