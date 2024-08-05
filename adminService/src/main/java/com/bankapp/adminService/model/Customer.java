package com.bankapp.adminService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(length = 36, nullable = false)
    private String panNumber;

    private String name;
    private String password;
    private String email;
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<LoanRequest> loanRequests = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (this.panNumber == null) {
            this.panNumber = UUID.randomUUID().toString();
        }
    }
}
