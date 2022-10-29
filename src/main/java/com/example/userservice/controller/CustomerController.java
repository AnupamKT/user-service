package com.example.userservice.controller;

import com.example.userservice.common.CustomerNotFoundException;
import com.example.userservice.model.*;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.CustomerService;
import com.example.userservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity saveCustomer(@RequestBody Customer customer) throws Exception {
       Response response = customerService.saveCustomer(customer);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/{userName}")
    public ResponseEntity getCustomerByUserName(@PathVariable String userName) throws CustomerNotFoundException {
        Response response=customerService.getCustomerByUserName(userName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userName}/order")
    public ResponseEntity<Response> placeOrder(@PathVariable String userName, @RequestBody Order order) {
        ResponseEntity<Response> response = orderService.saveOrder(userName, order);
        return response;
    }

    @DeleteMapping("/{userName}/delete/{orderId}")
    public ResponseEntity<Response> deleteOrderById(@PathVariable String userName, @PathVariable String orderId) {
        ResponseEntity<Response> response = orderService.deleteOrderById(userName, orderId);
        return response;
    }

    @GetMapping("/{userName}/getAllOrders")
    public ResponseEntity<Response> getAllOrders(@PathVariable String userName) {
        ResponseEntity<Response> response = orderService.getAllOrders(userName);
        return response;
    }

}
