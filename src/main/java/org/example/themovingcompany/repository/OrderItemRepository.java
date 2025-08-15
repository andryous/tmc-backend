// File: src/main/java/org/example/themovingcompany/repository/OrderItemRepository.java
package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for managing OrderItem entities
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}