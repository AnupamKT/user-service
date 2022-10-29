package com.example.userservice.validator;

import com.example.userservice.common.InvalidCustomerRequestException;
import com.example.userservice.model.Customer;
import com.example.userservice.repository.CustomerRepository;
import com.example.userservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerValidator {

    @Autowired
    private CustomerRepository repository;


    public void validateCustomerRequest(Customer customer) throws InvalidCustomerRequestException {
        //validate Mandatory fields
        validateMandatoryFields(customer);
        //validate email and mobile number format
        validateEmailAddressFormat(customer.getEmail());
        validateMobileNumberFormat(customer.getMobileNumber());
        //validate if customer exists by same email, username and MobileNumber
        validateConflictingRequest(customer);
    }

    private void validateMobileNumberFormat(String mobileNumber) throws InvalidCustomerRequestException {
        if (!Util.validateMobileNumberFormat(mobileNumber)) {
            String msg = "Invalid request!! Mobile number is not in correct format " + mobileNumber;
            throw new InvalidCustomerRequestException(msg);
        }
    }

    private void validateEmailAddressFormat(String email) throws InvalidCustomerRequestException {
        if (!Util.validateEmailAddressFormat(email)) {
            String msg = "Invalid request!! Email is not in correct format " + email;
            throw new InvalidCustomerRequestException(msg);
        }
    }

    private void validateConflictingRequest(Customer customer) throws InvalidCustomerRequestException {
        StringBuilder msg = new StringBuilder("Conflict!! ");
        if (repository.existsByEmail(customer.getEmail())) {
            msg.append("email address ")
                    .append(customer.getEmail())
                    .append(" is already in use");
            throw new InvalidCustomerRequestException(msg.toString());
        }
    }

    private void validateMandatoryFields(Customer customer) throws InvalidCustomerRequestException {
        List<String> missingFields = new ArrayList<>();
        if (!StringUtils.hasText(customer.getEmail())) {
            missingFields.add("email");
        }
        if (!StringUtils.hasText(customer.getFirstName())) {
            missingFields.add("firstName");
        }
        if (!StringUtils.hasText(customer.getMobileNumber())) {
            missingFields.add("mobileNumber");
        }

        if (missingFields.size() > 0) {
            StringBuilder msg = new StringBuilder("Invalid customer request! fields ");
            msg.append(missingFields).append(" are missing");
            throw new InvalidCustomerRequestException(msg.toString());
        }
    }
}
