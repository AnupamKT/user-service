package com.example.userservice.service;

import com.example.userservice.dao.User;
import com.example.userservice.model.Card;
import com.example.userservice.model.Response;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.executors.CardExecutor;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardExecutor cardExecutor;

    public Response addCard(String userName, Card card) {
        Response response=null;
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            Long userId = user.get().getId();
            response = cardExecutor.addCard(userId, card);
        }
        return response;
    }

    public Response deleteCard(String userName, String cardNumber) {
        Response response=null;
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            Long userId = user.get().getId();
            response = cardExecutor.deleteCard(cardNumber);
        }else{
           String msg="User not found "+userName;
            response=Response.builder().message(msg).statusCode(404).build();
        }
        return response;
    }
}
