package com.restaurant.order.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.order.model.OrderRequestResponse;
import com.restaurant.order.service.OrderService;

@RestController
public class RestaurantController {

    @Autowired
    OrderService orderService;
    
    @PostMapping("order/place")
    public OrderRequestResponse createOrder(@RequestBody OrderRequestResponse order){
        return orderService.createOrder(order);
    }
    
    @GetMapping("order/status")
    public OrderRequestResponse getStatus(@RequestParam(required = true) String orderId) {
        return orderService.getStatus(orderId);
    }
    
    @PostMapping("order/update")
    public Object updateOrder(@RequestBody OrderRequestResponse order){
        return orderService.updateStatus(order);
    }
    
    @GetMapping("order/agents")
    public HashMap getActiveAgents() {
        return orderService.getActiveAgents();
    }
    
    @GetMapping("order/delivery")
    public ResponseEntity<String> getActiveAgents(@RequestParam(required = true)String endPoint) {
        return orderService.updateEndpoint(endPoint);
    }
}
