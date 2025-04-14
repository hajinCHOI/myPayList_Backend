package com.mypaylist.backend.domain.pay.dto;

import java.time.LocalDate;

import com.mypaylist.backend.domain.pay.PayRecord;

public record PayRecordResponseDto(
    Long id,
    String title,
    int amount,
    LocalDate date,
    String categoryName
) {
    public static PayRecordResponseDto from(PayRecord payRecord) {
        return new PayRecordResponseDto(
            payRecord.getId(),
            payRecord.getTitle(),
            payRecord.getAmount(),
            payRecord.getDate(),
            payRecord.getCategory().getName()
        );
    }
}

