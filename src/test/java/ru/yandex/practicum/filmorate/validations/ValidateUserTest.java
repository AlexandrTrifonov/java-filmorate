package ru.yandex.practicum.filmorate.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidateUserTest {

    @Test
    void shouldThrowValidateExceptionIfUserEmailIsBlank() {
        ValidateUser validate = new ValidateUser();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        user.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> validate.validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserEmailWithoutDog() {
        ValidateUser validate = new ValidateUser();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        user.setEmail("222ya.ru");
        Assertions.assertThrows(ValidationException.class, () -> validate.validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserLoginIsBlank() {
        ValidateUser validate = new ValidateUser();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        user.setLogin(" ");
        Assertions.assertThrows(ValidationException.class, () -> validate.validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserLoginWithSpace() {
        ValidateUser validate = new ValidateUser();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        user.setLogin("Ло гин");
        Assertions.assertThrows(ValidationException.class, () -> validate.validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserNameIsBlank() {
        ValidateUser validate = new ValidateUser();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        user.setName("");
        validate.validateUser(user);
        Assertions.assertEquals("Логин", user.getName());
    }

    @Test
    void shouldThrowValidateExceptionIfUserBirthdayInFuture() {
        ValidateUser validate = new ValidateUser();
        User user = new User("222@ya.ru", "Логин", "Имя пользователя", LocalDate.of(1977,11,11));
        user.setBirthday(LocalDate.of(2025,01,01));
        Assertions.assertThrows(ValidationException.class, () -> validate.validateUser(user));
    }
}