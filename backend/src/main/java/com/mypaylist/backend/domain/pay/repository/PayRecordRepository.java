package com.mypaylist.backend.domain.pay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mypaylist.backend.domain.pay.PayRecord;
import com.mypaylist.backend.domain.statistics.dto.CategoryStatDto;
import com.mypaylist.backend.domain.user.User;

public interface PayRecordRepository extends JpaRepository<PayRecord, Long> {
    List<PayRecord> findAllByUser(User user); // 사용자별 소비 기록 조회용
    

    @Query("SELECT new com.mypaylist.backend.domain.statistics.dto.CategoryStatDto(p.category.name, SUM(p.amount)) " +
    "FROM PayRecord p " +
    "WHERE p.user = :user AND YEAR(p.date) = :year AND MONTH(p.date) = :month " +
    "GROUP BY p.category.name")
List<CategoryStatDto> getMonthlyCategoryStats(@Param("user") User user,
    @Param("year") int year,
    @Param("month") int month);

}
