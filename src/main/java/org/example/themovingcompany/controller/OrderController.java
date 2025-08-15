// File: src/main/java/org/example/themovingcompany/controller/OrderController.java
package org.example.themovingcompany.controller;

import jakarta.validation.Valid;
import org.example.themovingcompany.model.OrderRequestDTO;
import org.example.themovingcompany.model.OrderResponseDTO;
import org.example.themovingcompany.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Optional<OrderResponseDTO> orderDTO = orderService.getOrderById(id);
        return orderDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    // FIX: Cleaned up the method to delegate logic to the service and return a DTO
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDTO request) {
        try {
            OrderResponseDTO savedOrder = orderService.createOrder(request);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequestDTO request) {
        try {
            orderService.updateOrder(id, request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchOrder(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            orderService.patchOrder(id, updates);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}