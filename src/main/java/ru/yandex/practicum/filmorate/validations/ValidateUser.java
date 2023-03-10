package ru.yandex.practicum.filmorate.validations;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class ValidateUser {

    public void validateUser (User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.info("Поле e-mail не заполнено");
            throw new ValidationException("Ошибка валидации");
        }

        if (!user.getEmail().contains("@")) {
            log.info("Поле e-mail не содержит @");
            throw new ValidationException("Ошибка валидации");
        }

        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.info("Поле логин не заполнено или содержит пробелы");
            throw new ValidationException("Ошибка валидации");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Дата рождения указана в будущем");
            throw new ValidationException("Ошибка валидации");
        }
    }
}
