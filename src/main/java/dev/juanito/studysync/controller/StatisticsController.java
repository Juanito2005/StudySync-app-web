package dev.juanito.studysync.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.juanito.studysync.dto.StatsResponseDto;
import dev.juanito.studysync.service.StatisticsService;

@RestController
@RequestMapping("/api/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<StatsResponseDto> getStatistics() {
        StatsResponseDto stats = statisticsService.getUserStatistics();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
