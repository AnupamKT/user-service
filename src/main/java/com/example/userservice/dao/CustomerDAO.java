package com.example.userservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;
@Data
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER_INFO", uniqueConstraints = {
        @UniqueConstraint(name = "emailIdUnique", columnNames = {"email"}),
        @UniqueConstraint(name = "userNameUnique", columnNames = {"userName"})})
public class CustomerDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customerId;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String mobileNumber;
    @Column(nullable = false,length = 8)
    private String userName;
}
