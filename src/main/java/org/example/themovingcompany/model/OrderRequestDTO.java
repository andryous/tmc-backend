// File: src/main/java/org/example/themovingcompany/model/OrderRequestDTO.java
package org.example.themovingcompany.model;

import java.util.List;

// This DTO represents the entire request to create a new order,
// which now contains general information and a list of services.
public class OrderRequestDTO {

    private Long customerId; // ID of the customer placing the order
    private Long consultantId; // ID of the main consultant assigned to the order
    private Long modifiedByConsultantId; // ID of the consultant modifying the order
    private String status; // Overall status of the order, e.g., "PENDING"

    // A list of services to be included in this order.
    private List<OrderItemRequestDTO> items;


    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public Long getModifiedByConsultantId() {
        return modifiedByConsultantId;
    }


    public void setModifiedByConsultantId(Long modifiedByConsultantId) {
        this.modifiedByConsultantId = modifiedByConsultantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }
}