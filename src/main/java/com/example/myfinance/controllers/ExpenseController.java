package com.example.myfinance.controllers;

import com.example.myfinance.entity.Expense;
import com.example.myfinance.repositories.ExpenseRepository;
import com.example.myfinance.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления расходами.
 * Предоставляет REST API для создания и получения расходов.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseService expenseService;

    /**
     * Создает новый расход.
     *
     * @param expense объект расхода, который нужно создать
     * @return ResponseEntity с созданным расходом и статусом 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense savedExpense = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    /**
     * Получает все расходы для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return ResponseEntity со списком расходов и статусом 200 (OK)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable UUID userId) {
        List<Expense> expenses = expenseService.findAllExpensesUser(userId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Получает все расходы для указанной группы.
     *
     * @param groupId идентификатор группы
     * @return ResponseEntity со списком расходов и статусом 200 (OK)
     */
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<Expense>> getExpensesByGroupId(@PathVariable UUID groupId) {
        List<Expense> expenses = expenseService.findAllExpensesGroup(groupId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Получает все расходы. Доступно только для пользователей с ролью ADMIN.
     *
     * @return ResponseEntity со списком всех расходов и статусом 200 (OK)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getExpensesFindAll() {
        List<Expense> expenses = expenseRepository.findAll();
        return ResponseEntity.ok(expenses);
    }
}
