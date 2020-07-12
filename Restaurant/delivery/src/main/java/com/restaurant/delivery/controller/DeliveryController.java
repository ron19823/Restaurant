package com.restaurant.delivery.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.delivery.model.OrderRequestResponse;
import com.restaurant.delivery.service.DeliveryService;

@RestController
public class DeliveryController {
    
    @Autowired
    DeliveryService deliveryService;

    @PostMapping("deliver")
    public Object deliver(@RequestBody OrderRequestResponse order) {
        return deliveryService.assignOrder(order);
    }
    
    @GetMapping("delivery/agents")
    public HashMap getAgents(){
        return deliveryService.getActiveAgents();
    }
}
