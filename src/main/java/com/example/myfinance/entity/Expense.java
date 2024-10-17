package com.example.myfinance.entity;

import com.example.myfinance.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Поле money обязательно для заполнения.")
    private BigDecimal money;

    @NotNull(message = "Поле userId обязательно для заполнения.")
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "user_group_id")
    private UUID userGroupId;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private ExpenseCategory expenseCategory;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
