package org.example.themovingcompany.controller;

import jakarta.validation.Valid;
import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.OrderRequestDTO;
import org.example.themovingcompany.model.Person;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.PersonRole;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.PersonRepository;
import org.example.themovingcompany.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // GET /api/orders/by-consultant/{id} --> Returns all orders assigned to a specific consultant.
    @GetMapping("/by-consultant/{id}")
    public List<Order> getOrdersByConsultant(@PathVariable Long id) {
        return orderService.getOrdersByConsultant(id);
    }

    // GET /api/orders/by-customer/{id} --> Returns all orders placed by a specific customer.
    @GetMapping("/by-customer/{id}")
    public List<Order> getOrdersByCustomer(@PathVariable Long id) {
        return orderService.getOrdersByCustomer(id);
    }

    // GET /api/orders/by-status/{status} --> Returns all orders with a specific status.
    @GetMapping("/by-status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    // POST /api/orders --> Creates a new order linked to a person.
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        // Find the customer by ID
        Person customer = personRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + request.getCustomerId()));

        // Find the consultant by ID
        Person consultant = personRepository.findById(request.getConsultantId())
                .orElseThrow(() -> new IllegalArgumentException("Consultant not found with id: " + request.getConsultantId()));

        // Manually build the order from the DTO
        Order order = new Order();
        order.setParentOrderId(request.getParentOrderId());
        order.setFromAddress(request.getFromAddress());
        order.setToAddress(request.getToAddress());
        order.setServiceType(ServiceType.valueOf(request.getServiceType()));
        order.setStartDate(LocalDate.parse(request.getStartDate()));
        order.setEndDate(LocalDate.parse(request.getEndDate()));
        order.setNote(request.getNote());
        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        order.setCustomer(customer);
        order.setConsultant(consultant);

        // Validate that the person assigned as customer really has the CUSTOMER role
        if (!customer.getPersonRole().equals(PersonRole.CUSTOMER)) {
            throw new IllegalArgumentException("Person with ID " + customer.getId() + " is not a customer");
        }

        // Validate that the person assigned as consultant really has the CONSULTANT role
        if (!consultant.getPersonRole().equals(PersonRole.CONSULTANT)) {
            throw new IllegalArgumentException("Person with ID " + consultant.getId() + " is not a consultant");
        }
        // Save and return the new order
        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // GET /api/orders/by-service-type/{type} --> Returns all orders with a specific service type.
    @GetMapping("/by-service-type/{type}")
    public List<Order> getOrdersByServiceType(@PathVariable ServiceType type) {
        return orderService.getOrdersByServiceType(type);
    }


    // GET /api/orders/search?from=YYYY-MM-DD&to=YYYY-MM-DD --> Returns orders within a date range.
    @GetMapping("/search")
    public List<Order> searchOrdersByDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return orderService.searchOrdersByDateRange(from, to);
    }

    // PUT /api/orders/{id} --> Fully updates an existing order based on incoming DTO
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,                             // Extract the order ID from the URL path
            @Valid @RequestBody OrderRequestDTO request) {     // Deserialize and validate the request body into a DTO
        try {
            // Call the service to perform the update using the DTO data
            Order updatedOrder = orderService.updateOrder(id, request);

            // Return 200 OK with the updated order object
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            // If invalid data or ID, return 400 Bad Request with no body
            return ResponseEntity.badRequest().build();
        }
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
