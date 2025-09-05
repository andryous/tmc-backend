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


    /**
     * Finds all orders and sorts them by ID in descending order.
     * This will show the most recently created orders first.
     * @return A list of orders sorted by ID descending.
     */
    List<Order> findAllByOrderByIdDesc();


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

    @Query(value = "SELECT TO_CHAR(o.creation_date, 'Month'), COUNT(o.id) " +
            "FROM orders o " +
            "WHERE o.creation_date >= :startDate " +
            "GROUP BY TO_CHAR(o.creation_date, 'Month'), EXTRACT(MONTH FROM o.creation_date) " +
            "ORDER BY EXTRACT(MONTH FROM o.creation_date)",
            nativeQuery = true)
    List<Object[]> countOrdersByMonthSince(@Param("startDate") LocalDateTime startDate);
}