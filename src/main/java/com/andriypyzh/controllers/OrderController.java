package com.andriypyzh.controllers;

import com.andriypyzh.entities.Order;
import com.andriypyzh.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PatchMapping("/{id}")
    public void changeItems(@PathVariable Long orderId, @RequestBody Map<String, String> json) {
        String operation = json.get("operation");
        Long itemId = Long.parseLong(json.get("itemId"));

        if (operation.equals("add")) {
            orderService.addItem(orderId, itemId);
        } else {
            orderService.deleteItem(orderId, itemId);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
    }

}
