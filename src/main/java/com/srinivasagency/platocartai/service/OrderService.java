package com.srinivasagency.platocartai.service;

import com.srinivasagency.platocartai.model.Order;
import com.srinivasagency.platocartai.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(String id, String status, String deliveryDate) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            if (deliveryDate != null && !deliveryDate.trim().isEmpty()) {
                order.setExpectedDeliveryDate(deliveryDate);
            }
            return orderRepository.save(order);
        }
        return null;
    }
}
