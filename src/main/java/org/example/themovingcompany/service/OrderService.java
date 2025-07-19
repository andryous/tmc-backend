package org.example.themovingcompany.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.OrderRequestDTO;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.OrderRepository;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component (used for business logic)
public class OrderService {

    private final OrderRepository orderRepository;
    private final PersonRepository personRepository; // Injects access to the person table

    @PersistenceContext
    private EntityManager entityManager;

    // Constructor injection: Spring injects both repositories here
    public OrderService(OrderRepository orderRepository, PersonRepository personRepository) {
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
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

    // Saves a new order using a DTO that contains only IDs and Strings
    public Order createOrder(OrderRequestDTO dto) {
        Order order = new Order();

        // Convert enums and date strings to actual types
        order.setServiceType(ServiceType.valueOf(dto.getServiceType()));
        order.setStatus(OrderStatus.valueOf(dto.getStatus()));
        order.setStartDate(LocalDate.parse(dto.getStartDate()));
        order.setEndDate(LocalDate.parse(dto.getEndDate()));

        // Set simple string fields
        order.setFromAddress(dto.getFromAddress());
        order.setToAddress(dto.getToAddress());
        order.setNote(dto.getNote());
        order.setParentOrderId(dto.getParentOrderId());

        // 🔗 Set customer and consultant using their IDs
        Person customer = personRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);

        if (dto.getConsultantId() != null) {
            Person consultant = personRepository.findById(dto.getConsultantId())
                    .orElseThrow(() -> new RuntimeException("Consultant not found"));
            order.setConsultant(consultant);
        }

        if (dto.getModifiedByConsultantId() != null) {
            Person modifier = personRepository.findById(dto.getModifiedByConsultantId())
                    .orElseThrow(() -> new RuntimeException("Modifier not found"));
            order.setModifiedBy(modifier);
        }

        return orderRepository.save(order);
    }

    // Saves a new order from a full Order object (used in tests or admin logic)
    public Order createOrder(Order order) {
        if (order.getEndDate().isBefore(order.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        return orderRepository.save(order);
    }

    // Updates an existing order using data from a DTO (cannot change customer/consultant)
    public Order updateOrder(Long id, OrderRequestDTO request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        existingOrder.setFromAddress(request.getFromAddress());
        existingOrder.setToAddress(request.getToAddress());
        existingOrder.setServiceType(ServiceType.valueOf(request.getServiceType()));
        existingOrder.setStartDate(LocalDate.parse(request.getStartDate()));
        existingOrder.setEndDate(LocalDate.parse(request.getEndDate()));
        existingOrder.setNote(request.getNote());
        existingOrder.setStatus(OrderStatus.valueOf(request.getStatus()));
        existingOrder.setParentOrderId(request.getParentOrderId());

        existingOrder.setLastUpdated(java.time.LocalDateTime.now());

        if (request.getModifiedByConsultantId() != null) {
            Person modifier = entityManager.getReference(Person.class, request.getModifiedByConsultantId());
            existingOrder.setModifiedBy(modifier);
        }

        if (request.getConsultantId() != null) {
            Person newConsultant = entityManager.getReference(Person.class, request.getConsultantId());
            existingOrder.setConsultant(newConsultant);
        }

        return orderRepository.save(existingOrder);
    }

    // Deletes an order by ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Updates an order object and validates its status transition
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        validateStatusTransition(existingOrder, updatedOrder.getStatus());

        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setNote(updatedOrder.getNote());
        existingOrder.setFromAddress(updatedOrder.getFromAddress());
        existingOrder.setToAddress(updatedOrder.getToAddress());
        existingOrder.setStartDate(updatedOrder.getStartDate());
        existingOrder.setEndDate(updatedOrder.getEndDate());
        existingOrder.setServiceType(updatedOrder.getServiceType());

        return orderRepository.save(existingOrder);
    }

    // Partially updates fields of an existing order
    public Order patchOrder(Long id, Map<String, Object> updates) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

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
                        order.setParentOrderId(null);
                    }
                    break;
                default:
                    // Ignore unknown fields
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

    // Validates allowed status transitions to maintain business logic integrity
    private void validateStatusTransition(Order existingOrder, OrderStatus newStatus) {
        OrderStatus currentStatus = existingOrder.getStatus();

        if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot transition directly from PENDING to COMPLETED.");
        }

        if (currentStatus == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot change status once order is CANCELLED.");
        }

        if (currentStatus == OrderStatus.COMPLETED && newStatus != OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot change status once order is COMPLETED.");
        }
    }

}
