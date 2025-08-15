// File: src/main/java/org/example/themovingcompany/model/OrderItem.java
package org.example.themovingcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;

import java.time.LocalDate;

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for this specific service item.

    @NotNull
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType; // The type of service (MOVING, PACKING, CLEANING).

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // The status of this specific service.

    @NotNull
    private LocalDate startDate; // Start date for this specific service.

    @NotNull
    private LocalDate endDate; // End date for this specific service.

    // Addresses can be specific to the item.
    private String fromAddress;
    private String toAddress;

    private String note; // Optional note for this specific service.

    // Establishes the many-to-one relationship with the main Order.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // This column in the ORDER_ITEMS table will link to the ORDERS table.
    @JsonIgnore // Prevents infinite loops during JSON serialization.
    private Order order;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getNote() {
        return note;
    }



    public void setNote(String note) {
        this.note = note;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}