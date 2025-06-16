package org.example.themovingcompany.controller;

import jakarta.validation.Valid;
import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.OrderRequestDTO;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.PersonRepository;
import org.example.themovingcompany.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders") // Base path for all order endpoints.
public class OrderController {

    private final OrderService orderService;
    private final PersonRepository personRepository;

    // Constructor to inject dependencies
    public OrderController(OrderService orderService, PersonRepository personRepository) {
        this.orderService = orderService;
        this.personRepository = personRepository;
    }

    // GET /api/orders --> Returns all orders.
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET /api/orders/{id} --> Returns one order by ID.
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/orders --> Creates a new order linked to a person.
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        // Find the person by ID
        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + request.getPersonId()));

        // Manually build the order from the DTO
        Order order = new Order();
        order.setFromAddress(request.getFromAddress());
        order.setToAddress(request.getToAddress());
        order.setServiceType(ServiceType.valueOf(request.getServiceType()));
        order.setStartDate(LocalDate.parse(request.getStartDate()));
        order.setEndDate(LocalDate.parse(request.getEndDate()));
        order.setNote(request.getNote());
        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        order.setPerson(person); // Link the order to the person

        // Save and return the new order
        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // PUT /api/orders/{id} --> Updates an existing order.
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody Order updatedOrder) {

        Order updated = orderService.updateOrder(id, updatedOrder);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/orders/{id} --> Partially update an order.
    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        try {
            Order updatedOrder = orderService.patchOrder(id, updates);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    // DELETE /api/orders/{id} --> Deletes an order by ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
