// File: src/main/java/org/example/themovingcompany/model/OrderResponseDTO.java
package org.example.themovingcompany.model;

import java.time.LocalDateTime;
import java.util.List;

// The main response DTO for an Order.
public class OrderResponseDTO {
    private Long id;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdated;
    private PersonResponseDTO customer;
    private PersonResponseDTO consultant;
    private List<OrderItemResponseDTO> items;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    public PersonResponseDTO getCustomer() { return customer; }
    public void setCustomer(PersonResponseDTO customer) { this.customer = customer; }
    public PersonResponseDTO getConsultant() { return consultant; }
    public void setConsultant(PersonResponseDTO consultant) { this.consultant = consultant; }
    public List<OrderItemResponseDTO> getItems() { return items; }
    public void setItems(List<OrderItemResponseDTO> items) { this.items = items; }
}