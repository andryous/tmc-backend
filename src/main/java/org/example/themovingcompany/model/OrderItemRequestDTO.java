// File: src/main/java/org/example/themovingcompany/model/OrderItemRequestDTO.java
package org.example.themovingcompany.model;

// This DTO represents a single service item within a larger order creation request.
public class OrderItemRequestDTO {

    private String serviceType; // e.g., "MOVING", "PACKING"
    private String fromAddress;
    private String toAddress;
    private String startDate;
    private String endDate;
    private String note;
    private String status; // Status for this specific item, e.g., "PENDING"

    // Getters and Setters
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}