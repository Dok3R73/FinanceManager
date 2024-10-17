package com.example.myfinance.service;

import com.example.myfinance.entity.Expense;
import com.example.myfinance.entity.User;
import com.example.myfinance.entity.UserGroup;
import com.example.myfinance.repositories.ExpenseRepository;
import com.example.myfinance.repositories.UserGroupRepository;
import com.example.myfinance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private final ExpenseRepository expenseRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserGroupRepository userGroupRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }


    public Expense createExpense(Expense expense) {
        User user = userRepository.findByUserId(expense.getUserId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        expense.setUserId(user.getUserId());

        if (expense.getUserGroupId() != null) {
            if (!expense.getUserGroupId().equals(user.getGroupId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User group not found");
            }

            UserGroup userGroup = userGroupRepository.findById(expense.getUserGroupId()).orElse(null);
            if (userGroup != null && expense.getMoney().compareTo(userGroup.getExpenseLimit()) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense exceeds group limit");
            }
        }

        Expense savedExpense = expenseRepository.save(expense);

        if (expense.getUserGroupId() != null) {
            UserGroup group = userGroupRepository.findById(expense.getUserGroupId()).orElse(null);
            if (group != null) {
                group.setExpenseLimit(group.getExpenseLimit().subtract(expense.getMoney()));
                userGroupRepository.save(group);
            }
        }

        return savedExpense;
    }

    public List<Expense> findAllExpensesUser(UUID userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> findAllExpensesGroup(UUID groupId) {
        UserGroup userGroup = userGroupRepository.findByGroupId(groupId);
        if (userGroup == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User group not found");
        }
        return expenseRepository.findByUserGroupId(groupId);
    }
}
