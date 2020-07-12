package com.restaurant.delivery.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.restaurant.delivery.enums.StatusEnum;
import com.restaurant.delivery.model.OrderRequestResponse;

@Service
public class DeliveryService {

    private static HashMap<String, OrderRequestResponse> deliveryBoys=new HashMap<String, OrderRequestResponse>();
    
    @Autowired
    RestTemplate restTemplate;
    
    @Value("${local.endpoint}")
    String localEndPoint;
    
    @PostConstruct
    public void updateEndpoint() {
        restTemplate.getForObject("http://localhost:8080/order/delivery?endPoint="+localEndPoint, ResponseEntity.class);
    }
    
    public HashMap<String, OrderRequestResponse> getActiveAgents() {
        return deliveryBoys;
    }
    
    public Object assignOrder(OrderRequestResponse order) {
        if (deliveryBoys.containsKey(order.getDeliveryPersonId())) {
            OrderRequestResponse curOrder = deliveryBoys.get(order.getDeliveryPersonId());
            if (StatusEnum.ACCEPTED.name().equals(curOrder.getStatus())) {
                return new ResponseEntity<String>("Agent is busy delivering order id : " + curOrder.getOrderId(), HttpStatus.CONFLICT);
            }
        }
        OrderRequestResponse newOrder = restTemplate.getForObject("http://localhost:8080/order/status?orderId="+order.getOrderId(), OrderRequestResponse.class);
        if (newOrder == null) {
            return new ResponseEntity<String>("no Order found with order id : " + order.getOrderId(), HttpStatus.BAD_REQUEST);
        }
        if(StatusEnum.ACCEPTED.name().equals(newOrder.getStatus())) {
            return new ResponseEntity<String>("Order already has been assigned", HttpStatus.BAD_REQUEST);
        }
        newOrder.setDeliveryPersonId(order.getDeliveryPersonId());
        newOrder.setStatus(StatusEnum.ACCEPTED.name());
        newOrder = restTemplate.postForObject("http://localhost:8080/order/update", newOrder, OrderRequestResponse.class);
        deliveryBoys.put(order.getDeliveryPersonId(), newOrder);
        return newOrder;
    }
}
