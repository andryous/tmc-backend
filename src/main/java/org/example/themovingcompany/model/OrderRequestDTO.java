package org.example.themovingcompany.model;

public class OrderRequestDTO {

    private String fromAddress;
    private String toAddress;
    private String serviceType;
    private String startDate;
    private String endDate;
    private String note;
    private String status;
    private Long personId;

    // Getters
    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public Long getPersonId() {
        return personId;
    }

    // Setters
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
