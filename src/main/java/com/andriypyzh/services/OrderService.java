package com.andriypyzh.services;

import com.andriypyzh.entities.Item;
import com.andriypyzh.entities.Order;
import com.andriypyzh.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    private ItemService itemService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void addItem(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        Item item = itemService.getItemById(itemId);
        List<Item> items = order.getItems();
        items.add(item);
        order.setItems(items);
        order.setPrice(order.getPrice() + item.getPrice());
        orderRepository.save(order);
    }

    public void deleteItem(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        Item item = itemService.getItemById(itemId);
        List<Item> items = order.getItems();
        items.remove(item);
        order.setItems(items);
        order.setPrice(order.getPrice() - item.getPrice());
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public void deleteOutdatedOrders() {
        orderRepository.deleteAllByCreatedAtIsBefore(LocalDateTime.now().minusMinutes(10));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
