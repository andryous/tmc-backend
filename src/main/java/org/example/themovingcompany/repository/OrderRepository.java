package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for managing Order entities
public interface OrderRepository extends JpaRepository<Order, Long> {
    // I can add custom query methods here if needed later
}
