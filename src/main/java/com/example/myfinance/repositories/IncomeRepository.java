package com.example.myfinance.repositories;

import com.example.myfinance.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Income save(Income income);

    List<Income> findByUserId(UUID userId);

    List<Income> findByUserGroupId(UUID groupId);
}
