package com.example.myfinance.controllers;

import com.example.myfinance.entity.UserGroup;
import com.example.myfinance.repositories.UserGroupRepository;
import com.example.myfinance.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Контроллер для управления группами пользователей.
 * Этот контроллер обрабатывает запросы, связанные с созданием групп пользователей и установкой лимитов расходов.
 */
@RestController
@RequestMapping("/user-groups")
public class UserGroupController {

    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserGroupService userGroupService;

    /**
     * Создает новую группу пользователей.
     *
     * @param userGroup объект группы пользователей, который необходимо создать.
     * @return ResponseEntity с созданной группой пользователей и статусом 201 (Создано),
     * или статусом 404 (Не найдено), если группа не была создана.
     */
    @PostMapping
    public ResponseEntity<UserGroup> createUserGroup(@Valid @RequestBody UserGroup userGroup) {
        UserGroup savedGroup = userGroupService.createUserGroup(userGroup);
        if (savedGroup != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGroup);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Устанавливает лимит расходов для указанной группы пользователей.
     *
     * @param groupId идентификатор группы пользователей, для которой необходимо установить лимит.
     * @param limit   лимит расходов, который необходимо установить.
     * @return ResponseEntity с обновленной группой пользователей и статусом 200 (ОК),
     * или статусом 404 (Не найдено), если группа не была найдена.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{groupId}/expense-limit")
    public ResponseEntity<UserGroup> setExpenseLimit(@PathVariable UUID groupId, @RequestBody BigDecimal limit) {
        return userGroupRepository.findById(groupId)
                .map(group -> {
                    group.setExpenseLimit(limit);
                    UserGroup updatedGroup = userGroupRepository.save(group);
                    return ResponseEntity.ok(updatedGroup);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
