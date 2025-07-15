package org.example.themovingcompany.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.OrderRequestDTO;
import org.example.themovingcompany.model.Person;
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
    @PersistenceContext
    private EntityManager entityManager;

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

    // Returns all orders linked to a given consultant ID
    public List<Order> getOrdersByConsultant(Long consultantId) {
        return orderRepository.findByConsultantId(consultantId);
    }

    // Returns all orders linked to a given customer ID
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // Returns all orders matching a given status
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // Returns all orders with a given service type
    public List<Order> getOrdersByServiceType(ServiceType serviceType) {
        return orderRepository.findByServiceType(serviceType);
    }

    // Returns all orders with startDate between given range
    public List<Order> searchOrdersByDateRange(LocalDate from, LocalDate to) {
        return orderRepository.findByStartDateBetween(from, to);
    }


    // Saves a new order or updates an existing one
    public Order createOrder(Order order) {
        // Check the endDate is not before startDate
        if (order.getEndDate().isBefore(order.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");

        }

        return orderRepository.save(order);
    }

    // Updates an existing order using data from a DTO.
// This version does not allow changing customer or consultant.
    public Order updateOrder(Long id, OrderRequestDTO request) {
        // Fetch the existing order from the database, or throw if not found
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        // Update editable fields using values from the request DTO
        existingOrder.setFromAddress(request.getFromAddress()); // Update 'from' address
        existingOrder.setToAddress(request.getToAddress());     // Update 'to' address
        existingOrder.setServiceType(ServiceType.valueOf(request.getServiceType())); // Update service type
        existingOrder.setStartDate(LocalDate.parse(request.getStartDate()));         // Update start date
        existingOrder.setEndDate(LocalDate.parse(request.getEndDate()));             // Update end date
        existingOrder.setNote(request.getNote());                                    // Update note
        existingOrder.setStatus(OrderStatus.valueOf(request.getStatus())); // Update order status
        existingOrder.setParentOrderId(request.getParentOrderId()); // Assign parent ID

        // Set lastUpdated timestamp
        existingOrder.setLastUpdated(java.time.LocalDateTime.now());

        // Set who modified it (consultant)
        if (request.getModifiedByConsultantId() != null) {
            Person modifier = entityManager.getReference(Person.class, request.getModifiedByConsultantId());
            existingOrder.setModifiedBy(modifier);
        }
        // Set new consultant if consultantId is present
        if (request.getConsultantId() != null) {
            Person newConsultant = entityManager.getReference(Person.class, request.getConsultantId());
            existingOrder.setConsultant(newConsultant);
        }

        // Save and return the updated order
        return orderRepository.save(existingOrder);
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
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

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
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

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

                case "parentOrderId":
                    if (value instanceof Number) {
                        order.setParentOrderId(((Number) value).longValue());
                    } else if (value == null) {
                        order.setParentOrderId(null); // allow unsetting parent
                    }
                    break;

                default:
                    // Ignore unknown fields or throw exception if preferred
                    break;

            }
        }

        if (order.getStartDate() == null || order.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }

        if (order.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if (order.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the past");
        }

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
