package org.example.themovingcompany.service;

import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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


    //MORE VALIDATION:
    // Updates an existing order and validates status transition.
    public Order updateOrder(Long id, Order updatedOrder) {
        // Find existing order by ID or throw if not found
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        // Validate allowed status transitions
        validateStatusTransition(existingOrder, updatedOrder.getStatus());

        // Update fields from the incoming object
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setNote(updatedOrder.getNote());
        existingOrder.setFromAddress(updatedOrder.getFromAddress());
        existingOrder.setToAddress(updatedOrder.getToAddress());
        existingOrder.setStartDate(updatedOrder.getStartDate());
        existingOrder.setEndDate(updatedOrder.getEndDate());
        existingOrder.setServiceType(updatedOrder.getServiceType());

        // Save updated order and return
        return orderRepository.save(existingOrder);
    }

    // Partially updates fields of an existing order.
    public Order patchOrder(Long id, Map<String, Object> updates) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        // Iterate over the fields to update
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "fromAddress":
                    if (value instanceof String && !((String) value).isBlank()) {
                        order.setFromAddress((String) value);
                    }
                    break;
                case "toAddress":
                    if (value instanceof String && !((String) value).isBlank()) {
                        order.setToAddress((String) value);
                    }
                    break;
                case "serviceType":
                    if (value instanceof String) {
                        order.setServiceType(ServiceType.valueOf(((String) value).toUpperCase()));
                    }
                    break;
                case "startDate":
                    if (value instanceof String) {
                        try {
                            order.setStartDate(LocalDate.parse((String) value));
                        } catch (DateTimeParseException e) {
                            throw new IllegalArgumentException("Invalid startDate format");
                        }
                    }
                    break;
                case "endDate":
                    if (value instanceof String) {
                        try {
                            order.setEndDate(LocalDate.parse((String) value));
                        } catch (DateTimeParseException e) {
                            throw new IllegalArgumentException("Invalid endDate format");
                        }
                    }
                    break;
                case "note":
                    if (value instanceof String) {
                        order.setNote((String) value);
                    }
                    break;
                case "status":
                    if (value instanceof String) {
                        OrderStatus newStatus = OrderStatus.valueOf(((String) value).toUpperCase());
                        validateStatusTransition(order, newStatus);
                        order.setStatus(newStatus);
                    }
                    break;
                default:
                    // Ignore unknown fields or throw exception if preferred
                    break;
            }
        }

        // Check that endDate is not before startDate after updates
        if (order.getEndDate().isBefore(order.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        return orderRepository.save(order);
    }


    // MORE VALIDATIONS:
    // Validates allowed status transitions to maintain business logic integrity.
    private void validateStatusTransition(Order existingOrder, OrderStatus newStatus) {
        OrderStatus currentStatus = existingOrder.getStatus();

        // Prevent direct transition from PENDING to COMPLETED
        if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot transition directly from PENDING to COMPLETED.");
        }

        // Add more rules as needed, for example:
        // Prevent transition from CANCELLED to any other status
        if (currentStatus == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot change status once order is CANCELLED.");
        }

        // Prevent transition from COMPLETED to any other status
        if (currentStatus == OrderStatus.COMPLETED && newStatus != OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot change status once order is COMPLETED.");
        }
    }




}
