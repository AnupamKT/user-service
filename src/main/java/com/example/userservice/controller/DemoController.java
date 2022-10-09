package com.example.userservice.controller;

import com.example.userservice.dao.User;
import com.example.userservice.model.*;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.MyUserDetailsService;
import com.example.userservice.service.OrderService;
import com.example.userservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class DemoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;

    @PostMapping("/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String jwtToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{userName}/order")
    public ResponseEntity<Response> placeOrder(@PathVariable("userName") String userName, @RequestBody Order order) {
        ResponseEntity<Response> response = orderService.saveOrder(userName, order);
        return response;
    }

    @DeleteMapping("/{userName}/delete/{orderId}")
    public ResponseEntity<Response> deleteOrderById(@PathVariable("userName") String userName, @PathVariable String orderId) {
        ResponseEntity<Response> response = orderService.deleteOrderById(userName, orderId);
        return response;
    }

    @GetMapping("/{userName}/getAllOrders")
    public ResponseEntity<Response> getAllOrders(@PathVariable("userName") String userName) {
        ResponseEntity<Response> response = orderService.getAllOrders(userName);
        return response;
    }

}
