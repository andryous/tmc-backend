// File: src/main/java/org/example/themovingcompany/repository/OrderRepository.java
package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository interface for managing Order entities
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Finds all orders where the consultant's ID matches
    List<Order> findByConsultantId(Long consultantId);

    // Finds all orders where the customer's ID matches
    List<Order> findByCustomerId(Long customerId);

    // NOTE: We have removed findByStatus, findByServiceType, and findByStartDateBetween
    // because those properties now belong to OrderItem, not the Order itself.
    // We will add new, more complex queries later if needed for searching.
}