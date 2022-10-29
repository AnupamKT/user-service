package com.example.userservice.repository;

import com.example.userservice.dao.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO, UUID> {
    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByUserName(String userName);

    long countByUserName(String userName);

    Optional<CustomerDAO> findByUserName(String userName);
}
