// File: src/main/java/org/example/themovingcompany/model/OrderItemResponseDTO.java
package org.example.themovingcompany.model;

import java.time.LocalDate;

// A simplified OrderItem object for API responses.
public class OrderItemResponseDTO {
    private Long id;
    private String serviceType;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String fromAddress;
    private String toAddress;
    private String note;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getFromAddress() { return fromAddress; }
    public void setFromAddress(String fromAddress) { this.fromAddress = fromAddress; }
    public String getToAddress() { return toAddress; }
    public void setToAddress(String toAddress) { this.toAddress = toAddress; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}