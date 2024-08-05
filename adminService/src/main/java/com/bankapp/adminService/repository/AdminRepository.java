package com.bankapp.adminService.repository;

import com.bankapp.adminService.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
