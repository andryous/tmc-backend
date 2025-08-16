package org.example.themovingcompany.service;

import org.example.themovingcompany.model.StatisticsSummaryDTO;
import org.example.themovingcompany.model.enums.OrderStatus;
import org.example.themovingcompany.model.enums.ServiceType;
import org.example.themovingcompany.repository.OrderItemRepository;
import org.example.themovingcompany.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // ADDED
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public StatisticsService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public StatisticsSummaryDTO getStatisticsSummary() {
        StatisticsSummaryDTO dto = new StatisticsSummaryDTO();

        // --- 1. Calculate KPI Card Metrics ---
        dto.setTotalOrders(orderRepository.count());
        dto.setOrdersInProgress(orderRepository.countByStatus(OrderStatus.IN_PROGRESS));
        dto.setPendingServices(orderItemRepository.countByStatus(OrderStatus.PENDING));
        dto.setEstimatedTotalRevenue(calculateRevenue());

        // --- 2. Calculate Data for Charts ---
        Map<String, Long> ordersByStatus = orderRepository.countOrdersByStatus().stream()
                .collect(Collectors.toMap(row -> ((OrderStatus) row[0]).name(), row -> (Long) row[1]));
        dto.setOrdersByStatus(ordersByStatus);

        Map<String, Long> servicesByType = orderItemRepository.countServicesByType().stream()
                .collect(Collectors.toMap(row -> ((ServiceType) row[0]).name(), row -> (Long) row[1]));
        dto.setServicesByType(servicesByType);

        List<StatisticsSummaryDTO.TopPerformerDTO> topConsultants = orderRepository.findTop5ConsultantsByOrderCount().stream()
                .map(row -> new StatisticsSummaryDTO.TopPerformerDTO((String) row[0], (Long) row[1]))
                .collect(Collectors.toList());
        dto.setTopConsultants(topConsultants);

        List<StatisticsSummaryDTO.TopPerformerDTO> topCustomers = orderRepository.findTop5CustomersByOrderCount().stream()
                .map(row -> new StatisticsSummaryDTO.TopPerformerDTO((String) row[0], (Long) row[1]))
                .collect(Collectors.toList());
        dto.setTopCustomers(topCustomers);

        // CHANGED: Calculate the start date here and pass it to the repository
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        Map<String, Long> monthlyOrders = orderRepository.countOrdersByMonthSince(oneYearAgo).stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));
        dto.setMonthlyOrders(monthlyOrders);


        return dto;
    }

    private double calculateRevenue() {
        long completedMovings = orderItemRepository.countByStatusAndServiceType(OrderStatus.COMPLETED, ServiceType.MOVING);
        long completedPackings = orderItemRepository.countByStatusAndServiceType(OrderStatus.COMPLETED, ServiceType.PACKING);
        long completedCleanings = orderItemRepository.countByStatusAndServiceType(OrderStatus.COMPLETED, ServiceType.CLEANING);

        return (completedMovings * 1500.0) + (completedPackings * 500.0) + (completedCleanings * 300.0);
    }
}