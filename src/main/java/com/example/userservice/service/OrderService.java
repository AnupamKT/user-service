package com.example.userservice.service;

import com.example.userservice.dao.User;
import com.example.userservice.model.Order;
import com.example.userservice.model.Response;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.executors.OrderExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderExecutor orderExecutor;

    public ResponseEntity<Response> saveOrder(String userName, Order order) {
        Optional<User> users = userRepository.findByUserName(userName);
        if (users.isPresent()) {
            order.setUserId(users.get().getId());
            return orderExecutor.saveOrder(order);
        } else {
            Response response = new Response();
            //response.setMessage("userName not found " + userName);
            response.setStatus(400);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Response> deleteOrderById(String userName, String orderId) {
        Response response = new Response();
        Optional<User> users = userRepository.findByUserName(userName);
        if (users.isPresent()) {
            orderExecutor.deleteOrderById(orderId);
            response.setStatus(200);
            //response.setMessage("order deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            //response.setMessage("userName not found " + userName);
            response.setStatus(400);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Response> getAllOrders(String userName) {
        Response response = new Response();
        Optional<User> users = userRepository.findByUserName(userName);
        if (users.isPresent()) {
            ResponseEntity<Response> getResponse = orderExecutor.getAllOrders(users.get().getId());
//            response.setStatusCode(200);
//            response.setResponseBody(getResponse.getBody().getResponseBody());
            return getResponse;
        } else {
            //response.setMessage("userName not found " + userName);
            response.setStatus(400);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
