// File: src/main/java/org/example/themovingcompany/repository/OrderItemRepository.java
package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.OrderItem;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Repository interface for managing OrderItem entities
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // --- ADDED: Custom Queries for Statistics ---

    // Counts service items for a specific status (e.g., PENDING)
    Long countByStatus(OrderStatus status);

    // Counts completed service items to calculate revenue
    Long countByStatusAndServiceType(OrderStatus status, ServiceType serviceType);

    // Groups service items by type and returns a list of [ServiceType, Count]
    @Query("SELECT i.serviceType, COUNT(i) FROM OrderItem i GROUP BY i.serviceType")
    List<Object[]> countServicesByType();
}