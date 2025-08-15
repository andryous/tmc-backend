// File: src/main/java/org/example/themovingcompany/service/OrderService.java
package org.example.themovingcompany.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.themovingcompany.model.*;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.OrderRepository;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderService(OrderRepository orderRepository, PersonRepository personRepository) {
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderResponseDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    // FIX: Changed return type from Order to OrderResponseDTO
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {
        Person customer = personRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Person consultant = personRepository.findById(requestDTO.getConsultantId())
                .orElseThrow(() -> new IllegalArgumentException("Consultant not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setConsultant(consultant);
        order.setStatus(OrderStatus.valueOf(requestDTO.getStatus().toUpperCase()));

        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one service item.");
        }

        for (OrderItemRequestDTO itemDTO : requestDTO.getItems()) {
            OrderItem item = new OrderItem();
            item.setServiceType(ServiceType.valueOf(itemDTO.getServiceType().toUpperCase()));
            item.setStatus(OrderStatus.valueOf(itemDTO.getStatus().toUpperCase()));
            item.setStartDate(LocalDate.parse(itemDTO.getStartDate()));
            item.setEndDate(LocalDate.parse(itemDTO.getEndDate()));
            item.setFromAddress(itemDTO.getFromAddress());
            item.setToAddress(itemDTO.getToAddress());
            item.setNote(itemDTO.getNote());
            order.addItem(item);
        }

        Order savedOrder = orderRepository.save(order);
        // FIX: Convert the saved entity to a DTO before returning
        return convertToDTO(savedOrder);
    }

    @Transactional
    public Order updateOrder(Long id, OrderRequestDTO requestDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        Person customer = personRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Person consultant = personRepository.findById(requestDTO.getConsultantId())
                .orElseThrow(() -> new IllegalArgumentException("Consultant not found"));

        order.setCustomer(customer);
        order.setConsultant(consultant);
        order.setStatus(OrderStatus.valueOf(requestDTO.getStatus().toUpperCase()));

        order.getItems().clear();

        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("An updated order must contain at least one service item.");
        }

        for (OrderItemRequestDTO itemDTO : requestDTO.getItems()) {
            OrderItem item = new OrderItem();
            item.setServiceType(ServiceType.valueOf(itemDTO.getServiceType().toUpperCase()));
            item.setStatus(OrderStatus.valueOf(itemDTO.getStatus().toUpperCase()));
            item.setStartDate(LocalDate.parse(itemDTO.getStartDate()));
            item.setEndDate(LocalDate.parse(itemDTO.getEndDate()));
            item.setFromAddress(itemDTO.getFromAddress());
            item.setToAddress(itemDTO.getToAddress());
            item.setNote(itemDTO.getNote());
            order.addItem(item);
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order patchOrder(Long id, Map<String, Object> updates) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "status":
                    if (value instanceof String) {
                        order.setStatus(OrderStatus.valueOf(((String) value).toUpperCase()));
                    }
                    break;
                case "consultantId":
                    if (value instanceof Number) {
                        Person newConsultant = personRepository.findById(((Number) value).longValue())
                                .orElseThrow(() -> new IllegalArgumentException("Consultant not found"));
                        order.setConsultant(newConsultant);
                    }
                    break;
            }
        });
        return orderRepository.save(order);
    }

    private OrderResponseDTO convertToDTO(Order order) {
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus().name());
        orderDTO.setCreationDate(order.getCreationDate());
        orderDTO.setLastUpdated(order.getLastUpdated());

        Person customer = order.getCustomer();
        orderDTO.setCustomer(new PersonResponseDTO(customer.getId(), customer.getFirstName(), customer.getLastName()));

        Person consultant = order.getConsultant();
        if (consultant != null) {
            orderDTO.setConsultant(new PersonResponseDTO(consultant.getId(), consultant.getFirstName(), consultant.getLastName()));
        }

        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream().map(item -> {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setId(item.getId());
            itemDTO.setServiceType(item.getServiceType().name());
            itemDTO.setStatus(item.getStatus().name());
            itemDTO.setStartDate(item.getStartDate());
            itemDTO.setEndDate(item.getEndDate());
            itemDTO.setFromAddress(item.getFromAddress());
            itemDTO.setToAddress(item.getToAddress());
            itemDTO.setNote(item.getNote());
            return itemDTO;
        }).collect(Collectors.toList());

        orderDTO.setItems(itemDTOs);

        return orderDTO;
    }
}