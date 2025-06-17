package org.example.themovingcompany.repository;

import org.example.themovingcompany.model.Order;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Repository interface for managing Order entities
public interface OrderRepository extends JpaRepository<Order, Long> {
    // I can add custom query methods here if needed later

    // Finds all orders where the consultant's ID matches
    List<Order> findByConsultantId(Long consultantId);

    // Finds all orders where the customer's ID matches
    List<Order> findByCustomerId(Long customerId);

    // Finds all orders matching a specific status
    List<Order> findByStatus(OrderStatus status);

    // Finds all orders matching a specific service type
    List<Order> findByServiceType(ServiceType serviceType);

    // Finds all orders with startDate between two given dates
    List<Order> findByStartDateBetween(LocalDate from, LocalDate to);

}
