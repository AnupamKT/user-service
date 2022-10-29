package com.example.userservice.converter;

import com.example.userservice.dao.CustomerDAO;
import com.example.userservice.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public CustomerDAO convertCustomerToDAO(Customer customer) {
        ObjectMapper mapper= new ObjectMapper();
        CustomerDAO dao=mapper.convertValue(customer,CustomerDAO.class);
        return dao;
    }
}
