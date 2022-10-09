package com.example.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String cardNumber;
    private Date expiryDate;
    private String cardHolderName;
}
