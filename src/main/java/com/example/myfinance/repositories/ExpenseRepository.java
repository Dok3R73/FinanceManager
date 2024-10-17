package com.example.myfinance.repositories;

import com.example.myfinance.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense save(Expense expense);

    List<Expense> findByUserGroupId(UUID userGroupId);

    List<Expense> findByUserId(UUID userId);
}
