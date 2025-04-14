package com.mypaylist.backend.domain.statistics.service;

import com.mypaylist.backend.domain.statistics.dto.CategoryStatDto;
import com.mypaylist.backend.domain.pay.repository.PayRecordRepository;
import com.mypaylist.backend.domain.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final PayRecordRepository payRecordRepository;

    public List<CategoryStatDto> getMonthlyStats(User user, int year, int month) {
        return payRecordRepository.getMonthlyCategoryStats(user, year, month);
    }
}
