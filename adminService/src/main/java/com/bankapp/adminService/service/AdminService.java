package com.bankapp.adminService.service;

import com.bankapp.adminService.model.Admin;

public interface AdminService {
    void createAdmin(Admin admin);
    void resetPassword(Long adminId, String newPassword);

}
