package com.mypaylist.backend.domain.statistics.controller;

import com.mypaylist.backend.domain.statistics.dto.CategoryStatDto;
import com.mypaylist.backend.domain.statistics.service.StatisticsService;
import com.mypaylist.backend.domain.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<List<CategoryStatDto>> getStats(
        @AuthenticationPrincipal User user,
        @RequestParam int year,
        @RequestParam int month
    ) {
        List<CategoryStatDto> stats = statisticsService.getMonthlyStats(user, year, month);
        return ResponseEntity.ok(stats);
    }
}
