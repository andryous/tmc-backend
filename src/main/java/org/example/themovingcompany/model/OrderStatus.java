package org.example.themovingcompany.model;

// Enum for the current status of an order
public enum OrderStatus {
    PENDING,       // The service is scheduled but not started
    IN_PROGRESS,   // The team is currently executing the job
    COMPLETED,     // The job has been completed successfully
    CANCELLED      // The order was cancelled by the customer
}