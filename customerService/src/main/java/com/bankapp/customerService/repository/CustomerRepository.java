package com.bankapp.customerService.repository;

import com.bankapp.customerService.model.Customer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByPanNumber(String panNumber);
}
