package com.example.myfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_groups")
@Getter
@Setter
public class UserGroup {

    @Id
    private UUID groupId = UUID.randomUUID();

    @NotBlank(message = "Поле groupName обязательно для заполнения")
    private String groupName;

    @ElementCollection
    @CollectionTable(name = "user_group_members", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "user_id")
    private List<UUID> usersId;

    private BigDecimal expenseLimit;
}
