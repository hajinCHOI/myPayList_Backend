package com.mypaylist.backend.domain.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mypaylist.backend.domain.category.Category;
import com.mypaylist.backend.domain.category.dto.CategoryRequestDto;
import com.mypaylist.backend.domain.category.dto.CategoryResponseDto;
import com.mypaylist.backend.domain.category.repository.CategoryRepository;
import com.mypaylist.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Transactional
    public void createCategory(CategoryRequestDto requestDto, User user) {
        Category category = new Category(requestDto.name(), requestDto.color(), user);
        categoryRepository.save(category);
    }

    public List<CategoryResponseDto> getCategories(User user) {
        return categoryRepository.findAllByUser(user).stream()
            .map(category -> new CategoryResponseDto(category.getId(), category.getName()))
            .toList();
    }
}
