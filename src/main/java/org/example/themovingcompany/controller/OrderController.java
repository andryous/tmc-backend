package org.example.themovingcompany.controller;

import jakarta.validation.Valid;
import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders") // Base path for all order endpoints.
public class OrderController {

    private final OrderService orderService;

    //CONSTRUCTOR injection of the service layer.
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /api/orders --> Returns all orders.
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    // GET /api/orders --> Returns one order by ID.
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/orders --> Creates new order.
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // DELETE /api/orders --> Delete an order by ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
