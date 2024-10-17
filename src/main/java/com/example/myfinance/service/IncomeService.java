package com.example.myfinance.service;

import com.example.myfinance.entity.Income;
import com.example.myfinance.entity.User;
import com.example.myfinance.entity.UserGroup;
import com.example.myfinance.repositories.IncomeRepository;
import com.example.myfinance.repositories.UserGroupRepository;
import com.example.myfinance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class IncomeService {

    @Autowired
    private final IncomeRepository incomeRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserGroupRepository userGroupRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public Income createIncome(Income income) {
        User user = userRepository.findByUserId(income.getUserId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        income.setUserId(user.getUserId());

        if (income.getUserGroupId() != null) {
            if (!income.getUserGroupId().equals(user.getGroupId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User group not found");
            }
        }
        return incomeRepository.save(income);
    }

    public List<Income> findAllIncomesUser(UUID userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return incomeRepository.findByUserId(userId);
    }

    public List<Income> findAllIncomesGroup(UUID groupId) {
        UserGroup userGroup = userGroupRepository.findByGroupId(groupId);
        if (userGroup == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User group not found");
        }
        return incomeRepository.findByUserGroupId(groupId);
    }
}
