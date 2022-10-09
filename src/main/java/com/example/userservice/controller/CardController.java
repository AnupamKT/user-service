package com.example.userservice.controller;

import com.example.userservice.model.Card;
import com.example.userservice.model.Response;
import com.example.userservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/{userName}/card")
    public ResponseEntity<Response> addCard(@PathVariable String userName, @RequestBody Card card) {
        Response response = cardService.addCard(userName, card);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{userName}/card/{cardNumber}")
    public ResponseEntity<Response> deleteUserCard(@PathVariable String userName, @PathVariable String cardNumber) {
        Response response = cardService.deleteCard(userName, cardNumber);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
