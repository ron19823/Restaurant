package com.restaurant.order.service;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.restaurant.order.enums.StatusEnum;
import com.restaurant.order.model.OrderRequestResponse;

@Service
public class OrderService {
    private static HashMap<String, OrderRequestResponse> orders = new HashMap<String, OrderRequestResponse>();
    private static String deliveryServiceEndpoint="http://localhost:8080";
 
    @Autowired
    RestTemplate restTemplate;
    
    public OrderRequestResponse createOrder(OrderRequestResponse order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(StatusEnum.NEW.name());
        orders.put(order.getOrderId(), order);
        return order;
    }
    
    public ResponseEntity<String> updateEndpoint(String endPoint) {
    	deliveryServiceEndpoint=endPoint;
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
    public OrderRequestResponse getStatus(String orderId) {
        return orders.get(orderId);
    }
    
    public Object updateStatus(OrderRequestResponse order) {
        if(orders.containsKey(order.getOrderId()) && !StringUtils.isEmpty(order.getStatus())) {
            orders.get(order.getOrderId()).setStatus(order.getStatus());
        }
        else {
           return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return order;
    }
    
    public HashMap getActiveAgents(){
    	return restTemplate.getForObject(deliveryServiceEndpoint+"/delivery/agents", HashMap.class);
    }
}
