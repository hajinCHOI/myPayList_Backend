package com.mypaylist.backend.domain.pay.dto;

import java.time.LocalDate;

public record PayRecordRequestDto(
    String title,
    int amount,
    LocalDate date,
    Long categoryId
) {}
