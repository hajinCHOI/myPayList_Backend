package com.mypaylist.backend.domain.pay.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypaylist.backend.domain.pay.dto.PayRecordRequestDto;
import com.mypaylist.backend.domain.pay.dto.PayRecordResponseDto;
import com.mypaylist.backend.domain.pay.service.PayRecordService;
import com.mypaylist.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payrecords")
@RequiredArgsConstructor
public class PayRecordController {

    private final PayRecordService payRecordService;

    @PostMapping
    public ResponseEntity<Void> createPayRecord(
        @RequestBody PayRecordRequestDto dto,
        @AuthenticationPrincipal User user
    ) {
        payRecordService.createPayRecord(dto, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PayRecordResponseDto>> getPayRecords(
        @AuthenticationPrincipal User user
    ) {
        List<PayRecordResponseDto> list = payRecordService.getPayRecords(user);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePayRecord(
        @PathVariable Long id,
        @RequestBody PayRecordRequestDto dto,
        @AuthenticationPrincipal User user
    ) {
        payRecordService.updatePayRecord(id, dto, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayRecord(
        @PathVariable Long id,
        @AuthenticationPrincipal User user
    ) {
        payRecordService.deletePayRecord(id, user);
        return ResponseEntity.noContent().build();
    }


}

