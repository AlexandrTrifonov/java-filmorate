package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
/*
    @Test
    void shouldThrowValidationExceptionIfUserCreateWithAlreadyExistEmail() {
        UserController userController = new UserController();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        userController.createUser(user);
        User userNew = new User("222@ya.ru", "ЛогинНовый", "Имя нового пользователя", LocalDate.of(1987,11,11));
        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(userNew));
    }

    @Test
    void shouldThrowValidationExceptionIfUserUpdateWithWrongId() {
        UserController userController = new UserController();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        userController.createUser(user);
        User userUpdate = new User("333@ya.ru", "Логин", "Имя пользователя новое", LocalDate.of(1977,11,11));
        Integer idWrong = user.getId() +1;
        userUpdate.setId(idWrong);
        Assertions.assertThrows(ValidationException.class, () -> userController.updateUser(userUpdate));
    }*/
}