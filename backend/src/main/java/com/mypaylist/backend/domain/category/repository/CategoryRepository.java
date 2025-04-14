package com.mypaylist.backend.domain.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mypaylist.backend.domain.category.Category;
import com.mypaylist.backend.domain.user.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser(User user);
}
