package com.example.userservice.service.executors;

import com.example.userservice.model.Card;
import com.example.userservice.model.CardResponse;
import com.example.userservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CardExecutor {

    @Value("${card.service.name}")
    private String CARD_SERVICE_NAME;

    @Autowired
    private RestTemplate restTemplate;

    public Response addCard(Long userId, Card card) {
        String url = "http://" + CARD_SERVICE_NAME + "/card/" + userId + "/add";
        int status = 500;
        Object responseBody = "server error occurred while saving card";
        try {
            ResponseEntity<CardResponse> response = restTemplate.postForEntity(url, card, CardResponse.class);
            if (response != null && response.hasBody()) {
                CardResponse cardResponse = (CardResponse) response.getBody();
                if (cardResponse != null && cardResponse.getBody() != null) {
                    status = 200;
                    responseBody = cardResponse.getBody();
                }
            }
        } catch (Exception e) {
            responseBody = e.getMessage();
        }
        return Response.builder().status(status).body(responseBody).build();
    }

    public Response deleteCard(String cardNumber) {
        String url = "http://" + CARD_SERVICE_NAME + "/card/" + cardNumber;
        int status = 500;
        String responseMsg = null;
        try {
            restTemplate.delete(url);
            status = 200;
            responseMsg = "card deleted successfully " + cardNumber;
        } catch (Exception e) {
            responseMsg = "server error occurred while deleting card " + cardNumber;
        }
        return Response.builder().status(status).build();
    }
}
