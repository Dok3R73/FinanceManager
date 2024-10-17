package com.example.myfinance.entity;

import com.example.myfinance.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private UUID userId = UUID.randomUUID();

    @NotBlank(message = "Поле name обязательно для заполнения")
    private String name;

    @NotBlank(message = "Поле password обязательно для заполнения")
    private String password;

    @NotBlank(message = "Поле email обязательно для заполнения")
    @Column(unique = true)
    private String email;

    private UUID groupId;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
