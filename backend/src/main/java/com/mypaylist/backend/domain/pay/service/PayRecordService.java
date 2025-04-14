package com.mypaylist.backend.domain.pay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mypaylist.backend.domain.category.Category;
import com.mypaylist.backend.domain.category.repository.CategoryRepository;
import com.mypaylist.backend.domain.pay.PayRecord;
import com.mypaylist.backend.domain.pay.dto.PayRecordRequestDto;
import com.mypaylist.backend.domain.pay.dto.PayRecordResponseDto;
import com.mypaylist.backend.domain.pay.repository.PayRecordRepository;
import com.mypaylist.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayRecordService {

    private final PayRecordRepository payRecordRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createPayRecord(PayRecordRequestDto dto, User user) {
        Category category = categoryRepository.findById(dto.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        PayRecord payRecord = new PayRecord(
            dto.title(),
            dto.amount(),
            dto.date(),
            user,
            category
        );

        payRecordRepository.save(payRecord);
    }

    public List<PayRecordResponseDto> getPayRecords(User user) {
        List<PayRecord> records = payRecordRepository.findAllByUser(user);
        return records.stream()
                .map(PayRecordResponseDto::from)
                .toList();
    }
    @Transactional
    public void updatePayRecord(Long id, PayRecordRequestDto dto, User user) {
        PayRecord payRecord = payRecordRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 내역이 존재하지 않습니다."));

        if (!payRecord.getUser().getId().equals(user.getId())) {
            throw new SecurityException("해당 유저의 내역이 아닙니다.");
        }

        Category category = categoryRepository.findById(dto.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        payRecord.update(dto.title(), dto.amount(), dto.date(), category);
    }

    @Transactional
    public void deletePayRecord(Long id, User user) {
        PayRecord payRecord = payRecordRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 내역이 존재하지 않습니다."));

        if (!payRecord.getUser().getId().equals(user.getId())) {
            throw new SecurityException("해당 유저의 내역이 아닙니다.");
        }

        payRecordRepository.delete(payRecord);
    }
}
