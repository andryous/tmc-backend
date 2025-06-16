package org.example.themovingcompany.service;

import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component (used for business logic)
public class OrderService {

    private final OrderRepository orderRepository;

    // Constructor injection: Spring injects the repository here
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Returns all orders from the database
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Returns a specific order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Saves a new order or updates an existing one
    public Order createOrder(Order order) {
        // Check the endDate is not before startDate
        if (order.getEndDate().isBefore(order.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");

        }

        return orderRepository.save(order);
    }

    // Deletes an order by ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Additional update methods (PUT/PATCH) can be added later
}
