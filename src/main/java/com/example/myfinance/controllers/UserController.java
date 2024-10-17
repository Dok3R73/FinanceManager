package com.example.myfinance.controllers;

import com.example.myfinance.entity.User;
import com.example.myfinance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Контроллер для управления пользователями.
 * Обрабатывает HTTP-запросы, связанные с пользователями, такие как создание нового пользователя
 * и получение информации о пользователе по его идентификатору.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Создает нового пользователя.
     *
     * @param user объект пользователя, который необходимо создать
     * @return ResponseEntity с созданным пользователем и статусом 200 (OK)
     */
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return ResponseEntity с найденным пользователем и статусом 200 (OK),
     * или статусом 404 (Not Found), если пользователь не найден
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
