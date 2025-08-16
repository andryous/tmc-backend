// File: src/main/java/org/example/themovingcompany/repository/OrderRepository.java
package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByConsultantId(Long consultantId);
    List<Order> findByCustomerId(Long customerId);

    // --- Custom Queries for Statistics ---
    Long countByStatus(OrderStatus status);

    @Query("SELECT o.status, COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();

    @Query("SELECT p.firstName || ' ' || p.lastName, COUNT(o) FROM Order o JOIN o.consultant p GROUP BY p.id ORDER BY COUNT(o) DESC LIMIT 5")
    List<Object[]> findTop5ConsultantsByOrderCount();

    @Query("SELECT p.firstName || ' ' || p.lastName, COUNT(o) FROM Order o JOIN o.customer p GROUP BY p.id ORDER BY COUNT(o) DESC LIMIT 5")
    List<Object[]> findTop5CustomersByOrderCount();

    // CHANGED: The query now accepts a startDate parameter instead of calculating it internally.
    @Query("SELECT FUNCTION('MONTHNAME', o.creationDate), COUNT(o) " +
            "FROM Order o " +
            "WHERE o.creationDate >= :startDate " +
            "GROUP BY FUNCTION('MONTHNAME', o.creationDate), FUNCTION('MONTH', o.creationDate) " +
            "ORDER BY FUNCTION('MONTH', o.creationDate)")
    List<Object[]> countOrdersByMonthSince(@Param("startDate") LocalDateTime startDate);
}