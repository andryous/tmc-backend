// File: src/main/java/org/example/themovingcompany/model/Order.java
package org.example.themovingcompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.example.themovingcompany.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated unique ID.

    // Overall status of the entire order.
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Date the order was created.
    private LocalDateTime creationDate;

    // Last modification date.
    private LocalDateTime lastUpdated;

    // Customer associated with this order.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Person customer;

    // Main consultant assigned to this order.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Person consultant;

    // Consultant who last modified this order.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_consultant_id")
    private Person modifiedBy;

    // One-to-many relationship with order items.
    // CascadeType.ALL: Operations (persist, merge, remove) on Order will cascade to OrderItems.
    // orphanRemoval = true: If an OrderItem is removed from this list, it will be deleted from the database.
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER // Fetch items eagerly with the order
    )
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    // Helper method to manage the bidirectional relationship
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Person getConsultant() {
        return consultant;
    }

    public void setConsultant(Person consultant) {
        this.consultant = consultant;
    }

    public Person getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Person modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}