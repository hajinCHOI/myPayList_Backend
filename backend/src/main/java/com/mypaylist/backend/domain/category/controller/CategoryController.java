package com.mypaylist.backend.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypaylist.backend.domain.category.dto.CategoryRequestDto;
import com.mypaylist.backend.domain.category.dto.CategoryResponseDto;
import com.mypaylist.backend.domain.category.service.CategoryService;
import com.mypaylist.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories(@AuthenticationPrincipal User user) {
        System.out.println("여기는 왔다.");
        // user가 null이면 인증 실패입니다
        if (user == null) {
            throw new RuntimeException("인증된 유저 정보 없음"); // 이걸 로그로 확인해봐요!
        }
        List<CategoryResponseDto> categories = categoryService.getCategories(user);
        
        System.out.println(user.getNickname());
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
        @RequestBody CategoryRequestDto requestDto,
        @AuthenticationPrincipal User user) {

        if (user == null) {
            throw new RuntimeException("인증된 유저 정보 없음");
        }

        categoryService.createCategory(requestDto, user);
        return ResponseEntity.ok().build();
    }
}
