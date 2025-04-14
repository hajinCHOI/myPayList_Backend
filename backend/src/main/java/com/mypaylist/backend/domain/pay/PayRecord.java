package com.mypaylist.backend.domain.pay;

import com.mypaylist.backend.domain.user.User;
import com.mypaylist.backend.domain.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class PayRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int amount;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public PayRecord(String title, int amount, LocalDate date, User user, Category category) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.category = category;
    }

    public void update(String title, int amount, LocalDate date, Category category) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
    
}