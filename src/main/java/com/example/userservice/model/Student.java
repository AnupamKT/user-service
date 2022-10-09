package com.example.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String classOfStudy;
    private String section;
    private String emailId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String motherName;
    private String fatherName;
}


