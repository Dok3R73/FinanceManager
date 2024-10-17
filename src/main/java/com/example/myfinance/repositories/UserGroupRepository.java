package com.example.myfinance.repositories;

import com.example.myfinance.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UUID> {

    UserGroup findByGroupId(UUID groupId);
}
