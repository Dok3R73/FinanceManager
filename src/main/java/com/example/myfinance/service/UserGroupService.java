package com.example.myfinance.service;

import com.example.myfinance.entity.User;
import com.example.myfinance.entity.UserGroup;
import com.example.myfinance.repositories.UserGroupRepository;
import com.example.myfinance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    public UserGroupService(UserGroupRepository userGroupRepository, UserRepository userRepository) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }

    public UserGroup createUserGroup(UserGroup userGroup) {
        List<UUID> usersToAdd = new ArrayList<>();

        for (UUID userId : userGroup.getUsersId()) {
            User user = userRepository.findByUserId(userId);
            if (user != null) {
                if (user.getGroupId() == null) {
                    usersToAdd.add(userId);
                }
            }
        }

        if (!usersToAdd.isEmpty()) {
            UserGroup newGroup = new UserGroup();
            newGroup.setUsersId(usersToAdd);
            newGroup.setGroupName(userGroup.getGroupName());
            newGroup.setExpenseLimit(userGroup.getExpenseLimit());

            UserGroup savedGroup = userGroupRepository.save(newGroup);

            for (UUID userId : usersToAdd) {
                User user = userRepository.findByUserId(userId);
                if (user != null) {
                    user.setGroupId(savedGroup.getGroupId());
                    userRepository.save(user);
                }
            }

            return savedGroup;
        }
        return null; // Если не удалось создать группу, возвращаем null
    }
}
