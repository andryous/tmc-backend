package org.example.themovingcompany.model;

import java.util.List;
import java.util.Map;

// This DTO is a container for all the calculated statistics to be sent to the frontend.
public class StatisticsSummaryDTO {

    // --- KPI Cards ---
    private long totalOrders;
    private long ordersInProgress;
    private long pendingServices;
    private double estimatedTotalRevenue;

    // --- Chart Data ---
    // For the Pie Chart (e.g., {"PENDING": 5, "COMPLETED": 10})
    private Map<String, Long> ordersByStatus;
    // For a potential Service Type Pie Chart
    private Map<String, Long> servicesByType;
    // For the Bar Chart (e.g., {"July": 15, "August": 22})
    private Map<String, Long> monthlyOrders;
    // For the Top Performers charts
    private List<TopPerformerDTO> topConsultants;
    private List<TopPerformerDTO> topCustomers;


    // Getters and Setters
    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getOrdersInProgress() {
        return ordersInProgress;
    }

    public void setOrdersInProgress(long ordersInProgress) {
        this.ordersInProgress = ordersInProgress;
    }

    public long getPendingServices() {
        return pendingServices;
    }

    public void setPendingServices(long pendingServices) {
        this.pendingServices = pendingServices;
    }

    public double getEstimatedTotalRevenue() {
        return estimatedTotalRevenue;
    }

    public void setEstimatedTotalRevenue(double estimatedTotalRevenue) {
        this.estimatedTotalRevenue = estimatedTotalRevenue;
    }

    public Map<String, Long> getOrdersByStatus() {
        return ordersByStatus;
    }

    public void setOrdersByStatus(Map<String, Long> ordersByStatus) {
        this.ordersByStatus = ordersByStatus;
    }

    public Map<String, Long> getServicesByType() {
        return servicesByType;
    }

    public void setServicesByType(Map<String, Long> servicesByType) {
        this.servicesByType = servicesByType;
    }

    public Map<String, Long> getMonthlyOrders() {
        return monthlyOrders;
    }

    public void setMonthlyOrders(Map<String, Long> monthlyOrders) {
        this.monthlyOrders = monthlyOrders;
    }

    public List<TopPerformerDTO> getTopConsultants() {
        return topConsultants;
    }

    public void setTopConsultants(List<TopPerformerDTO> topConsultants) {
        this.topConsultants = topConsultants;
    }

    public List<TopPerformerDTO> getTopCustomers() {
        return topCustomers;
    }

    public void setTopCustomers(List<TopPerformerDTO> topCustomers) {
        this.topCustomers = topCustomers;
    }

    // A static inner class to represent a top performer (customer or consultant)
    public static class TopPerformerDTO {
        private String name;
        private long count;

        public TopPerformerDTO(String name, long count) {
            this.name = name;
            this.count = count;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
}