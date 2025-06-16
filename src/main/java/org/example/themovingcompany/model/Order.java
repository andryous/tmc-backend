package org.example.themovingcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;

import java.time.LocalDate;

@Entity // Marks this class as a JPA entity (database table)
@Table(name = "ORDERS") // avoids conflict with reserved word ORDER
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Auto-generated unique ID.


    //START VALIDATIONS.

    @NotBlank
    private String fromAddress; // Address the customer is moving from.

    @NotBlank
    private String toAddress; // Address the customer is moving to.

    @NotNull
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType; // Type of service: MOVING,PACKING,etc.

    @NotNull
    private LocalDate startDate; // Start date of the service.

    @NotNull
    private LocalDate endDate; // End date of the service.

    private String note; // Optional note from the consultant.

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Current status: PENDING, IN_PROGRESS, etc.


    @ManyToOne
    @JoinColumn(name = "person_id") // FK column to link with Person table.
    @JsonIgnore // Each order is linked to one person (customer or consultant).
    private Person person; // The person who placed the order.

    //END VALIDATIONS.

    // Default CONSTRUCTOR (Required by JPA).
    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}