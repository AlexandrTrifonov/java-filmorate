package ru.yandex.practicum.filmorate.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import static ru.yandex.practicum.filmorate.service.ValidateUser.validateUser;

class ValidateUserTest {

    @Test
    void shouldThrowValidateExceptionIfUserEmailIsBlank() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        user.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserEmailWithoutDog() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        user.setEmail("222ya.ru");
        Assertions.assertThrows(ValidationException.class, () -> validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserLoginIsBlank() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        user.setLogin(" ");
        Assertions.assertThrows(ValidationException.class, () -> validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserLoginWithSpace() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        user.setLogin("Ло гин");
        Assertions.assertThrows(ValidationException.class, () -> validateUser(user));
    }

    @Test
    void shouldThrowValidateExceptionIfUserNameIsBlank() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        user.setName("");
        validateUser(user);
        Assertions.assertEquals("Логин", user.getName());
    }

    @Test
    void shouldThrowValidateExceptionIfUserBirthdayInFuture() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        user.setBirthday(LocalDate.of(2025,01,01));
        Assertions.assertThrows(ValidationException.class, () -> validateUser(user));
    }
}