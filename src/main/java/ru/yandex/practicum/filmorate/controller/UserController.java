package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validations.ValidateUser;

import java.util.*;

import static ru.yandex.practicum.filmorate.validations.ValidateUser.validateUser;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private Integer idUser = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.info("Получен запрос на получение списка пользователей");
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validateUser(user);
        String email = user.getEmail();
        for (User user1 : users.values()) {
            if (user1.getEmail().equals(email)) {
                log.warn("Пользователь с {} уже зарегистрирован", email);
                throw new ValidationException("Пользователь с таким e-mail уже зарегистрирован");
            }
        }
        String login = user.getLogin();
        for (User userLogin : users.values()) {
            if (userLogin.getLogin().equals(login)) {
                log.warn("Пользователь с логином {} уже зарегистрирован", login);
                throw new ValidationException("Пользователь с таким логином уже зарегистрирован");
            }
        }
        user.setId(idUser);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: '{}'", user);
        this.idUser ++;
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validateUser(user);
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с id={} не существует.", user.getId());
            throw new ValidationException("Пользователь с введеным id не существует");
        }
        users.put(user.getId(), user);
        log.info("Обновлен пользователь: '{}'", user);
        return user;
    }
}
