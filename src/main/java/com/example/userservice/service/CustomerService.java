package com.example.userservice.service;

import com.example.userservice.common.CustomerNotFoundException;
import com.example.userservice.converter.CustomerConverter;
import com.example.userservice.dao.CustomerDAO;
import com.example.userservice.model.Customer;
import com.example.userservice.model.Response;
import com.example.userservice.repository.CustomerRepository;
import com.example.userservice.validator.CustomerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerValidator customerValidator;
    @Autowired
    private CustomerConverter converter;
    @Autowired
    private CustomerRepository repo;

    public Response saveCustomer(Customer customer) throws Exception {

        //validate Mandatory fields
        customerValidator.validateCustomerRequest(customer);
        //prepare customerDao object
        CustomerDAO dao = converter.convertCustomerToDAO(customer);
        //generate userName based on firstName, lastName
        dao.setUserName(CompletableFuture.supplyAsync(() -> generateUserName(customer)).join());
        //save customerDAO object
        CustomerDAO savedCustomer = repo.save(dao);
        return new Response(HttpStatus.ACCEPTED.value(), savedCustomer);
    }

    /**
     * Generates 7 character long userName based on first name and last name
     * takes first character form first name and remaining six char from last name
     * if userName is found in DB, then takes two character from firstName and remaining five from lastName and so on
     * if after all combination userName is not found, userName formed from first character from first name and remaining six character from last name is appended with right number to create userName
     * if lastName is not there only first name is used for creating userName
     */
    private synchronized String generateUserName(Customer customer) {
        int userNameMaxLength = 8;
        String userName = null;
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        if (StringUtils.hasText(lastName)) {
            int i = 0;
            for (; i < firstName.length(); i++) {
                int index = userNameMaxLength - (i + 1);
                int lastNameEndIndex = (lastName.length() > index) ? index : lastName.length();
                userName = firstName.substring(0, i + 1) +
                        lastName.substring(0, lastNameEndIndex);
                if (checkIfUserNameExists(userName) == 0) {
                    break;
                }
            }
            //all combinations are tried here
            if (i == firstName.length()) {
                int index = userNameMaxLength - 1;
                int lastNameEndIndex = (lastName.length() > index) ? index : lastName.length();
                userName = firstName.substring(0, 1) +
                        lastName.substring(0, lastNameEndIndex);
                if (checkIfUserNameExists(userName) > 0) {
                    userName = userName + (checkIfUserNameExists(userName) + 1);
                }

            }
        } else {
            int index = (firstName.length() > userNameMaxLength) ? userNameMaxLength : firstName.length();
            userName = firstName.substring(0, index);
            if (checkIfUserNameExists(userName) > 0) {
                userName = userName + (checkIfUserNameExists(userName) + 1);
            }
        }
        return userName.toLowerCase();
    }

    private long checkIfUserNameExists(String userName) {
        return repo.countByUserName(userName.toLowerCase());
    }

    public Response getCustomerByUserName(String userName) throws CustomerNotFoundException {
        Optional<CustomerDAO> dao=Optional.empty();
        CustomerDAO customerDAO=null;
        dao= repo.findByUserName(userName);
        if(dao.isPresent()){
            customerDAO=dao.get();
        }else{
            String msg="customer not found with userName "+userName;
            log.error(msg);
            throw new CustomerNotFoundException(msg);
        }
        return new Response(HttpStatus.OK.value(),customerDAO);
    }
}
