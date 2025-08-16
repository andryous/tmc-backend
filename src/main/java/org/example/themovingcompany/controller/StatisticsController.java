package org.example.themovingcompany.controller;

import org.example.themovingcompany.model.StatisticsSummaryDTO;
import org.example.themovingcompany.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // This endpoint will be called by our frontend dashboard
    @GetMapping("/summary")
    public ResponseEntity<StatisticsSummaryDTO> getStatisticsSummary() {
        StatisticsSummaryDTO summary = statisticsService.getStatisticsSummary();
        return ResponseEntity.ok(summary);
    }
}