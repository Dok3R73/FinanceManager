package com.example.myfinance.controllers;

import com.example.myfinance.entity.Income;
import com.example.myfinance.repositories.IncomeRepository;
import com.example.myfinance.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления доходами.
 * Предоставляет REST API для создания и получения доходов.
 */
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private IncomeService incomeService;

    /**
     * Создает новый доход.
     *
     * @param income объект дохода, который необходимо создать
     * @return ResponseEntity с созданным доходом и статусом 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<Income> createIncome(@Valid @RequestBody Income income) {
        Income savedIncome = incomeService.createIncome(income);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncome);
    }

    /**
     * Получает список доходов по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return ResponseEntity со списком доходов и статусом 200 (OK)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Income>> getIncomesByUserId(@PathVariable UUID userId) {
        List<Income> incomes = incomeService.findAllIncomesUser(userId);
        return ResponseEntity.ok(incomes);
    }

    /**
     * Получает список доходов по идентификатору группы.
     *
     * @param groupId идентификатор группы
     * @return ResponseEntity со списком доходов и статусом 200 (OK)
     */
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<Income>> getIncomesByGroupId(@PathVariable UUID groupId) {
        List<Income> incomes = incomeService.findAllIncomesGroup(groupId);
        return ResponseEntity.ok(incomes);
    }

    /**
     * Получает список всех доходов. Доступно только для пользователей с ролью ADMIN.
     *
     * @return ResponseEntity со списком всех доходов и статусом 200 (OK)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Income>> getIncomesFindAll() {
        List<Income> incomes = incomeRepository.findAll();
        return ResponseEntity.ok(incomes);
    }
}
