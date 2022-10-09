package com.example.userservice.service.executors;

import com.example.userservice.config.MyConfiguration;
import com.example.userservice.model.Order;
import com.example.userservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderExecutor {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${order.service.name}")
    private String ORDER_SERVICE_NAME;
    public ResponseEntity<Response> saveOrder(Order order) {
        ResponseEntity<Response> response = restTemplate.
                postForEntity("http://"+ORDER_SERVICE_NAME+"/order/save",order, Response.class);
        return response;
    }

    public void deleteOrderById(String orderId) {
        String url="http://"+ORDER_SERVICE_NAME+"/order/delete/"+orderId;
        try{
            restTemplate.delete(url);
        }catch(Exception e){

        }

    }

    public ResponseEntity<Response> getAllOrders(Long id) {
        ResponseEntity<Response> response = restTemplate.
                getForEntity("http://"+ORDER_SERVICE_NAME+"/order/fetchall/"+id, Response.class);
        return response;
    }
}
